package com.rescueme;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RescueMeLoginFragment extends Fragment implements
		IRescueMeUpdateListener {

	EditText nameText;
	EditText emailText;
	EditText phoneText;
	ImageButton registerButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		nameText = (EditText) getActivity().findViewById(R.id.name);
		emailText = (EditText) getActivity().findViewById(R.id.email_id);
		phoneText = (EditText) getActivity().findViewById(R.id.phone_number);
		registerButton = (ImageButton) getActivity()
				.findViewById(R.id.register);

		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	private void register() {
		String name = nameText.getText().toString();
		String emailId = emailText.getText().toString();
		String phoneNumber = phoneText.getText().toString();
		if (!TextUtils.isEmpty(name)) {
			if (emailId.endsWith("@gmail.com")) {
				if (phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+")) {
					String params[] = {name, emailId, phoneNumber, RescueMeConstants.YES};
					RescueMeUpdateTask task = new RescueMeUpdateTask(this, getActivity().getApplicationContext());
					getActivity().setProgressBarIndeterminateVisibility(true);
					task.execute(params);
				} else {
					Toast.makeText(
							getActivity(),
							getActivity().getApplicationContext().getString(
									R.string.enter_valid_number),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(
						getActivity(),
						getActivity().getApplicationContext().getString(
								R.string.enter_valid_email_id),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					getActivity(),
					getActivity().getApplicationContext().getString(
							R.string.enter_valid_name), Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.log_in_layout, container, false);
	}

	private void stopProgress() {
		getActivity().setProgressBarIndeterminateVisibility(false); 
	}
	
	@Override
	public void contactsFetched(String result) {
		stopProgress();
		((RescueMeActivity) getActivity()).loginComplete();
	}

}
