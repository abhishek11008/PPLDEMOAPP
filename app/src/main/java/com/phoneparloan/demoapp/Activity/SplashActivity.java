package com.phoneparloan.demoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Log;
import com.phoneparloan.demoapp.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(hasPermissions()) {
            Intent intent = new Intent(SplashActivity.this,BankSeggregationActivity.class);
            startActivity(intent);
            finish();
        }else{
            showConsentScreen();
        }
        try {
            Utils.randSelected = getRandomNumberInRange(1, 10);
            getTheJson();
        }catch (Exception e){
            Log.d("Exception",e.toString());
        }
    }


    public void showConsentScreen(){
        try {
            Intent consentScreenIntent = new Intent(SplashActivity.this,ConsentScreenActivity.class);
            startActivity(consentScreenIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getTheJson(){
        JSONObject jsonobject = null;
        Log.d("abhi_json_check","json_case"+Utils.randSelected+".json");
        try {
            //String jsonLocation = Utils.readJSONFromAsset(SplashActivity.this,  "json_case"+Utils.randSelected+".json");//+Utils.randSelected+".json");
            String jsonLocation = Utils.readJSONFromAsset(SplashActivity.this,  "json_case1"+".json");//+Utils.randSelected+".json");
            Utils.selectedRandomJson = new JSONObject(jsonLocation);
            Log.d("abhi_json_check",Utils.selectedRandomJson+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public boolean hasPermissions() {
        if (ConsentScreenActivity.permissionsArray != null) {
            for (String permission : ConsentScreenActivity.permissionsArray) {
                if (ActivityCompat.checkSelfPermission(SplashActivity.this, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
