package com.planetmayo.debrief.satc_rcp.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.planetmayo.debrief.satc.model.generator.IBoundsManager;
import com.planetmayo.debrief.satc.model.generator.IBoundsManager.IShowBoundProblemSpaceDiagnostics;
import com.planetmayo.debrief.satc.model.generator.IBoundsManager.IShowGenerateSolutionsDiagnostics;
import com.planetmayo.debrief.satc.model.generator.IConstrainSpaceListener;
import com.planetmayo.debrief.satc.model.generator.IGenerateSolutionsListener;
import com.planetmayo.debrief.satc.model.generator.ISolutionGenerator;
import com.planetmayo.debrief.satc.model.legs.CompositeRoute;
import com.planetmayo.debrief.satc.model.legs.CoreLeg;
import com.planetmayo.debrief.satc.model.legs.CoreRoute;
import com.planetmayo.debrief.satc.model.legs.LegType;
import com.planetmayo.debrief.satc.model.states.BaseRange.IncompatibleStateException;
import com.planetmayo.debrief.satc.model.states.BoundedState;
import com.planetmayo.debrief.satc.model.states.LocationRange;
import com.planetmayo.debrief.satc.util.GeoSupport;
import com.planetmayo.debrief.satc_rcp.SATC_Activator;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class SpatialView extends ViewPart implements IConstrainSpaceListener,
		GeoSupport.GeoPlotter, IShowBoundProblemSpaceDiagnostics,
		IShowGenerateSolutionsDiagnostics, IGenerateSolutionsListener
{
	private static JFreeChart _chart;

	private static XYPlot _plot;
	private static XYLineAndShapeRenderer _renderer;
	private Action _debugMode;

	private XYSeriesCollection _myData;
	/**
	 * keep track of how many sets of series that we've plotted
	 * 
	 */
	int _numCycles = 0;
	private Action _resizeButton;

	private Action _showLegend;

	private IBoundsManager boundsManager;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowBoundProblemSpaceDiagnostics
	 */
	private boolean _showLegEndBounds;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowBoundProblemSpaceDiagnostics
	 */
	private boolean _showAllBounds;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowGenerateSolutionsDiagnostics
	 */
	private boolean _showPoints;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowGenerateSolutionsDiagnostics
	 */
	private boolean _showAchievablePoints;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowGenerateSolutionsDiagnostics
	 */
	private boolean _showRoutes;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowGenerateSolutionsDiagnostics
	 */
	private boolean _showRoutesWithScores;

	/**
	 * level of diagnostics for user
	 * 
	 * @see IBoundsManager.IShowGenerateSolutionsDiagnostics
	 */
	@SuppressWarnings("unused")
	private boolean _showRecommendedSolutions;

	/**
	 * the last set of states we plotted
	 * 
	 */
	private Collection<BoundedState> _lastStates = null;

	private ISolutionGenerator solutionGenerator;

	private ArrayList<CoreLeg> _lastSetOfScoredLegs;

	private CompositeRoute[] _lastSetOfSolutions;

	@Override
	public void clear(String title)
	{
		_myData.removeAllSeries();

		if (title != null)
			_chart.setTitle(new TextTitle(title, new java.awt.Font("SansSerif",
					java.awt.Font.BOLD, 8)));
		else
			_chart.setTitle(title);
	}

	@Override
	public void statesBounded(IBoundsManager boundsManager)
	{
		_lastStates = boundsManager.getSpace().states();
		showBoundedStates(_lastStates);
	}

	/**
	 * Creates the Chart based on a dataset
	 * 
	 * @param _myData2
	 */

	private JFreeChart createChart(XYDataset _myData2)
	{
		// tell it to draw joined series
		_renderer = new XYLineAndShapeRenderer(true, false);

		_chart = ChartFactory.createScatterPlot("States", "Lat", "Lon", _myData2,
				PlotOrientation.HORIZONTAL, true, false, false);
		_plot = (XYPlot) _chart.getPlot();
		_plot.setBackgroundPaint(Color.WHITE);
		_plot.setDomainCrosshairPaint(Color.LIGHT_GRAY);
		_plot.setRangeCrosshairPaint(Color.LIGHT_GRAY);
		_plot.setNoDataMessage("No data available");
		_plot.setRenderer(_renderer);
		_chart.getLegend().setVisible(false);

		return _chart;
	}

	@Override
	public void createPartControl(Composite parent)
	{
		boundsManager = SATC_Activator.getDefault().getService(
				IBoundsManager.class, true);
		solutionGenerator = SATC_Activator.getDefault().getService(
				ISolutionGenerator.class, true);
		// get the data ready
		_myData = new XYSeriesCollection();

		JFreeChart chart = createChart(_myData);
		new ChartComposite(parent, SWT.NONE, chart, true);

		makeActions();

		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(_showLegend);
		bars.getToolBarManager().add(_debugMode);
		bars.getToolBarManager().add(_resizeButton);

		// tell the GeoSupport about us
		GeoSupport.setPlotter(this, this, this);
		boundsManager.addBoundStatesListener(this);
		solutionGenerator.addReadyListener(this);
	}

	@Override
	public void dispose()
	{
		boundsManager.removeSteppingListener(this);
		super.dispose();
	}

	@Override
	public void error(IBoundsManager boundsManager, IncompatibleStateException ex)
	{
		String theCont = boundsManager.getCurrentContribution().getName();
		clear("In contribution:[" + theCont + "] problem is: ["
				+ ex.getLocalizedMessage() + "]");
	}

	private void makeActions()
	{
		_debugMode = new Action("Debug Mode", SWT.TOGGLE)
		{
		};
		_debugMode.setText("Debug Mode");
		_debugMode.setChecked(false);
		_debugMode
				.setToolTipText("Track all states (including application of each Contribution)");

		_showLegend = new Action("Show Legend", SWT.TOGGLE)
		{
			public void run()
			{
				super.run();
				_chart.getLegend(0).setVisible(_showLegend.isChecked());
			}
		};
		_showLegend.setText("Show Legend");
		_showLegend.setChecked(false);
		_showLegend.setToolTipText("Show the legend");

		_resizeButton = new Action("Resize", SWT.NONE)
		{

			@Override
			public void run()
			{
				// TODO: Akash - resize the plot to show all the data
			}

		};
		_resizeButton
				.setToolTipText("Track all states (including application of each Contribution)");
	}

	@Override
	public void restarted(IBoundsManager boundsManager)
	{
		clear(null);
	}

	@Override
	public void setFocus()
	{
	}

	private void showBoundedStates(Collection<BoundedState> newStates)
	{
		if (newStates.isEmpty())
		{
			return;
		}

		// just double-check that we're showing any states
		if (!_showLegEndBounds && !_showAllBounds)
			return;

		String lastSeries = "UNSET";
		@SuppressWarnings("unused")
		Color thisColor = null;
		int turnCounter = 1;

		// and plot the new data
		Iterator<BoundedState> iter = newStates.iterator();
		while (iter.hasNext())
		{
			BoundedState thisS = iter.next();
			// get the poly
			LocationRange loc = thisS.getLocation();
			if (loc != null)
			{
				boolean showThisState = false;

				// ok, color code the series
				String thisSeries = thisS.getMemberOf();

				// ok, what about the name?
				String legName = null;

				if (thisSeries != lastSeries)
				{
					// right this is the start of a new leg

					// are we storing leg ends?
					if (_showLegEndBounds)
					{
						showThisState = true;

						if (thisSeries != null)
							legName = thisSeries;
						else
							legName = "Turn " + turnCounter++;

					}

					// ok, use new color

					// TODO: generate a new color. We should prob allow up to 20 colors, I
					// welcome
					// a strategy for generateNewColor()
					thisColor = generateNewColor(thisSeries);

					// and remember the new series
					lastSeries = thisSeries;

				}

				// are we adding this leg?
				if (!showThisState)
				{
					// no, but are we showing mid=leg states?
					if (_showAllBounds)
					{
						// yes - we do want mid-way stats, better add it.
						showThisState = true;
						legName = thisS.getTime().toString();
					}
				}

				// right then do we create a shape (series) for this one?
				if (showThisState)
				{
					// ok, we've got a new series
					XYSeries series = new XYSeries(legName, false);

					// get the shape
					Geometry geometry = loc.getGeometry();
					Coordinate[] boundary = geometry.getCoordinates();
					for (int i = 0; i < boundary.length; i++)
					{
						Coordinate coordinate = boundary[i];
						series.add(new XYDataItem(coordinate.y, coordinate.x));
					}
					_myData.addSeries(series);

					// TODO: Akash - we have to do some fancy JFreeChart to set the color
					// for this data series.
					// I think we may have to retrieve the series index, get the renderer,
					// then set the color
					// (or something like that)
				}
			}
		}
	}

	/**
	 * create a colour for this leg
	 * 
	 * @param legName
	 * @return
	 */
	private Color generateNewColor(String legName)
	{
		// TODO: Akash - generate color for this leg: maybe use hash of legName?
		// i.e. have list of 20 colours. then used hash code mod 20 to give
		// a number from 1 to 20, use that as the index?

		return Color.red;
	}

	@Override
	public void showGeometry(String title, Coordinate[] coords)
	{

		// are we in debug mode?
		if (!_debugMode.isChecked())
			return;

		plotTheseCoordsAsALine(title, coords);

	}

	private void plotTheseCoordsAsALine(String title, Coordinate[] coords)
	{
		int num = addSeries(title, coords);

		_renderer.setSeriesStroke(num, new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 1.0f, new float[]
				{ 10.0f, 6.0f }, 0.0f));
	}

	private void plotTheseCoordsAsAPoints(Collection<Point> points,
			boolean largePoints)
	{
		Collection<Coordinate> coords = new ArrayList<Coordinate>();
		for (Iterator<Point> iterator = points.iterator(); iterator.hasNext();)
		{
			Point point = (Point) iterator.next();

			// ok, add the coordinate of this point
			coords.add(point.getCoordinate());
		}
		Coordinate[] demo = new Coordinate[]
		{};

		// create the data series, get the index number
		int num = addSeries("" + _numCycles++, coords.toArray(demo));

		// TODO: AKASH: configure the renderer to not show lines, but as points
		// (large/small)
		_renderer.setSeriesShapesVisible(num, true);
		_renderer.setSeriesLinesVisible(num, false);
		
		// TODO: AKASH - there's some probem with the logic here. It really looks like I'm
		// using the wrong index num, since it looks like one of the bounded state lines
		// gets switched to be symbols.
		
	}

	private int addSeries(String title, Coordinate[] coords)
	{
		// ok, we've got a new series
		XYSeries series = new XYSeries(title, false);

		// get the shape
		for (int i = 0; i < coords.length; i++)
		{
			Coordinate coordinate = coords[i];
			series.add(new XYDataItem(coordinate.y, coordinate.x));
		}
		_myData.addSeries(series);

		// get the series num
		int num = _myData.getSeriesCount();
		return num;
	}

	@Override
	public void stepped(IBoundsManager boundsManager, int thisStep, int totalSteps)
	{
		if (_debugMode.isChecked())
			showBoundedStates(boundsManager.getSpace().states());
	}

	@Override
	public void setShowAllBounds(boolean onOff)
	{
		_showAllBounds = onOff;

		redoChart();
	}

	@Override
	public void setShowLegEndBounds(boolean onOff)
	{
		_showLegEndBounds = onOff;

		redoChart();
	}

	@Override
	public void setShowRecommendedSolutions(boolean onOff)
	{
		_showRecommendedSolutions = onOff;

		redoChart();
	}

	private void redoChart()
	{
		// clear the UI
		clear(null);

		// and replot
		if (_lastStates != null)
			showBoundedStates(_lastStates);
		if (_lastSetOfScoredLegs != null)
			legsScored(_lastSetOfScoredLegs);
		if (_lastSetOfSolutions != null)
			solutionsReady(_lastSetOfSolutions);
	}

	@Override
	public void setShowPoints(boolean onOff)
	{
		_showPoints = onOff;

		redoChart();
	}

	@Override
	public void setShowAchievablePoints(boolean onOff)
	{
		_showAchievablePoints = onOff;
		redoChart();
	}

	@Override
	public void setShowRoutes(boolean onOff)
	{
		_showRoutes = onOff;
		redoChart();
	}

	@Override
	public void setShowRoutesWithScores(boolean onOff)
	{
		_showRoutesWithScores = onOff;
		redoChart();
	}

	@Override
	public void solutionsReady(CompositeRoute[] routes)
	{
		_lastSetOfSolutions = routes;

		// TODO: IAN - HIGH present the optimal solutions
	}

	private static class ScoredRoute
	{
		private LineString theRoute;
		private double theScore;

		public ScoredRoute(LineString route, double score)
		{
			theRoute = route;
			theScore = score;
		}
	}

	@Override
	public void legsScored(ArrayList<CoreLeg> theLegs)
	{
		_lastSetOfScoredLegs = theLegs;

		// hey, are we showing points?
		if (_showPoints || _showAchievablePoints || _showRoutes)
		{
			Collection<Point> allPoints = new ArrayList<Point>();
			Collection<Point> possiblePoints = new ArrayList<Point>();
			Collection<LineString> possibleRoutes = new ArrayList<LineString>();
			Collection<ScoredRoute> scoredRoutes = new ArrayList<ScoredRoute>();

			// ok, loop trough
			for (Iterator<CoreLeg> iterator = theLegs.iterator(); iterator.hasNext();)
			{
				CoreLeg thisLeg = (CoreLeg) iterator.next();

				// ok, get the points
				CoreRoute[][] routes = thisLeg.getRoutes();

				// go through the start points
				int numStart = routes.length;
				int numEnd = routes[0].length;

				// sort out the start points first
				for (int i = 0; i < numStart; i++)
				{
					CoreRoute[] thisStart = routes[i];

					// ok, are we showing all?
					Point startPoint = thisStart[0].getStartPoint();
					if (_showPoints)
					{
						// ok, just add it to the list
						allPoints.add(startPoint);
					}

					// ok - do we need to check which ones have any valid points?
					if (_showAchievablePoints || _showRoutes)
					{
						boolean isPossible = false;

						for (int j = 0; j < numEnd; j++)
						{
							CoreRoute thisRoute = thisStart[j];

							if (thisRoute.isPossible())
							{
								isPossible = true;

								// we're only currently going to draw lines for straight legs
								if (thisLeg.getType() == LegType.STRAIGHT)
								{
									if (_showRoutes || _showRoutesWithScores)
									{
										Coordinate[] coords = new Coordinate[]
										{ thisRoute.getStartPoint().getCoordinate(),
												thisRoute.getEndPoing().getCoordinate() };
										LineString newR = GeoSupport.getFactory().createLineString(
												coords);

										if (_showRoutes)
											possibleRoutes.add(newR);

										if (_showRoutesWithScores)
											scoredRoutes.add(new ScoredRoute(newR, thisRoute
													.getScore()));
									}
								}
							}
						}

						// ok, add it to the list
						if (isPossible)
						{

							if (_showAchievablePoints)
								possiblePoints.add(startPoint);
						}
					}

				}
			}

			System.out.println("num all points:" + allPoints.size());
			System.out.println("num achievable points:" + possiblePoints.size());

			plotTheseCoordsAsAPoints(allPoints, false);
			plotTheseCoordsAsAPoints(possiblePoints, true);
			plotPossibleRoutes(possibleRoutes);
			plotRoutesWithScores(scoredRoutes);

		}

	}

	private void plotRoutesWithScores(Collection<ScoredRoute> scoredRoutes)
	{
		for (Iterator<ScoredRoute> iterator = scoredRoutes.iterator(); iterator
				.hasNext();)
		{
			ScoredRoute route = iterator.next();

			@SuppressWarnings("unused")
			Point startP = route.theRoute.getStartPoint();
			@SuppressWarnings("unused")
			Point endP = route.theRoute.getEndPoint();

			@SuppressWarnings("unused")
			double thisScore = route.theScore;

			// TODO: Akash, draw a line between these points, colour coded according
			// to the score
		}

	}

	private void plotPossibleRoutes(Collection<LineString> possibleRoutes)
	{
		for (Iterator<LineString> iterator = possibleRoutes.iterator(); iterator
				.hasNext();)
		{
			LineString line = iterator.next();

			// Point startP = line.getStartPoint();
			// Point endP = line.getEndPoint();

			// TODO: Akash, draw a line between these points
			// System.out.println(" r:" + startP.getCoordinate() + " " +
			// endP.getCoordinate());

			plotTheseCoordsAsALine("" + (_numCycles++), line.getCoordinates());

		}

	}
}