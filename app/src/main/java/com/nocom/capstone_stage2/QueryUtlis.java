package com.nocom.capstone_stage2;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;


public final class QueryUtlis {

    String weburl;

     static final String LOG_TAG = QueryUtlis.class.getSimpleName();

    private QueryUtlis() {
    }
    public static ArrayList<Tennis> featchrecipedata(String requestUrl) throws JSONException {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<Tennis> tennis = extractFeatureFromJson(jsonResponse);//,jsonResponse2);

        return tennis;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the tennis news JSON results.", e);


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static ArrayList<Tennis> extractFeatureFromJson(String tennisjson) throws JSONException {
        if (TextUtils.isEmpty(tennisjson)) {
            return null;
        }
        ArrayList<Tennis> list = new ArrayList<>();

        try {

            //       // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            //    -JSONObject baseJsonResponse = new JSONObject(SAMPLE_JSON_RESPONSE);
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(tennisjson);
            JSONObject responceobject = baseJsonResponse.getJSONObject("response");
            JSONArray docs = responceobject.getJSONArray("docs");
            for (int i=0; i<docs.length(); i++){

                JSONObject currentdocs = docs.getJSONObject(i);

                String weburl = currentdocs.getString( "web_url"); // weburl
                Log.i("weburl",weburl);

                String snippetarticledetail = currentdocs.getString("snippet"); // snippet tmam fy sleem


                JSONObject headlines = currentdocs.getJSONObject("headline");
                String articleheadline =  headlines.getString("print_headline"); // headline tmam fy sleem

                Log.i("articleheadline",articleheadline);

               JSONArray multimedia = currentdocs.getJSONArray("multimedia");
              //  for(int j=0; j<multimedia.length();j++ ){

                   JSONObject firstmultimedia = multimedia.getJSONObject(0);
                   String imageurl = firstmultimedia.getString("url");
                   Log.i("IMAGEURL",imageurl);


                    Tennis tennis = new Tennis(weburl,snippetarticledetail,articleheadline,imageurl);


                    list.add(tennis);


                }



            //}


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Movie JSON results", e);
        }
        return list;


    }





    }

