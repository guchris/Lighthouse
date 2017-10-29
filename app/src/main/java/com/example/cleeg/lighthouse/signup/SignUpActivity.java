package com.example.cleeg.lighthouse.signup;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.cleeg.lighthouse.MainActivity;
import com.example.cleeg.lighthouse.R;
import com.example.cleeg.lighthouse.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Patient> {

    private static final String TYPEFORM_REQUEST_URL = "https://api.typeform.com/v1/form/FqsykY" +
            "?key=d125edddd57012ac634d17b238c35e6339862dd6&completed=true" +
            "&order_by[]=date_land,desc";
    private static final String TAG = "SignUpActivity";

    // Constant value for the patient loader ID
    private static final int PATIENT_LOADER_ID = 1;

    private static int INTENT_ID = 5;

    private DatabaseReference mDatabaseReference;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Uri uri = Uri.parse("https://christophergu.typeform.com/to/FqsykY");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        // startActivityForResult(intent, INTENT_ID);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(PATIENT_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            Log.e(TAG, "Display Error");
        }
    }

    /**
     * For when the LoaderManager has determined that the loader with our specified ID isn't
     * running, so we should create a new one.
     */
    @Override
    public Loader<Patient> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new PatientLoader(this, TYPEFORM_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<Patient> loader, Patient patient) {

        // If there is a valid Patient, then add into Firebase Database
        if (patient != null) {
            String email = patient.getEmail();
            Log.d(TAG, email);

            String username;
            if (email.contains("@")){
                username = email.split("@")[0];
            } else {
                username = email;
            }
            Log.d(TAG, username);

            mDatabaseReference.child("patients").child(username).child("info").setValue(patient);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onLoaderReset(Loader<Patient> loader) {

    }
}
