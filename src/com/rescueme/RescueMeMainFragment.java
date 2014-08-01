package com.rescueme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class RescueMeMainFragment extends Fragment {

	ImageButton friendsButton;
	ImageButton emergencyButton;
	ImageButton cameraButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		friendsButton = (ImageButton) getActivity()
				.findViewById(R.id.friendsButton);
		emergencyButton = (ImageButton) getActivity()
				.findViewById(R.id.emergencyButton);
		cameraButton = (ImageButton) getActivity()
				.findViewById(R.id.cameraButton);

		friendsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				friends();
			}
		});
		
		emergencyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				emergency();
			}
		});
		
		cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				camera();
			}
		});
	}
	
	private void friends() {
		((RescueMeActivity) getActivity()).friendPressed();
	}
	
	private void emergency() {
		((RescueMeActivity) getActivity()).emergencyPressed();
	}
	
	private void camera() {
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_layout, container, false);
	}
}
