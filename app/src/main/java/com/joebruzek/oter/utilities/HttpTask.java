package com.joebruzek.oter.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * HttpTask is a implementation of AsyncTask that handles making http requests
 *
 * Calling activity must implement HttpTask.HttpCaller interface
 *
 * Created by jbruzek on 11/13/15.
 */
public class HttpTask extends AsyncTask<String, Void, JSONObject> {

    /**
     * Interface to handle processing returned values from an HttpTask
     */
    public interface HttpCaller {
        public void processResults(JSONObject result);
    }

    private HttpCaller caller;

    /**
     * Constructor
     * @param caller the callback to whatever class created this task
     */
    public HttpTask(HttpCaller caller) {
        this.caller = caller;
    }

    /**
     * Pre-execute. Maybe nothing here.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Operate the Http request in the background
     * @param strings AsyncTask parameters
     * @return the completed string value
     */
    @Override
    protected JSONObject doInBackground(String... strings) {
        return getJSON(strings[0]);
    }

    /**
     * Send the result of the task back to the caller
     * @param result The resulting JSONObject
     */
    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        caller.processResults(result);
    }

    private JSONObject getJSON(String address){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
                return new JSONObject(builder.toString());
            } else {
                Log.e(HttpTask.class.toString(), "Failed to get JSON object");
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
