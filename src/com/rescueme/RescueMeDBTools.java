package com.rescueme;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RescueMeDBTools extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "contacts";

	public RescueMeDBTools(Context applicationContext) {

		// Call use the database or to create it

		super(applicationContext, "contactbook.db", null, 1);

	}

	// onCreate is called the first time the database is created

	public void onCreate(SQLiteDatabase database) {

		// How to create a table in SQLite
		// Make sure you don't put a ; at the end of the query

		String query = "CREATE TABLE " + TABLE_NAME
				+ " ( contactId INTEGER PRIMARY KEY, " + RescueMeConstants.NAME
				+ " TEXT, " + RescueMeConstants.EMAIL + " TEXT, "
				+ RescueMeConstants.NUMBER + " TEXT, "
				+ RescueMeConstants.EMERGENCY + " TEXT)";

		// Executes the query provided as long as the query isn't a select
		// or if the query doesn't return any data

		database.execSQL(query);

	}

	// onUpgrade is used to drop tables, add tables, or do anything
	// else it needs to upgrade
	// This is droping the table to delete the data and then calling
	// onCreate to make an empty table

	public void onUpgrade(SQLiteDatabase database, int version_old,
			int current_version) {
		String query = "DROP TABLE IF EXISTS " + TABLE_NAME;

		// Executes the query provided as long as the query isn't a select
		// or if the query doesn't return any data

		database.execSQL(query);
		onCreate(database);
	}

	public void insertContact(RescueMeContactsModel model) {

		// Open a database for reading and writing

		SQLiteDatabase database = this.getWritableDatabase();

		// Stores key value pairs being the column name and the data
		// ContentValues data type is needed because the database
		// requires its data type to be passed

		ContentValues values = new ContentValues();

		values.put(RescueMeConstants.NAME, model.getName());
		values.put(RescueMeConstants.EMAIL, model.getEmailId());
		values.put(RescueMeConstants.NUMBER, model.getPhoneNumber());
		values.put(RescueMeConstants.EMERGENCY, model.isEmergencyContact());

		// Inserts the data in the form of ContentValues into the
		// table name provided

		database.insert("contacts", null, values);

		// Release the reference to the SQLiteDatabase object

		database.close();
	}

	public int updateContact(RescueMeContactsModel model) {

		// Open a database for reading and writing

		SQLiteDatabase database = this.getWritableDatabase();

		// Stores key value pairs being the column name and the data

		ContentValues values = new ContentValues();

		values.put(RescueMeConstants.NAME, model.getName());
		values.put(RescueMeConstants.EMAIL, model.getEmailId());
		values.put(RescueMeConstants.NUMBER, model.getPhoneNumber());
		values.put(RescueMeConstants.EMERGENCY, model.isEmergencyContact());

		// update(TableName, ContentValueForTable, WhereClause,
		// ArgumentForWhereClause)

		return database.update("contacts", values, "contactId" + " = ?",
				new String[] { model.getContactId() });
	}

	// Used to delete a contact with the matching contactId

	public void deleteContact(String id) {

		// Open a database for reading and writing

		SQLiteDatabase database = this.getWritableDatabase();

		String deleteQuery = "DELETE FROM  contacts where contactId='" + id
				+ "'";

		// Executes the query provided as long as the query isn't a select
		// or if the query doesn't return any data

		database.execSQL(deleteQuery);
	}

	public ArrayList<RescueMeContactsModel> getAllContacts() {

		ArrayList<RescueMeContactsModel> contactArrayList = new ArrayList<RescueMeContactsModel>();

		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		// Open a database for reading and writing

		SQLiteDatabase database = this.getWritableDatabase();

		// Cursor provides read and write access for the
		// data returned from a database query

		// rawQuery executes the query and returns the result as a Cursor

		Cursor cursor = database.rawQuery(selectQuery, null);

		// Move to the first row

		if (cursor.moveToFirst()) {
			do {
				String contactId = cursor.getString(0);
				String name = cursor.getString(1);
				String email = cursor.getString(2);
				String number = cursor.getString(3);
				String emergency = cursor.getString(4);

				RescueMeContactsModel model = new RescueMeContactsModel(
						contactId, name, email, number, emergency);

				contactArrayList.add(model);

			} while (cursor.moveToNext()); // Move Cursor to the next row
		}

		// return contact list
		return contactArrayList;
	}

	public RescueMeContactsModel getContactInfo(String id) {

		RescueMeContactsModel model = null;

		// Open a database for reading only

		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM contacts where contactId='" + id
				+ "'";

		// rawQuery executes the query and returns the result as a Cursor

		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {

				String contactId = cursor.getString(0);
				String name = cursor.getString(1);
				String email = cursor.getString(2);
				String number = cursor.getString(3);
				String emergency = cursor.getString(4);

				model = new RescueMeContactsModel(contactId, name, email,
						number, emergency);

			} while (cursor.moveToNext());
		}
		return model;
	}
}
