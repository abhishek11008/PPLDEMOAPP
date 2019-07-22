package com.phoneparloan.demoapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phoneparloan.demoapp.R;

public class ShowSalarySMSesActivity extends AppCompatActivity {

    String bankName="",bankImage ="";
    TextView bankNameText;
    ImageView bankImageView;
    RecyclerView rvSalarySmsRV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exact_salary_sms);
        bankName = getIntent().getStringExtra("bankName");
        bankImage = getIntent().getStringExtra("bankImage");
        init();
    }

    public void init(){
        bankNameText = findViewById(R.id.bankName);
        bankImageView = findViewById(R.id.bankImage);
        bankNameText.setText(bankName);
        Glide.with(ShowSalarySMSesActivity.this)
                .load(bankImage)
                .into(bankImageView);


    }
}
