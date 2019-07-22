package com.phoneparloan.demoapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.phoneparloan.demoapp.Objects.SmsParsedBankData;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Answers;
import com.phoneparloan.demoapp.Utils.Log;
import com.phoneparloan.demoapp.Utils.RecyclerItemClickListener;
import com.phoneparloan.demoapp.Utils.Utils;
import com.phoneparloan.demoapp.adapters.AdapterBankList;
import com.phoneparloan.demoapp.adapters.AdapterSalarySMSList;

import java.util.ArrayList;

public class ShowSalarySMSesActivity extends AppCompatActivity {

    String bankName="",bankImage ="";
    TextView bankNameText;
    ImageView bankImageView;
    RecyclerView rvSalarySmsRV;
    LinearLayoutManager linearLayoutManager;
    Activity mActivity;
    AdapterSalarySMSList adapter;
    String salarybankName;
    ArrayList<SmsParsedBankData> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exact_salary_sms);
        bankName = getIntent().getStringExtra("bankName");
        bankImage = getIntent().getStringExtra("bankImage");
        init();
    }

    public void init(){
        mActivity = ShowSalarySMSesActivity.this;
        bankNameText = findViewById(R.id.bankName);
        bankImageView = findViewById(R.id.bankImage);
        bankNameText.setText(bankName);
        Glide.with(ShowSalarySMSesActivity.this)
                .load(bankImage)
                .into(bankImageView);

        rvSalarySmsRV = findViewById(R.id.rvSalarySmsRV);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rvSalarySmsRV.setLayoutManager(linearLayoutManager);
        rvSalarySmsRV.setItemAnimator(new DefaultItemAnimator());
        rvSalarySmsRV.setHasFixedSize(true);

        parseSMSDataList();

        /*rvSalarySmsRV.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, rvSalarySmsRV, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mActivity, dataList.get(position).getBankName(), Toast.LENGTH_SHORT).show();

                Log.d("abhi_salary_check","bankName: "+ bankName);
                Log.d("abhi_salary_check","salaryBankName :" + salarybankName);

                // Intent for new Activity here to show the salary smses list

                Toast.makeText(mActivity, "Add here Dashboard Button", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //bottomSheetDialogLayout(dataList.get(position).getCredit_id(),position);
            }
        }));*/

    }

    public void parseSMSDataList(){
        int getrandomSelected = Utils.randSelected;
        String[] smsSalaryList;
        String[] defaultSMSList;
        String[] existingEMIList;


        if(getrandomSelected == 1){
            smsSalaryList = Answers.Json1SalarySMSList;
            defaultSMSList = Answers.Json1DefaultSMS;
            existingEMIList = Answers.Json1ExisitngEMI;
            salarybankName = "UCO Bank";
        }else if(getrandomSelected == 2){
            smsSalaryList = Answers.Json2SalarySMSList;
            defaultSMSList = Answers.Json2DefaultSMS;
            existingEMIList = Answers.Json2ExisitngEMI;
            salarybankName = "HDFC Bank";
        }else if(getrandomSelected == 3){
            smsSalaryList = Answers.Json3SalarySMSList;
            defaultSMSList = Answers.Json3DefaultSMS;
            existingEMIList = Answers.Json3ExisitngEMI;
            salarybankName = "HDFC Bank";
        }else if(getrandomSelected == 4){
            smsSalaryList = Answers.Json4SalarySMSList;
            defaultSMSList = Answers.Json4DefaultSMS;
            existingEMIList = Answers.Json4ExisitngEMI;
            salarybankName = "Axis Bank";
        }else if(getrandomSelected == 5){
            smsSalaryList = Answers.Json5SalarySMSList;
            defaultSMSList = Answers.Json5DefaultSMS;
            existingEMIList = Answers.Json5ExisitngEMI;
            salarybankName = "ICICI Bank";
        }else if(getrandomSelected == 6){
            smsSalaryList = Answers.Json6SalarySMSList;
            defaultSMSList = Answers.Json6DefaultSMS;
            existingEMIList = Answers.Json6ExisitngEMI;
            salarybankName = "Union Bank";
        }else if(getrandomSelected == 7){
            smsSalaryList = Answers.Json7SalarySMSList;
            defaultSMSList = Answers.Json7DefaultSMS;
            existingEMIList = Answers.Json7ExisitngEMI;
            salarybankName = "HDFC Bank";
        }else if(getrandomSelected == 8){
            smsSalaryList = Answers.Json8SalarySMSList;
            defaultSMSList = Answers.Json8DefaultSMS;
            existingEMIList = Answers.Json8ExisitngEMI;
            salarybankName = "State Bank Of India";
        }else if(getrandomSelected == 9){
            smsSalaryList = Answers.Json9SalarySMSList;
            defaultSMSList = Answers.Json9DefaultSMS;
            existingEMIList = Answers.Json9ExisitngEMI;
            salarybankName = "HDFC Bank";
        }else{
            smsSalaryList = Answers.Json10SalarySMSList;
            defaultSMSList = Answers.Json10DefaultSMS;
            existingEMIList = Answers.Json10ExisitngEMI;
            salarybankName = "YES Bank";
        }


        for(int i=0;i<smsSalaryList.length;i++){
                SmsParsedBankData smsParsedBankData = new SmsParsedBankData();
                smsParsedBankData.setSalarySMS(smsSalaryList[i]);
                dataList.add(smsParsedBankData);
        }

        adapter = new AdapterSalarySMSList(mActivity, mActivity, dataList);
        rvSalarySmsRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
