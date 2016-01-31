package com.owner.janetgaedke.jgaedkelab12_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 11/29/2015.
 */
public class Contact implements Parcelable {
    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPhone;
    private String mDescription;
    public String toString() {
        mDescription = mFirstName + " " + mLastName + "  "
                + mEmail + " " + mPhone;
        return mDescription;
    }

    //constructor with arguments
    public Contact(String mFirstName, String mLastName, String mEmail, String mPhone) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mEmail = mEmail;
        this.mPhone = mPhone;

    }


    //constructor with no args
    public Contact(String mFirstName, String mLastName) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        mEmail = "";
        mPhone = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhone);
        dest.writeString(this.mDescription);
    }

    protected Contact(Parcel in) {
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
        this.mEmail = in.readString();
        this.mPhone = in.readString();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
