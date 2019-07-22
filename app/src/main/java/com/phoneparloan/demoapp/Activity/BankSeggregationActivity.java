package com.phoneparloan.demoapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.phoneparloan.demoapp.Objects.SmsParsedBankData;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Log;
import com.phoneparloan.demoapp.Utils.RecyclerItemClickListener;
import com.phoneparloan.demoapp.Utils.Utils;
import com.phoneparloan.demoapp.adapters.AdapterBankList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BankSeggregationActivity extends AppCompatActivity {
    private static String TAG = BankSeggregationActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    HashSet<String> bankNameHashset = new HashSet<String>();
    ArrayList<String> finalBankNamesFound = new ArrayList<>();
     ArrayList<String> bankName = new ArrayList<>();
     ArrayList<String> bankNameImages = new ArrayList<>();
    Activity mActivity;
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SmsParsedBankData> dataList = new ArrayList<>();
    HashSet<String> hashSet = new HashSet<>();
    AdapterBankList adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks_seggregation);
        mActivity = BankSeggregationActivity.this;
        init();
    }


    public void init() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rvBanks);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    Toast.makeText(mActivity, dataList.get(position).getBankName(), Toast.LENGTH_SHORT).show();


                    // Intent for new Activity here to show the salary smses list

                Intent intent = new Intent(BankSeggregationActivity.this,ShowSalarySMSesActivity.class);
                intent.putExtra("bankName",dataList.get(position).getBankName());
                intent.putExtra("bankImage",dataList.get(position).getBankImage());
                startActivity(intent);


            }

            @Override
            public void onItemLongClick(View view, int position) {
                //bottomSheetDialogLayout(dataList.get(position).getCredit_id(),position);
            }
        }));


        Log.d("bank_counts", "Sender_Ids Count: " + Utils.senderId_keyword_array.length + "");
        Log.d("bank_counts", "Bank Names Count: " + Utils.bankNamesMapping.length + "");
        Log.d("bank_counts", "Bank Images Count: " + Utils.bankImagesMapping.length + "");

        parseTheJson();

        Log.d("abhi_sms_parsing","Total_SMS_DATA_COUNT: "+ Utils.smsArray.length());
        getAllTransactionalSMS();
        Log.d("abhi_sms_parsing", "ALL_TXN_SMS_DATA Count: "+ Utils.smsTransArray.length());
        getCreditedTxnSMSData();
        Log.d("abhi_sms_parsing", "ALL_CREDITED_TXN_SMS_DATA Count: "+ Utils.smsCreditedArray.length());
        getDebitedTxnSMSData();
        Log.d("abhi_sms_parsing", "ALL_DEBITED_TXN_SMS_DATA Count: "+ Utils.smsDebitedArray.length());
    }


    public void parseTheJson() {
        try {
            Utils.callLogArray = Utils.selectedRandomJson.getJSONArray("call_log");
            Utils.smsArray = Utils.selectedRandomJson.getJSONArray("sms_list");
            Utils.contactListArray = Utils.selectedRandomJson.getJSONArray("contact_list");
            Utils.appListArray = Utils.selectedRandomJson.getJSONArray("app_list");
            Utils.deviceInfoArray = Utils.selectedRandomJson.getJSONArray("deivce_info");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getAllTransactionalSMS(){
        if(Utils.smsArray != null &&Utils.smsArray.length() > 0){
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < Utils.senderId_keyword_array.length; i++) {
                for (int j = 0; j < Utils.smsArray.length(); j++) {
                    try {
                        if (Utils.smsArray.getJSONObject(j).getString("address").trim().toUpperCase().contains(Utils.senderId_keyword_array[i].trim().toUpperCase())) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("address", Utils.smsArray.getJSONObject(j).getString("address"));
                                jsonObject.put("body", Utils.smsArray.getJSONObject(j).getString("body"));
                                jsonObject.put("date_get", Utils.smsArray.getJSONObject(j).getString("date_get"));
                                bankNameHashset.add(Utils.senderId_keyword_array[i]);
                                Log.e("abhi_sms_parsing", "BankName Hashset :" +  bankNameHashset.size());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);

                        }
                    } catch (JSONException e) {
                        // txnSMSFound = false;
                        e.printStackTrace();
                    }
                }
            }
            Utils.smsTransArray = jsonArray;
            // Utils.smsCreditedArray =jsonArray;
            Log.e("abhi_sms_parsing",jsonArray.toString());

            retrieveBanKNameData(bankNameHashset);
        }
    }

     
    public void retrieveBanKNameData(Set set) {
        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            finalBankNamesFound.add(itr.next().toString());
            itr.next();
        }

        for (int i = 0; i < finalBankNamesFound.size(); i++) {
            Log.e("abhi_sms_parsing", "Final Bank Names List" + finalBankNamesFound.get(i));

            for (int h = 0; h < Utils.senderId_keyword_array.length; h++) {
                if (Utils.senderId_keyword_array[h].equals(finalBankNamesFound.get(i))) {
                    Log.e("abhi_sms_parsing","H Found" + h );
                    SmsParsedBankData smsParsedBankData = new SmsParsedBankData();
                    smsParsedBankData.setBankName(Utils.bankNamesMapping[h]);
                    smsParsedBankData.setBankImage("https://s3.ap-south-1.amazonaws.com/phoneparloan/bank_icons/"+Utils.bankImagesMapping[h]);

                    if(!hashSet.contains(Utils.bankNamesMapping[h])) {
                        dataList.add(smsParsedBankData);
                        hashSet.add(Utils.bankNamesMapping[h]);
                    }
                }
            }
        }

        adapter = new AdapterBankList(mActivity, mActivity, dataList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Log.e("abhi_sms_parsing", "DATA LIST SIZE::" + dataList.size());
    }

    private void getCreditedTxnSMSData() {
        if (Utils.smsTransArray != null && Utils.smsTransArray.length() > 0) {
            JSONArray jsonArray = new JSONArray();

            for (int j = 0; j < Utils.smsTransArray.length(); j++) { // smstransArray
                for (int k = 0; k < Utils.credit_keyword_array.length; k++) {
                    try {
                        if (Utils.smsTransArray.getJSONObject(j).getString("body").trim().toUpperCase().contains(Utils.credit_keyword_array[k].trim().toUpperCase())) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("address", Utils.smsTransArray.getJSONObject(j).getString("address"));
                                jsonObject.put("body", Utils.smsTransArray.getJSONObject(j).getString("body"));
                                jsonObject.put("date_get", Utils.smsTransArray.getJSONObject(j).getString("date_get"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                            break;
                        }
                    } catch (JSONException e) {
                        // txnSMSFound = false;
                        e.printStackTrace();
                    }
                }
            }

            Utils.smsCreditedArray = jsonArray;
            Log.i("smsCreditedArray", jsonArray.toString());
        }
    }

    private void getDebitedTxnSMSData() {
        if (Utils.smsTransArray != null && Utils.smsTransArray.length() > 0) {
            JSONArray jsonArray = new JSONArray();

            for (int j = 0; j < Utils.smsTransArray.length(); j++) { // smstransArray
                for (int k = 0; k < Utils.debit_keyword_array.length; k++) {
                    try {
                        if (Utils.smsTransArray.getJSONObject(j).getString("body").trim().toUpperCase().contains(Utils.debit_keyword_array[k].trim().toUpperCase())) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("address", Utils.smsTransArray.getJSONObject(j).getString("address"));
                                jsonObject.put("body", Utils.smsTransArray.getJSONObject(j).getString("body"));
                                jsonObject.put("date_get", Utils.smsTransArray.getJSONObject(j).getString("date_get"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                            break;
                        }
                    } catch (JSONException e) {
                        // txnSMSFound = false;
                        e.printStackTrace();
                    }
                }
            }

            Utils.smsDebitedArray = jsonArray;
            Log.i("smsDebitedArray", jsonArray.toString());
        }
    }

    private boolean redirectSMSNotPresent() {
        try {
            boolean txnSMSFound = false;

            // Utils.showProgressbar(mActivity);
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mActivity);
                progressDialog.setMessage("Verifying Please Wait !!!");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }

            Log.d("abhi_debug_one", Utils.smsArray.length() + "");
            for (int i = 0; i < Utils.senderId_keyword_array.length; i++) {
                for (int j = 0; j < Utils.smsArray.length(); j++) {
                    try {
                        if (Utils.smsArray.getJSONObject(j).getString("address").trim().toUpperCase().contains(Utils.senderId_keyword_array[i].trim().toUpperCase())) {
                            // txnSMSFound = true;
                            return true;
                        } else {
                            txnSMSFound = false;
                        }
                    } catch (JSONException e) {
                        txnSMSFound = false;
                        e.printStackTrace();
                    }
                }
            }
            return txnSMSFound;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }
}
