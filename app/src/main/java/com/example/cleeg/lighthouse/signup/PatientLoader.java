package com.example.cleeg.lighthouse.signup;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.cleeg.lighthouse.models.Patient;

public class PatientLoader extends AsyncTaskLoader<Patient> {

    // Tag for log messages
    private static final String TAG = "EarthquakeLoader";

    // Query URL
    private String mUrl;

    /**
     * Constructs a new EarthquakeLoader
     * @param context of the activity
     * @param url to load data from
     */
    public PatientLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        // Required to actually trigger the loadInBackground() method to execute
        forceLoad();
    }

    @Override
    public Patient loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a patient
        Patient patient = QueryUtils.fetchPatientData(mUrl);
        return patient;
    }
}
