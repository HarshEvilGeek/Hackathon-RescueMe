package com.rescueme;

public class RescueMeContactsModel {

	private String contactId;
	private String name;
	private String emailId;
	private String phoneNumber;
	private String isEmergencyContact;
	
	public RescueMeContactsModel(String contactId, String name, String emailId, String phoneNumber, String isEmergencyContact) {
		this.contactId = contactId;
		this.name = name;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.isEmergencyContact = isEmergencyContact;
	}

	public String getContactId() {
		return contactId;
	}
	
	public String getName() {
		return name;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setEmergencyContact(String isEmergencyContact) {
		this.isEmergencyContact = isEmergencyContact;
	}
	
	public String isEmergencyContact() {
		return isEmergencyContact;
	}
}
