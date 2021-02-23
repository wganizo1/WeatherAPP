package com.global.kinetic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {
    public GpsTracker gpsTracker;
    public double latitude,longitude;
    public static final String JSON_ARRAY_WEATHER = "weather";
    public JSONArray servr_resp = null;
    public JSONObject jsonObjectWeather =null;
    private ProgressDialog loading;
    public ListView listView;
    public String address;
    public TextView tv_address;
    public ArrayList arrayList;
    public Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        tv_address = findViewById(R.id.current_location);
        check_permission();
        getLocation();
        if(isNetworkAvailable(getApplicationContext())) {
            getWeather("http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=53f9d8e4213222cf517d86dc406d67fc");
        }else {
            show_toast(getString(R.string.no_connection));
        }
    }

    private void show_toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void check_permission(){
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getLocation() {
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            try {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                getLocationDetails();
            }catch (Exception e){
                show_toast(getString(R.string.location_not_found_error));
            }
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    public void getWeather(final String url){
        try {
            show_dialog(getString(R.string.loading));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            try {
                                jsonObjectWeather = new JSONObject(response);
                                servr_resp = jsonObjectWeather.getJSONArray(JSON_ARRAY_WEATHER);
                                arrayList = new ArrayList<>();
                                adapter = new Adapter(getApplicationContext(), arrayList);
                                for (int i = 0; i < servr_resp.length(); i++) {
                                    try {
                                        JSONObject jo = servr_resp.getJSONObject(i);
                                        Model model = new Model();
                                        model.setID(jo.getString("id"));
                                        model.setMain(jo.getString("main"));
                                        model.setDescription(jo.getString("description"));
                                        model.setIcon(jo.getString("icon"));
                                        arrayList.add(model);
                                        listView.setAdapter(adapter);
                                    } catch (Exception e) {
                                        loading.dismiss();
                                    }

                                }
                            } catch (JSONException e) {
                                show_toast(getString(R.string.comm_error));
                                loading.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            show_toast(getString(R.string.comm_error));
                            loading.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){
            loading.dismiss();
            show_toast(getString(R.string.comm_error));
        }
    }
    public void show_dialog(String txt) {
        try {
            loading = ProgressDialog.show(this, txt, getString(R.string.wait), false, false);
        } catch (Exception e) {
            loading.dismiss();
        }
    }
    public void getLocationDetails(){
        if(gpsTracker.canGetLocation()){
            try {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                address = addresses.get(0).getAddressLine(0);
                tv_address.setText(address);
            }catch (Exception e){
            show_toast(getString(R.string.location_not_found_error));
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}