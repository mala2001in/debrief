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

import ASSET.NetworkParticipant;
import ASSET.ParticipantType;
import ASSET.Models.Decision.TargetType;
import ASSET.Models.Detection.DetectionList;
import ASSET.Participants.Category;
import MWC.GUI.Editable;
import MWC.GenericData.TimePeriod;
import MWC.GenericData.WorldDistance;
import MWC.GenericData.WorldLocation;
import MWC.GenericData.WorldSpeed;

public class NMEATrackObserver
  extends RecordStatusToFileObserverType
{
  /***************************************************************
   *  member variables
   ***************************************************************/

  /***************************************************************
   *  constructor
   ***************************************************************/
  /**
   * create a new monitor
   *
   * @param directoryName    the directory to output the plots to
   * @param recordDetections whether to record detections
   */
  public NMEATrackObserver(final String directoryName,
                          final String fileName,
                          final boolean recordDetections,
                          final boolean recordDecisions,
                          final boolean recordPositions,
                          final TargetType subjectType,
                          final String observerName,
                          final boolean isActive)
  {
    super(directoryName, fileName, recordDetections, recordDecisions, recordPositions, subjectType, observerName, isActive);
  }

  /**
   * create a new monitor (using the old constructor)
   *
   * @param directoryName    the directory to output the plots to
   * @param recordDetections whether to record detections
   */
  public NMEATrackObserver(final String directoryName,
                          final String fileName,
                          final boolean recordDetections,
                          final String observerName,
                          final boolean isActive)
  {
    super(directoryName, fileName, recordDetections, false, true, null, observerName, isActive);
  }


  /**
   * ************************************************************
   * member methods
   * *************************************************************
   */

  public void writeThesePositionDetails(final MWC.GenericData.WorldLocation loc,
                                        final ASSET.Participants.Status stat,
                                        final ASSET.ParticipantType pt,
                                        long newTime)
  {

    final StringBuffer buff = new StringBuffer();

    String res;

    // convert the location to flat-earth yards
    double yM = MWC.Algorithms.Conversions.Degs2m(loc.getLat());
    double xM = MWC.Algorithms.Conversions.Degs2m(loc.getLong());
    double zM = -loc.getDepth();

    long theTime = stat.getTime();
    if (theTime == TimePeriod.INVALID_TIME)
      theTime = newTime;

    final String dateStr = MWC.Utilities.TextFormatting.DebriefFormatDateTime.toString(theTime);

    buff.append(dateStr);
    buff.append(",");
    buff.append(pt.getName());
    buff.append(",");
    buff.append(df.format(xM));
    buff.append(",");
    buff.append(df.format(yM));
    buff.append(",");
    buff.append(df.format(zM));
    buff.append(",");
    buff.append(df.format(stat.getCourse()));
    buff.append(",");
    buff.append(df.format(stat.getSpeed().getValueIn(WorldSpeed.M_sec)));
    buff.append(",");
    buff.append(df.format(stat.getFuelLevel()));
    buff.append(",");
    res = buff.toString();

    if (res != null)
    {
      try
      {
        _os.write(res);
        _os.write("" + System.getProperty("line.separator"));
        _os.flush();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * write the current decision description to file
   *
   * @param pt       the participant we're looking at
   * @param activity a description of the current activity
   * @param dtg      the dtg at which the description was recorded
   */
  protected void writeThisDecisionDetail(NetworkParticipant pt, String activity, long dtg)
  {
  }

  /**
   * write these detections to file
   *
   * @param pt         the participant we're on about
   * @param detections the current set of detections
   * @param dtg        the dtg at which the detections were observed
   */
  protected void writeTheseDetectionDetails(ParticipantType pt, DetectionList detections, long dtg)
  {
  }

  public void outputThisDetection(WorldLocation loc, long dtg, String hostName, Category hostCategory,
                                  double bearing, WorldDistance range, String sensor_name,
                                  String label)
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
    return "res_" + name + "_" + MWC.Utilities.TextFormatting.DebriefFormatDateTime.toString(System.currentTimeMillis()) + ".log";
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
   * @param title the scenario we're describing
   * @throws IOException
   */

  protected void writeFileHeaderDetails(final String title, long currentDTG) throws IOException
  {
    // don't bother
  }

  protected void writeBuildDate(String details) throws IOException
  {
    // don't bother
  }
  //////////////////////////////////////////////////////////////////////
  // editable properties
  //////////////////////////////////////////////////////////////////////

  static public class CSVTrackObserverInfo extends Editable.EditorType
  {


    /**
     * constructor for editable details of a set of Layers
     *
     * @param data the Layers themselves
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
        final java.beans.PropertyDescriptor[] res = {
          prop("Directory", "The directory to place Debrief data-files"),
          prop("Active", "Whether this observer is active"),
        };

        return res;
      }
      catch (java.beans.IntrospectionException e)
      {
        return super.getPropertyDescriptors();
      }
    }

  }
}
