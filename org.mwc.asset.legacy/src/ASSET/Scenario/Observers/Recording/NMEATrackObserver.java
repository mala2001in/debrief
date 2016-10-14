/*
 *    Debrief - the Open Source Maritime Analysis Application
 *    http://debrief.info
 *
 *    (C) 2000-2014, PlanetMayo Ltd
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the Eclipse Public License v1.0
 *    (http://www.eclipse.org/legal/epl-v10.html)
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 */
package ASSET.Scenario.Observers.Recording;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ASSET.NetworkParticipant;
import ASSET.ParticipantType;
import ASSET.ScenarioType;
import ASSET.Models.Decision.TargetType;
import ASSET.Models.Detection.DetectionList;
import ASSET.Participants.Category;
import MWC.GUI.Editable;
import MWC.GenericData.TimePeriod;
import MWC.GenericData.WorldDistance;
import MWC.GenericData.WorldLocation;
import MWC.GenericData.WorldSpeed;

public class NMEATrackObserver extends RecordStatusToFileObserverType
{
  private SimpleDateFormat dateFormat;
  private boolean _freshRun = false;
  private DecimalFormat dp1;
  private DecimalFormat mmsi;
  private DecimalFormat dec3;
  private DecimalFormat dec2;
  private DecimalFormat dec2_4;

  /***************************************************************
   * member variables
   ***************************************************************/

  /***************************************************************
   * constructor
   ***************************************************************/
  /**
   * create a new monitor
   * 
   * @param directoryName
   *          the directory to output the plots to
   * @param recordDetections
   *          whether to record detections
   */
  public NMEATrackObserver(final String directoryName, final String fileName,
      final boolean recordDetections, final boolean recordDecisions,
      final boolean recordPositions, final TargetType subjectType,
      final String observerName, final boolean isActive)
  {
    super(directoryName, fileName, recordDetections, recordDecisions,
        recordPositions, subjectType, observerName, isActive);

    dateFormat = new SimpleDateFormat("yyyyMMdd,HHmmss.SSS");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    
    dp1 = new DecimalFormat("0.0");
    mmsi = new DecimalFormat("000000000");
    
    dec3 = new DecimalFormat("000");
    dec2 = new DecimalFormat("00");
    dec2_4 = new DecimalFormat("00.0000");
  }

  /**
   * create a new monitor (using the old constructor)
   * 
   * @param directoryName
   *          the directory to output the plots to
   * @param recordDetections
   *          whether to record detections
   */
  public NMEATrackObserver(final String directoryName, final String fileName,
      final boolean recordDetections, final String observerName,
      final boolean isActive)
  {
    this(directoryName, fileName, recordDetections, false, true, null,
        observerName, isActive);
  }

  @Override
  protected void performSetupProcessing(ScenarioType scenario)
  {
    super.performSetupProcessing(scenario);
    _freshRun = true;
  }

  /**
   * ************************************************************ member methods
   * *************************************************************
   */

  private String dateFor(long time)
  {
    Date d = new Date(time);
    // 20160720,000000.859
    return dateFormat.format(d);
  }

  private String locationFor(WorldLocation loc)
  {
    double dLat = Math.abs(loc.getLat());
    double dLong = Math.abs(loc.getLong());
    
    int degLat = (int) dLat;
    double minLat = dLat - degLat;
    char hemLat = loc.getLat() > 0 ? 'N' : 'S';
    int degLong = (int) dLong;
    double minLong = dLong - degLong;
    char hemLong = loc.getLong() > 0 ? 'E' : 'W';
    
    // 3606.3667,N,00522.3698,W
    return dec2.format(degLat) + dec2_4.format(minLat) + "," + hemLat + "," + dec3.format(degLong) + dec2_4.format(minLong) + "," + hemLong ;
  }

  private String nameMsg(String name)
  {
    // v-name: 
    return "$POSL,VNM," + name + "*03";
  }

  private String timeMsg(long time)
  {
    // time-now $POSL,DZA,20160720,000000.859,0007328229*42
    final String timeStr = dateFor(time);
    return "$POSL,DZA," + timeStr + ",0007328229*42";
  }

  private String depthMsg(double depth)
  {
    // depth: $POSL,PDS,9.2,M*03
    return "$POSL,PDS,"+ dp1.format(depth) + ",M*03";
  }

  private String stateMsg(double course, double speed)
  {
    // crse/spd: $POSL,VEL,GPS,276.3,4.6,,,*35
    return "$POSL,VEL,GPS," + dp1.format(course) + "," + dp1.format(speed) + ",,,*35;";
  }

  private String aisMsg(long time, WorldLocation loc, double course,
      double speed, long id)
  {
    // AIS: $POSL,AIS,564166000,3606.3667,N,00522.3698,W,0,7.8,327.9,0,330.0,AIS1,0,0*06
    return "$POSL,AIS," + mmsi.format(id) + "," + locationFor(loc) + ",0,7.8,327.9,0,330.0,AIS1,0,0*06";
  }

  private String osLocMsg(WorldLocation loc)
  {
    // os-pos: $POSL,POS,GPS,1122.2222,N,00712.6666,W,0.00,,Center of Rotation,N,,,,,*41
    return "$POSL,POS,GPS," + locationFor(loc) + ",0.00,,Center of Rotation,N,,,,,*41";
  }

  private void writeThis(String msg) throws IOException
  {
    // ok, output the vessel name
    _os.write(msg);
    _os.write("" + System.getProperty("line.separator"));
  }

  public void writeThesePositionDetails(
      final MWC.GenericData.WorldLocation loc,
      final ASSET.Participants.Status stat, final ASSET.ParticipantType pt,
      long newTime)
  {
    try
    {

      long theTime = stat.getTime();
      if (theTime == TimePeriod.INVALID_TIME)
        theTime = newTime;

      // ok, decide on the message type
      if (Category.Force.BLUE.equals(pt.getCategory().getForce()))
      {
        if (_freshRun)
        {
          writeThis(nameMsg(pt.getName()));
        }

        // output time
        writeThis(timeMsg(theTime));

        // output depth
        writeThis(depthMsg(loc.getDepth()));

        // output course/speed
        writeThis(stateMsg(stat.getCourse(), stat.getSpeed().getValueIn(
            WorldSpeed.Kts)));

        // output location
        writeThis(osLocMsg(loc));
      }
      else
      {
        // output AIS messag
        writeThis(aisMsg(newTime, loc, stat.getCourse(), stat.getSpeed()
            .getValueIn(WorldSpeed.Kts), pt.getId()));
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  /**
   * write the current decision description to file
   * 
   * @param pt
   *          the participant we're looking at
   * @param activity
   *          a description of the current activity
   * @param dtg
   *          the dtg at which the description was recorded
   */
  protected void writeThisDecisionDetail(NetworkParticipant pt,
      String activity, long dtg)
  {
  }

  /**
   * write these detections to file
   * 
   * @param pt
   *          the participant we're on about
   * @param detections
   *          the current set of detections
   * @param dtg
   *          the dtg at which the detections were observed
   */
  protected void writeTheseDetectionDetails(ParticipantType pt,
      DetectionList detections, long dtg)
  {
  }

  public void outputThisDetection(WorldLocation loc, long dtg, String hostName,
      Category hostCategory, double bearing, WorldDistance range,
      String sensor_name, String label)
  {
    // don't bother
    // todo: to implement (output this detection)
  }

  /**
   * ok, create the property editor for this class
   * 
   * @return the custom editor
   */
  protected Editable.EditorType createEditor()
  {
    return new NMEATrackObserver.CSVTrackObserverInfo(this);
  }

  protected String newName(final String name)
  {
    return "res_"
        + name
        + "_"
        + MWC.Utilities.TextFormatting.DebriefFormatDateTime.toString(System
            .currentTimeMillis()) + ".log";
  }

  /**
   * determine the normal suffix for this file type
   */
  protected String getMySuffix()
  {
    return "log";
  }

  /**
   * write out the file header details for this scenario
   * 
   * @param title
   *          the scenario we're describing
   * @throws IOException
   */

  protected void writeFileHeaderDetails(final String title, long currentDTG)
      throws IOException
  {
    // don't bother
  }

  protected void writeBuildDate(String details) throws IOException
  {
    // don't bother
  }

  // ////////////////////////////////////////////////////////////////////
  // editable properties
  // ////////////////////////////////////////////////////////////////////

  static public class CSVTrackObserverInfo extends Editable.EditorType
  {

    /**
     * constructor for editable details of a set of Layers
     * 
     * @param data
     *          the Layers themselves
     */
    public CSVTrackObserverInfo(final NMEATrackObserver data)
    {
      super(data, data.getName(), "Edit");
    }

    /**
     * editable GUI properties for our participant
     * 
     * @return property descriptions
     */
    public java.beans.PropertyDescriptor[] getPropertyDescriptors()
    {
      try
      {
        final java.beans.PropertyDescriptor[] res =
            {prop("Directory", "The directory to place Debrief data-files"),
                prop("Active", "Whether this observer is active"),};

        return res;
      }
      catch (java.beans.IntrospectionException e)
      {
        return super.getPropertyDescriptors();
      }
    }

  }
}
