package com.rescueme;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

public class RescueMeUpdateTask extends AsyncTask<String, String, String> {

	IRescueMeUpdateListener listener;
	Context mApplication;

	RescueMeUpdateTask(IRescueMeUpdateListener listener, Context application) {
		this.listener = listener;
		this.mApplication = application;
	}

	@Override
	protected String doInBackground(String... params) {
		String name = params[0];
		String emailId = params[1];
		String phoneNumber = params[2];
		boolean requiresLogin = RescueMeConstants.YES.equals(params[3]) ? true
				: false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (requiresLogin
				&& !RescueMeConstants.SUCCESS.equals(register(name, emailId,
						phoneNumber))) {
			return RescueMeConstants.LOGIN_FAIL;
		}
		ArrayList<RescueMeContactsModel> contacts = fetchContents();
		RescueMeDBTools dbHelper = new RescueMeDBTools(mApplication);
		for (RescueMeContactsModel model : contacts) {
			dbHelper.insertContact(model);
		}

		return null;

	}

	private String register(String name, String emailId, String phoneNumber) {
		HttpClient httpClient = new DefaultHttpClient();  
		String url = "http://akhilrescueme.appspot.com/rescuemeserver";
		url+="?type=register"+"&"+"name=" + name + "&email=" + emailId + "&phoneNumber=" + phoneNumber;
		HttpGet httpGet = new HttpGet(url);
		try {
		    HttpResponse response = httpClient.execute(httpGet);
		    StatusLine statusLine = response.getStatusLine();
		    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
		        HttpEntity entity = response.getEntity();
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        entity.writeTo(out);
		        out.close();
		        String responseStr = out.toString();
		        return RescueMeConstants.SUCCESS;
		        // do something with response 
		    } else {
		        // handle bad response
		    }
		} catch (ClientProtocolException e) {
		    // handle exception
		} catch (IOException e) {
		    // handle exception
		}
		return RescueMeConstants.LOGIN_FAIL;
	}

	private ArrayList<RescueMeContactsModel> fetchContents() {
		// dummy code
		Cursor phones = mApplication.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		List<String> numbers = new ArrayList<String>();

		while (phones.moveToNext()) {
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
			if (phoneNumber.length() >= 10) {
				numbers.add(phoneNumber.substring(phoneNumber.length() - 10));
			}
		}
		phones.close();

		ArrayList<RescueMeContactsModel> contactArrayList = new ArrayList<RescueMeContactsModel>();
		RescueMeContactsModel model = new RescueMeContactsModel("", "ran",
				"over", "11111", RescueMeConstants.YES);
		contactArrayList.add(model);
		model = new RescueMeContactsModel("", "ran bombadil", "over@gmail.com",
				"11111", RescueMeConstants.YES);
		contactArrayList.add(model);
		model = new RescueMeContactsModel("", "blo lol", "run@gmail.com",
				"21111", RescueMeConstants.NO);
		contactArrayList.add(model);
		model = new RescueMeContactsModel("", "tom bombadil", "cool@gmail.com",
				"14411", RescueMeConstants.YES);
		contactArrayList.add(model);
		model = new RescueMeContactsModel("", "tim runstein", "why@gmail.com",
				"30948", RescueMeConstants.NO);
		contactArrayList.add(model);

		return contactArrayList;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		listener.contactsFetched(result);
	}

}
