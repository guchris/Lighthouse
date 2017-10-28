package com.example.cleeg.lighthouse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.cleeg.lighthouse.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final String TYPEFORM_REQUEST_URL = "https://api.typeform.com/v1/form/" +
            "FqsykY?key=d125edddd57012ac634d17b238c35e6339862dd6&completed=true&order_by[]=" +
            "date_land,desc&offset=1&limit=1";
    private static final int REQUEST_CODE = 1;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Uri uri = Uri.parse("https://christophergu.typeform.com/to/FqsykY");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent, REQUEST_CODE);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        setContentView(myWebView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Patient patient = QueryUtils.fetchPatientData(TYPEFORM_REQUEST_URL);
            String email = patient.getEmail();
            Log.d("SignUpActivity", email);
            String username;
            if (email.contains("@")){
                username = email.split("@")[0];
            } else {
                username = email;
            }
            Log.d("SignUpActivity", username);
            mDatabaseReference.child("patients").child(username).push().setValue(patient);
        }
    }
}
