package com.wiprotestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 27-Jul-16.
 */
public class TransportInfoActivity extends Activity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Spinner spinner;
    private Button mNavigate;
    float mLatitude;
    float mLongitude;
    List<String> transportNamesList;
    List<Transport> transportArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportinfo_activity);

        spinner = (Spinner) findViewById(R.id.spinner);
        mNavigate = (Button) findViewById(R.id.btn_navigate);

        new LoadTransportInfoTask().execute();

        mNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapViewIntent = new Intent(TransportInfoActivity.this, MapViewActivity.class);
                mapViewIntent.putExtra("lat",mLatitude);
                mapViewIntent.putExtra("long",mLongitude);
                startActivity(mapViewIntent);
            }
        });
    }


    private class LoadTransportInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            String response = null;
            try {
                response = getResponseFromServiceURL(getResources().getString(R.string.url));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    Log.v("result;", "result====" + result);
                    transportArrayList = new ArrayList<Transport>();
                    transportNamesList = new ArrayList<String>();

                    JSONArray jsonarray = new JSONArray(result);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonEachTransportObject = jsonarray.getJSONObject(i);

                        Transport transportObj = new Transport();

                        transportObj.setId(jsonEachTransportObject.optString("id"));
                        transportObj.setName(jsonEachTransportObject.optString("name"));
                        transportObj.setTransportModeByCar(jsonEachTransportObject.optJSONObject("fromcentral").optString("car"));
                        transportObj.setTransportModeByTrain(jsonEachTransportObject.optJSONObject("fromcentral").optString("train"));
                        transportObj.setLatitude(Float.parseFloat(jsonEachTransportObject.optJSONObject("location").optString("latitude")));
                        transportObj.setLongitude(Float.parseFloat(jsonEachTransportObject.optJSONObject("location").optString("longitude")));

                        transportArrayList.add(transportObj);

                        // Saving the results of names in ArrayList to populate Spinner
                        transportNamesList.add(jsonEachTransportObject.optString("name"));

                    }

                    // Setting the ArrayList to Spinner adapter
                    spinner.setAdapter(new ArrayAdapter<String>(TransportInfoActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            transportNamesList));

                    // Spinner on item click listener
                    spinner
                            .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> arg0,
                                                           View arg1, int position, long arg3) {

                                    TextView tv_car = (TextView) findViewById(R.id.tv_car);
                                    TextView tv_train = (TextView) findViewById(R.id.tv_train);

                                    tv_car.setText("Car - "
                                            + transportArrayList.get(position).getTransportModeByCar());
                                    if(transportArrayList.get(position).getTransportModeByTrain().length()>0){
                                        tv_train.setVisibility(View.VISIBLE);
                                        tv_train.setText("Train - "
                                                + transportArrayList.get(position).getTransportModeByTrain());
                                    }else{
                                        tv_train.setVisibility(View.GONE);
                                    }

                                    mLatitude = transportArrayList.get(position).getLatitude();
                                    mLongitude = transportArrayList.get(position).getLongitude();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });


                }
            } catch (JSONException jex) {
                jex.printStackTrace();
            }
        }
    }


    private String getResponseFromServiceURL(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}
