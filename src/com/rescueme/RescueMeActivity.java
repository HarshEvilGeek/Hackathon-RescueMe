package com.rescueme;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class RescueMeActivity extends Activity {

	SharedPreferences pref;
	boolean loginRequired;
	String curTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		pref = this.getSharedPreferences("com.rescueme", Context.MODE_PRIVATE);
		loginRequired = pref.getBoolean(RescueMeConstants.LOGIN_REQUIRED, true);
		setContentView(R.layout.splash_screen_layout);
		if (loginRequired) {
			loadFragment(RescueMeConstants.loginPage, "");
		} else {
			loadFragment(RescueMeConstants.mainPage, "");
		}
	}

	public void loadFragment(String fragId, String extra) {
		Fragment newFragment;
		if (RescueMeConstants.loginPage.equals(fragId)) {
			curTag = RescueMeConstants.loginPage;
			newFragment = new RescueMeLoginFragment();
		} else if (RescueMeConstants.mainPage.equals(fragId)) {
			curTag = RescueMeConstants.mainPage;
			newFragment = new RescueMeMainFragment();
		} else if (RescueMeConstants.contactsPage.equals(fragId)) {
			curTag = RescueMeConstants.contactsPage;
			newFragment = new RescueMeContactsFragment();
		} else {
			curTag = RescueMeConstants.countdownPage;
			newFragment = new RescueMeCountdownFragment(extra);
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.container, newFragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (R.id.contacts == item.getItemId()) {
			loadFragment(RescueMeConstants.contactsPage, "");
		}
		if (R.id.refresh == item.getItemId()) {
			// TODO implement refresh
		}
		return super.onOptionsItemSelected(item);
	}

	public void loginComplete() {
		pref.edit().putBoolean(RescueMeConstants.LOGIN_REQUIRED, false)
				.commit();
		loadFragment(RescueMeConstants.mainPage, "");
	}

	public void friendPressed() {
		loadFragment(RescueMeConstants.countdownPage,
				RescueMeConstants.friendsTag);
	}

	public void emergencyPressed() {
		loadFragment(RescueMeConstants.countdownPage,
				RescueMeConstants.emergencyTag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rescue_me, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		if(RescueMeConstants.loginPage.equals(curTag) || RescueMeConstants.mainPage.equals(curTag)) {
			super.onBackPressed();
		}
		else {
			loadFragment(RescueMeConstants.mainPage, "");
		}
	}
	
	public void falseAlarm() {
		loadFragment(RescueMeConstants.mainPage, "");
	}
}
