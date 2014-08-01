package com.rescueme;

import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class RescueMeContactsFragment extends Fragment implements
		IRescueMeUpdateListener {

	ListView contactsListView;
	List<RescueMeContactsModel> contactArrayList;
	RescueMeDBTools dbHelper;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void contactsFetched(String result) {
		stopProgress();

	}

	private void stopProgress() {
		getActivity().setProgressBarIndeterminateVisibility(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.contacts_layout, container, false);
		dbHelper = new RescueMeDBTools(this.getActivity()
				.getApplicationContext());
		contactArrayList = dbHelper.getAllContacts();
		final ContactsArrayAdapter adapter = new ContactsArrayAdapter(
				getActivity(), R.layout.contact_list_item, contactArrayList);
		contactsListView = (ListView) view.findViewById(
				R.id.contactsList);
		contactsListView.setAdapter(adapter);
		return view;
	}

	private class ContactsArrayAdapter extends
			ArrayAdapter<RescueMeContactsModel> {

		HashMap<RescueMeContactsModel, Integer> mIdMap = new HashMap<RescueMeContactsModel, Integer>();

		Context context;

		public ContactsArrayAdapter(Context context, int textViewResourceId,
				List<RescueMeContactsModel> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.contact_list_item, parent,
					false);
			TextView name = (TextView) rowView.findViewById(R.id.contactName);
			TextView email = (TextView) rowView.findViewById(R.id.emailId);
			CheckBox chBox = (CheckBox) rowView
					.findViewById(R.id.selectContact);

			RescueMeContactsModel model = getItem(position);
			name.setText(model.getName());
			email.setText(model.getEmailId());
			if (RescueMeConstants.YES.equals(model.isEmergencyContact())) {
				chBox.setChecked(true);
			} else {
				chBox.setChecked(false);
			}
			return rowView;
		}

		@Override
		public long getItemId(int position) {
			RescueMeContactsModel item = getItem(position);
			return mIdMap.get(item);
		}

	}
}
