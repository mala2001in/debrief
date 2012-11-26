package com.planetmayo.debrief.satc.gwt.client.contributions;

import java.beans.PropertyChangeEvent;

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
import com.planetmayo.debrief.satc.model.contributions.BearingMeasurementContribution;

public class BearingMeasurementContributionView extends BaseContributionView
{

	interface BearingMeasurementContributionViewUiBinder extends
			UiBinder<Widget, BearingMeasurementContributionView>
	{
	}

	private static BearingMeasurementContributionViewUiBinder uiBinder = GWT
			.create(BearingMeasurementContributionViewUiBinder.class);

	@UiField
	Slider2BarWidget bearing;

	@UiField
	StartFinishWidget startFinish;
	@UiField
	NameWidget name;
	private BearingMeasurementContribution _myData;

	public BearingMeasurementContributionView()
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

		bearing.addBarValueChangedHandler(new BarValueChangedHandler()
		{
			@Override
			public void onBarValueChanged(BarValueChangedEvent event)
			{
				_myData.setBearingError(new Double(event.getValue()));
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

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0)
	{
		super.propertyChange(arg0);
		final String attr = arg0.getPropertyName();
		if (attr.equals(BaseContribution.NAME))
			name.setData((String) arg0.getNewValue());
		else if (attr.equals(BearingMeasurementContribution.BEARING_ERROR))
			bearing.setData((Integer) arg0.getNewValue());

	}

	@Override
	public void setData(BaseContribution contribution)
	{

		super.setData(contribution);

		// and store the type-casted contribution
		_myData = (BearingMeasurementContribution) contribution;

		// property changes
		// initialise the UI components
		Double brgE = _myData.getBearingError();
		
		// is there a bearing error?
		if(brgE != null)
			bearing.setData((int) brgE.doubleValue());
		name.setData(contribution.getName());
		startFinish.setData(contribution.getStartDate(),
				contribution.getFinishDate());

		contribution.addPropertyChangeListener(
				BearingMeasurementContribution.BEARING_ERROR, this);
	}

}
