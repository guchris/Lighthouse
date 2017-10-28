package com.example.cleeg.lighthouse.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Patient {
    private String mEmail;
    private String mUid;

    public Patient() {
        // Default constructor required for calls to DataSnapshot.getValue(Patient.class)
    }

    public Patient(String email, String uid) {
        mEmail = email;
        mUid = uid;
    }
}
