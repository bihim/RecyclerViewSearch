package com.artificialsoft.recyclerviewsearch;

public class Items
{
    String itemName;
    String phoneNo;
    String contactDetails;
    String profileURL;

    public Items(String itemName, String phoneNo, String contactDetails, String profileURL) {
        this.itemName = itemName;
        this.phoneNo = phoneNo;
        this.contactDetails = contactDetails;
        this.profileURL = profileURL;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPhoneNo()
    {
        return phoneNo;
    }
    public String getContactDetails() {
        return contactDetails;
    }
    public String getProfileURL() {
        return profileURL;
    }
}
