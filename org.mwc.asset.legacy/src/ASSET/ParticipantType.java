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

package ASSET;

import ASSET.Models.Sensor.SensorDataProvider;
import ASSET.Models.Sensor.SensorList;
import ASSET.Participants.Category;
import ASSET.Participants.DemandedStatus;
import ASSET.Participants.ParticipantDetectedListener;
import ASSET.Participants.Status;
import MWC.GenericData.WorldDistance;
import MWC.GenericData.WorldLocation;

public interface ParticipantType extends ParticipantDetectedListener, SensorDataProvider, NetworkParticipant
{

  public void addParticipantMovedListener(ASSET.Participants.ParticipantMovedListener listener);

  public void removeParticipantMovedListener(ASSET.Participants.ParticipantMovedListener listener);
  
  
  /** find the range of this participant from the specified location (which allows us to have a participant that
   * has an area, not just a point
   */
  public WorldDistance rangeFrom(WorldLocation point);

  /**
   * listeners which hear when a participant either is using a different behaviour to the previous step,
   * or if the behaviour stays the same but is returning a different activity message.
   *
   * @param listener the listener to add/remove
   */
  public void addParticipantDecidedListener(ASSET.Participants.ParticipantDecidedListener listener);

  /**
   * listeners which hear when a participant either is using a different behaviour to the previous step,
   * or if the behaviour stays the same but is returning a different activity message.
   *
   * @param listener the listener to add/remove
   */
  public void removeParticipantDecidedListener(ASSET.Participants.ParticipantDecidedListener listener);

  /**
   * the name of this participant
   */
  public void setName(String val);

  /**
   * the demanded status of this participant
   */
  public DemandedStatus getDemandedStatus();

  /**
   * perform the decision portion of the step
   */
  public void doDecision(long oldTime, long newTime, ASSET.ScenarioType scenario);

  /**
   * perform the movement portion of the step
   */
  public void doMovement(long oldtime, long newTime, ASSET.ScenarioType scenario);

  /**
   * perform the detection portion of the step
   */
  public void doDetection(long oldtime, long newTime, ASSET.ScenarioType scenario);


  /**
   * reset, to go back to the initial state
   */
  public void restart(ScenarioType scenario);

  /**
   * the movement characteristics for this participant
   */
  public ASSET.Models.Movement.MovementCharacteristics getMovementChars();

  /**
   * find the list of current detections
   */
  public ASSET.Models.Detection.DetectionList getNewDetections();

  /**
   * the movement characteristics for this participant
   */
  public ASSET.Models.MovementType getMovementModel();

  /**
   * the energy radiation characteristics for this participant
   */
  public ASSET.Models.Vessels.Radiated.RadiatedCharacteristics getRadiatedChars();

  /**
   * the self noise characteristics characteristics for this participant
   */
  public ASSET.Models.Vessels.Radiated.RadiatedCharacteristics getSelfNoise();

  /**
   * get the radiated noise of this participant in this bearing in this medium
   */
  double getRadiatedNoiseFor(int medium, double brg_degs);

  /**
   * get the radiated noise of this participant in this bearing in this medium
   */
  double getSelfNoiseFor(int medium, double brg_degs);

  /**
   * set the decision model for this participant
   */
  public void setDecisionModel(ASSET.Models.DecisionType decision);

  /**
   * get the decision model for this participant
   */
  public ASSET.Models.DecisionType getDecisionModel();

  /**
   * find out how many sensors there are
   */
  public int getNumSensors();

  /**
   * get a specific sensor
   */
  public ASSET.Models.SensorType getSensorAt(int index);

  /**
   * set the movement model for this participant
   */
  public void setMovementModel(ASSET.Models.MovementType movement);

  /**
   * add a sensor to this participant
   */
  public void addSensor(ASSET.Models.SensorType sensor);

  /**
   * set the category
   */
  public void setCategory(Category val);

  /**
   * set the initial status
   */
  public void setInitialStatus(Status val);

  /**
   * set the status
   */
  public void setStatus(Status _myStatus);

  /**
   * set the demanded status for this participant
   */
  public void setDemandedStatus(DemandedStatus _myDemandedStatus);

  /**
   * set the sensor fit
   */
  public void setSensorFit(ASSET.Models.Sensor.SensorList _mySensorList);

  /**
   * set the movement characteristics
   */
  public void setMovementChars(ASSET.Models.Movement.MovementCharacteristics moveChars);

  /**
   * set the radiated noise characteristics
   */
  public void setRadiatedChars(ASSET.Models.Vessels.Radiated.RadiatedCharacteristics radChars);

  /**
   * set the self noise characteristics
   */
  public void setSelfNoise(ASSET.Models.Vessels.Radiated.RadiatedCharacteristics radChars);

  /**
   * get the sensors for this participant
   *
   * @return
   */
  public SensorList getSensorFit();

  /**
   * find out if this participant radiates this type of noise
   *
   * @param medium the medium we're looking for
   * @return yes/no
   */
  boolean radiatesThisNoise(int medium);

  /** whether to paint decisions if this participant is shown graphically
   * 
   * @param paintDecisions
   */
	public  void setPaintDecisions(boolean paintDecisions);

  /** whether to paint decisions if this participant is shown graphically
   * 
   * @param paintDecisions
   */
	public  boolean getPaintDecisions();

	/** whether this participant is alive yet
	 * 
	 * @return
	 */
	public abstract boolean isAlive();
	
	/** whether this participant is alive yet
	 * 
	 * @return
	 */
	public abstract void setAlive(boolean val);

	/** whether this participant is alive yet
	 * 
	 * @return
	 */
	public abstract boolean getAlive();

}
