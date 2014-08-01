package com.rescueme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class RescueMeCountdownFragment extends Fragment{
	
	String tag;
	
	public RescueMeCountdownFragment (String tag) {
		this.tag = tag;
	}


	ImageButton falseAlarmButton;
	ImageButton confirmButton;
	ImageButton cameraButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		falseAlarmButton = (ImageButton) getActivity()
				.findViewById(R.id.falseAlarmButton);
		confirmButton = (ImageButton) getActivity()
				.findViewById(R.id.confirmButton);
		cameraButton = (ImageButton) getActivity()
				.findViewById(R.id.cameraButton);

		falseAlarmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				falseAlarm();
			}
		});
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				confirm();
			}
		});
		
		cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				camera();
			}
		});
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.countdown_layout, container, false);
	}
	
	private void falseAlarm() {
		((RescueMeActivity) getActivity()).falseAlarm();
	}
	
	private void confirm() {
		
	}
	
	private void camera() {
		
	}

}
