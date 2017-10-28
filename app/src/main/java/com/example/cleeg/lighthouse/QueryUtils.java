package com.example.cleeg.lighthouse;

import android.text.TextUtils;
import android.util.Log;

import com.example.cleeg.lighthouse.models.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {

    // Tag for the log messages
    private static final String TAG = "QueryUtils";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {}

    public static Patient fetchPatientData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a Patient object
        Patient patient = extractFeatureFromJson(jsonResponse);

        return patient;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        // A URLConnection with support for HTTP-specific features
        HttpURLConnection urlConnection = null;
        // Represent an input stream of bytes (small chunks of data)
        InputStream inputStream = null;
        try {
            // Return a HttpURLConnection instance that represents a connection to the remote
            // object referred to by the URL
            urlConnection = (HttpURLConnection) url.openConnection();

            /* The setup parameters and general request properties are manipulated */
            // Set the method for the URL request as GET
            urlConnection.setRequestMethod("GET");
            // Set the read timeout to a specified timeout, in milliseconds
            urlConnection.setReadTimeout(10000);
            // Set a specified timeout value, in milliseconds, to be used when opening a
            // communications link to the resource referenced by this URLConnection
            urlConnection.setConnectTimeout(15000);

            // Open a communications link to the resource referenced by this URL
            // *This is when the actual connection to the remote object is made
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and
            // parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                /* The header fields and contents of the remote object/resource can be accessed */
                // Return an input stream that reads from the open connection
                inputStream = urlConnection.getInputStream();
                // Return a String that contains the whole JSON response
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                // Indicate that other requests to the server are unlikely in the near future
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream should throw an IOException, which is why the
                // makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        // Constructs a string builder (a mutable sequence of characters) with no characters in
        // it and an initial capacity of 16 characters
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            // Create an InputStreamReader that bridges byte streams to character streams;
            // it reads bytes and decodes them into characters using a specified charset
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // Create a BufferedReader that reads text from a character-input stream, buffering
            // characters that allows for efficient reading of characters, arrays, and lines
            BufferedReader reader = new BufferedReader(inputStreamReader);

            // Read a line of text
            String line = reader.readLine();
            while (line != null) {
                // Append the specified string to this character sequence
                output.append(line);
                line = reader.readLine();
            }
        }
        // Return a string representing the data in this sequence
        return output.toString();
    }

    public static Patient extractFeatureFromJson(String jsonResponse) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        Patient patient = new Patient();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray patientArray = baseJsonResponse.getJSONArray("responses");
            JSONObject patientObject = patientArray.getJSONObject(0);
            JSONObject answersObject = patientObject.getJSONObject("answers");

            String name = answersObject.getString("textfield_65103582");
            Log.d(TAG, name);
            String email = answersObject.getString("email_65103637");
            Log.d(TAG, email);
            Integer phoneNumber = answersObject.getInt("number_65103649");
            String birthDate = answersObject.getString("date_65103641");
            Log.d(TAG, birthDate);
            Integer age = answersObject.getInt("textfield_65103645");
            String maritalStatus = answersObject.getString("list_65104043_choice");
            Log.d(TAG, maritalStatus);
            Integer numChildren = answersObject.getInt("textfield_65104113");
            String medicinalAllergies = answersObject.getString("textfield_65103815");
            Log.d(TAG, medicinalAllergies);
            String medications = answersObject.getString("textfield_65103842");
            String healthInsurance = answersObject.getString("list_65104104_choice");
            String medicalHistory = answersObject.getString("textarea_65113779");
            String familyHistory = answersObject.getString("textarea_65113821");
            Integer immunizationUpToDate = answersObject.getInt("yesno_65104422");
            String emergencyContactName = answersObject.getString("textfield_65104494");
            Integer emergencyContactPhone = answersObject.getInt("number_65104504");

            patient = new Patient(name, email, phoneNumber, birthDate, age, maritalStatus,
                    numChildren, medicinalAllergies, medications, healthInsurance, medicalHistory,
                    familyHistory, immunizationUpToDate, emergencyContactName, emergencyContactPhone);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the patient JSON results", e);
        }

        // Return the patient
        return patient;
    }
}
