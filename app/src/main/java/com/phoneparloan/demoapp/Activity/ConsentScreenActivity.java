package com.phoneparloan.demoapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phoneparloan.demoapp.Objects.PermissionPojo;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Log;
import com.phoneparloan.demoapp.Utils.Utils;
import com.phoneparloan.demoapp.adapters.PermissionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsentScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    Context context;
    public static final int MY_PERMISSIONS_REQUEST = 123;
    PermissionsAdapter permissionsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<PermissionPojo> arrayListData;
    TextView privacyPolicyText;
    public static String permissionsArray[] = {Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS};
    private static String TAG = ConsentScreenActivity.class.getName();
    Activity mActivity;
    LinearLayout finboxLayout;
    Button acceptBtn, declineBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.consent_screen_activity);
        mActivity = ConsentScreenActivity.this;
        context = ConsentScreenActivity.this;
        init();
        super.onCreate(savedInstanceState);
    }

    int REQUEST_CODE_ASK_PERMISSIONS = 11;

    /*private boolean hasPermission() {

        if (Utils.hasPermissions(ConsentScreenActivity.this, ConsentScreenActivity.permissionsArray)) {
            return true;
        } else {
            //init();
            return false;
        }
    }*/

    private void setDataToArrayList(ArrayList<PermissionPojo> arrayListData) {
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_textsms_24px),
                getResources().getString(R.string.SmsPermission),
                getResources().getString(R.string.SMSPermissionDescription)));
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_smartphone_24px),
                getResources().getString(R.string.PhonePermission),
                getResources().getString(R.string.PhonePermissionDescription)));
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_contacts_24px),
                getResources().getString(R.string.ContactsPermission),
                getResources().getString(R.string.ContactsPermissionDescription)));
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_location_on_24px),
                getResources().getString(R.string.LocationPermission),
                getResources().getString(R.string.LocationPermissionDescription)));
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_storage_24px),
                getResources().getString(R.string.StoragePermission),
                getResources().getString(R.string.StoragePermissionDescription)));
        arrayListData.add(new PermissionPojo(getResources().getDrawable(R.drawable.ic_baseline_account_circle_24px),
                getResources().getString(R.string.AccountsPermission),
                getResources().getString(R.string.AccountsPermissionDescription)));
    }

    public void init() {

        arrayListData = new ArrayList<>();
        setDataToArrayList(arrayListData);
        recyclerView = findViewById(R.id.rvPermissions);
        context = ConsentScreenActivity.this;
        permissionsAdapter = new PermissionsAdapter(context, arrayListData);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(permissionsAdapter);
        privacyPolicyText = findViewById(R.id.privacyPolicyText);
        acceptBtn = findViewById(R.id.acceptButton);
        declineBtn = findViewById(R.id.declineButton);

        privacyPolicyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "http://www.phoneparloan.in/privacy";
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    //  builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
                    // builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(ConsentScreenActivity.this, Uri.parse(url));

                } catch (Exception e) {
                    Log.d("abhi_exception", e.toString());
                }
            }
        });



        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ask for Permissions here
                if (!hasPermissions()) {
                    GrantPermission();
                } else {
                    if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Xiaomi Device Detected");
                        alertDialogBuilder.setMessage(getResources().getString(R.string.please_check_all_permissions) + " " + " ?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Utils.onDisplayPopupPermissionXIAOMI(mActivity);
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Utils.checkAllPermission(mActivity, MY_PERMISSIONS_REQUEST)) {
                                    dialog.dismiss();
                                    //pickGoogleAccount();
                                }
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    Intent intent = new Intent(ConsentScreenActivity.this,BankSeggregationActivity.class);
                    startActivity(intent);
                    finish();

                    //Toast.makeText(context, "Done with Consent work", Toast.LENGTH_SHORT).show();

                }
            }
        });

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //super.onBackPressed();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage(getResources().getString(R.string.register_back_Kill));
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finish();
                                    finishAffinity();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });


    }

    public void GrantPermission() {
        if (checkAndRequestPermissions()) {
           //
        } else {
            //Do nothing
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                try {
                   /* for (int res : grantResults) {
                        if (!(res == PackageManager.PERMISSION_GRANTED)) {
                            Utils.startActivity(mActivity, SorryActivity.class);
                            return;
                        }
                    }*/
                    //startStepActivity(null);

                    if (hasPermissions()) {
                        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            alertDialogBuilder.setTitle("Xiaomi Device Detected");
                            alertDialogBuilder.setMessage(getResources().getString(R.string.please_check_all_permissions) + " " + " ?");
                            alertDialogBuilder.setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Utils.onDisplayPopupPermissionXIAOMI(mActivity);
                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Utils.checkAllPermission(mActivity, MY_PERMISSIONS_REQUEST)) {
                                        dialog.dismiss();
                                        //pickGoogleAccount();
                                    }
                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                        Intent intent = new Intent(ConsentScreenActivity.this,BankSeggregationActivity.class);
                        startActivity(intent);
                        finish();
                        // new LongOperation(this).execute("");
                        //pickGoogleAccount();
                        break;
                    } else {
                        Toast.makeText(context, "Please allow all the permissions to proceed further", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);//1
        //int accountsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);//2
        int phoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);//3
        int storageReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);//4
        int storageWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);//4
        int locationPermissionFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);//5
        int locationPermissionCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);//5
        int readContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);//6
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionSendMessage != 0) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (phoneStatePermission != 0) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (storageReadPermission != 0 || storageWritePermission != 0) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (locationPermissionCoarse != 0 || locationPermissionFine != 0) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (readContactsPermission != 0) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }

    public boolean hasPermissions() {
        if (ConsentScreenActivity.permissionsArray != null) {
            for (String permission : ConsentScreenActivity.permissionsArray) {
                if (ActivityCompat.checkSelfPermission(ConsentScreenActivity.this, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}

