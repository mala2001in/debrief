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
package ASSET.Util.XML.Decisions;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import ASSET.Models.DecisionType;
import ASSET.Models.Decision.*;
import ASSET.Models.Decision.Movement.*;
import ASSET.Models.Decision.Tactical.*;
import ASSET.Util.XML.Decisions.Tactical.*;
import MWC.Utilities.ReaderWriter.XML.MWCXMLReader;

abstract public class WaterfallHandler extends CoreDecisionHandler
{

	private final static String type = "Waterfall";

	protected BehaviourList _myList;

	// we get into a problem, when we recursively add ChainHandlers to themselves.
	// control this, by only allowing Chains to nest 5 deep (at the fifth level,
	// don't add
	// the chain and sequence handlers)
	public final static int MAX_CHAIN_DEPTH = 6;
	public static int _thisChainDepth = 0;

	public WaterfallHandler(int thisDepth)
	{
		this(type, thisDepth);
	}

	public WaterfallHandler(String title, int thisDepth)
	{
		super(title);
		addHandlers(this, this, thisDepth);
		_myList = createNewList();
	}

	/**
	 * add the set of handlers to this object
	 */
	private void addHandlers(MWCXMLReader list, final WaterfallHandler handler,
			int thisDepth)
	{
		if (thisDepth > 0)
		{
			list.addHandler(new PatternSaw_SearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new PatternLadder2_SearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new PatternInwardSpiral_SearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new PatternOutwardSpiral_SearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new LadderSearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new ExpandingSquareSearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new EvadeHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new DetonationHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new SSKRechargeHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new RectangleWanderHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new WanderHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new LaunchWeaponHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new WaitHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new DeferredBirthHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new TrailHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new BearingTrailHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new InterceptHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new MarkDipHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new TransitWaypointHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new TransitHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new SternArcClearanceHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new CompositeHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new CircularDatumSearchHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new UserControlHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new TerminateHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new MoveHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new RaiseBodyHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new InvestigateHandler()
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});

			_thisChainDepth++;

			list.addHandler(new WorkingTransitHandler(--thisDepth)
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new SwitchHandler(--thisDepth)
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new SequenceHandler(--thisDepth)
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
			list.addHandler(new WaterfallHandler(--thisDepth)
			{
				public void setModel(final ASSET.Models.DecisionType dec)
				{
					handler.addModel(dec);
				}
			});
		}
	}

	protected BehaviourList createNewList()
	{
		return new Waterfall();
	}

	public void addModel(final ASSET.Models.DecisionType dec)
	{
		_myList.insertAtFoot(dec);
	}

	public void elementClosed()
	{

		// setup the parent (or child) bits
		setAttributes((CoreDecision) _myList);

		// finally output it
		setModel(_myList);

		// and reset it, ready for the next chain
		_myList = createNewList();
	}

	abstract public void setModel(ASSET.Models.DecisionType dec);

	static public void exportThis(final Object toExport,
			final org.w3c.dom.Element parent, final org.w3c.dom.Document doc)
	{
		// create ourselves
		final org.w3c.dom.Element thisPart = doc.createElement(type);

		// get data item
		final ASSET.Models.Decision.BehaviourList bb = (ASSET.Models.Decision.BehaviourList) toExport;

		// output the paernt bits
		CoreDecisionHandler.exportThis((CoreDecision) bb, thisPart, doc);

		// thisPart.setAttribute("MIN_DEPTH", writeThis(bb.getMinDepth()));
		// step through the models
		final java.util.Iterator<DecisionType> it = bb.getModels().iterator();
		while (it.hasNext())
		{
			final ASSET.Models.DecisionType dec = (ASSET.Models.DecisionType) it
					.next();

			exportThisDecisionModel(dec, thisPart, doc);

		}

		parent.appendChild(thisPart);

	}

	protected static void exportThisDecisionModel(
			final ASSET.Models.DecisionType dec, final org.w3c.dom.Element thisPart,
			final org.w3c.dom.Document doc)
	{
		if (dec instanceof BearingTrail)
			BearingTrailHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Trail)
			TrailHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof ExpandingSquareSearch)
			ExpandingSquareSearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof PatternSearch_Ladder)
			LadderSearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof PatternSearch_OutwardSpiral)
			PatternOutwardSpiral_SearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof PatternSearch_Saw)
			PatternSaw_SearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof PatternSearch_InwardSpiral)
			PatternInwardSpiral_SearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof PatternSearch_Ladder2)
			PatternLadder2_SearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof WorkingTransit)
			WorkingTransitHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof TransitWaypoint)
			TransitWaypointHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Transit)
			TransitHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Wait)
			WaitHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof DeferredBirth)
			DeferredBirthHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof MarkDip)
			MarkDipHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Investigate)
			InvestigateHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Evade)
			EvadeHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof SternArcClearance)
			SternArcClearanceHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof SSKRecharge)
			SSKRechargeHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof RectangleWander)
			RectangleWanderHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Wander)
			WanderHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Composite)
			CompositeHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof UserControl)
			UserControlHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Move)
			MoveHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof CircularDatumSearch)
			CircularDatumSearchHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Sequence)
			SequenceHandler.exportSequence(dec, thisPart, doc);
		else if (dec instanceof Waterfall)
			WaterfallHandler.exportThisDecisionModel(dec, thisPart, doc);
		else if (dec instanceof RaiseBody)
			RaiseBodyHandler.exportThis(dec, thisPart, doc);
		else if (dec instanceof Intercept)
			InterceptHandler.exportThis(dec, thisPart, doc);
	}

}