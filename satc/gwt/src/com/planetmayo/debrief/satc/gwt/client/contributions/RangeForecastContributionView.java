package com.planetmayo.debrief.satc.gwt.client.contributions;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.planetmayo.debrief.satc.gwt.client.ui.NameWidget;
import com.planetmayo.debrief.satc.gwt.client.ui.Slider2BarWidget;
import com.planetmayo.debrief.satc.gwt.client.ui.StartFinishWidget;
import com.planetmayo.debrief.satc.model.contributions.BaseContribution;
import com.planetmayo.debrief.satc.model.contributions.CourseForecastContribution;
import com.planetmayo.debrief.satc.model.contributions.RangeForecastContribution;

public class RangeForecastContributionView extends BaseContributionView
{

	interface RangeForecastContributionViewUiBinder extends
			UiBinder<Widget, RangeForecastContributionView>
	{
	}

	private static RangeForecastContributionViewUiBinder uiBinder = GWT
			.create(RangeForecastContributionViewUiBinder.class);

	private RangeForecastContribution _myData;

	@UiField
	Slider2BarWidget min;

	@UiField
	Slider2BarWidget max;

	@UiField
	Slider2BarWidget estimate;

	@UiField
	NameWidget name;

	@UiField
	StartFinishWidget startFinish;

	public RangeForecastContributionView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();

	}

	@Override
	protected BaseContribution getData()
	{
		return _myData;
	}

	@Override
	public void initHandlers()
	{
		super.initHandlers();

		max.addBarValueChangedHandler(new BarValueChangedHandler()
		{
			@Override
			public void onBarValueChanged(BarValueChangedEvent event)
			{
				_myData.setMaxRange(new Double(event.getValue()));
			}
		});

		min.addBarValueChangedHandler(new BarValueChangedHandler()
		{
			@Override
			public void onBarValueChanged(BarValueChangedEvent event)
			{
				_myData.setMinRange(new Double(event.getValue()));
			}
		});

		estimate.addBarValueChangedHandler(new BarValueChangedHandler()
		{
			@Override
			public void onBarValueChanged(BarValueChangedEvent event)
			{
				_myData.setEstimate(new Double(event.getValue()));
			}
		});
		name.addValueChangeHandler(new ValueChangeHandler<String>()
		{

			@Override
			public void onValueChange(ValueChangeEvent<String> event)
			{
				_myData.setName(event.getValue());

			}
		});

		startFinish.addValueChangeHandler(new ValueChangeHandler<Date>()
		{

			@Override
			public void onValueChange(ValueChangeEvent<Date> event)
			{
				_myData.setStartDate(event.getValue());

			}
		}, new ValueChangeHandler<Date>()
		{

			@Override
			public void onValueChange(ValueChangeEvent<Date> event)
			{
				_myData.setFinishDate(event.getValue());

			}
		});

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0)
	{
		super.propertyChange(arg0);
		final String attr = arg0.getPropertyName();
		if (attr.equals(CourseForecastContribution.MIN_COURSE))
			min.setData((Integer) arg0.getNewValue());
		else if (attr.equals(CourseForecastContribution.MAX_COURSE))
			max.setData((Integer) arg0.getNewValue());
		else if (attr.equals(BaseContribution.ESTIMATE))
			estimate.setData((int) Math.round((Double) arg0.getNewValue()));
		else if (attr.equals(BaseContribution.NAME))
			name.setData((String) arg0.getNewValue());
		else if (attr.equals(BaseContribution.START_DATE))
			startFinish.setStartData((Date) arg0.getNewValue());
		else if (attr.equals(BaseContribution.FINISH_DATE))
			startFinish.setFinishData((Date) arg0.getNewValue());

	}

	@Override
	public void setData(BaseContribution contribution)
	{

		// let the parent register with the contribution
		super.setData(contribution);

		// and store the type-casted contribution
		_myData = (RangeForecastContribution) contribution;

		// property changes
		// initialise the UI components
		Double minR = _myData.getMinRange();
		if (minR != null)
			min.setData((int) minR.doubleValue());
		Double maxR = _myData.getMaxRange();
		if (maxR != null)
			max.setData((int) maxR.doubleValue());
		estimate.setData((int) Math.round(Double.valueOf(_myData.getEstimate()
				.toString())));
		name.setData(contribution.getName());
		startFinish.setData(contribution.getStartDate(),
				contribution.getFinishDate());

	}

}
