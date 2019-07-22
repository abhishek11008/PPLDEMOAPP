package com.phoneparloan.demoapp.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.google.gson.Gson;
import com.phoneparloan.demoapp.Objects.AppData;
import com.phoneparloan.demoapp.Objects.AppList;
import com.phoneparloan.demoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class Utils {

    public static String TAG = "Utils";
    private static Dialog mDialog;
    public static int randSelected;
    public static JSONObject selectedRandomJson;
    public static JSONArray smsArray = new JSONArray();
    public static JSONArray callLogArray = new JSONArray();
    public static JSONArray contactListArray = new JSONArray();
    public static JSONArray appListArray = new JSONArray();
    public static JSONArray deviceInfoArray = new JSONArray();
    public static JSONArray smsTransArray = new JSONArray();
    public static JSONArray smsCreditedArray = new JSONArray();
    public static JSONArray smsDebitedArray = new JSONArray();
    ArrayList<Object> gamblingMatchedAppArray = new ArrayList<>();
    ArrayList<Object> userAppArray = new ArrayList<>();
    ArrayList<Object> matchedLoanAppArray = new ArrayList<>();
    ArrayList<Object> matchedGamblingAppArray = new ArrayList<>();

    public static ArrayList<AppData> masterLoanappListArray = new ArrayList<>();
    public static ArrayList<AppData> masterGamblingappListArray = new ArrayList<>();


    public static String readJSONFromAsset(Activity activity, String filename) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static void onDisplayPopupPermissionXIAOMI(Context context) {

        try {
            // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(localIntent);


        } catch (Exception e) {
            try {
                // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(localIntent);


            } catch (Exception e1) {
                // Otherwise jump to application details
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkAllPermission(Activity mActivity, int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
        try {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (
                        ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_SMS)) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_SMS,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.GET_ACCOUNTS}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_SMS,
                                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else { // <M
                return true;
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }


    public static void showProgressbar(Context context) {
        try {
            mDialog = new Dialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_bar);
            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

    }


    public static void hideProgressbar(Activity activity) {
        try {
            if (activity != null) {
                if (!activity.isFinishing() && mDialog != null) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
            }
        } catch (Exception e) {
            // handle exception here
        }
    }


    public static String regex_amount = "[rR][sS]\\.?\\s[,\\d]+\\.?\\d{0,2}|[rR][sS]\\.?[,\\d]+\\.?\\d{0,2}|[iI][nN][rR]\\.?\\s*[,\\d]+\\.?\\d{0,2}|[aA][mM][tT]\\:?\\-?\\s*[,\\d]+\\:?\\-?\\d{0,2}/s";


    public static String regex_txn_sms_data = "(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Aa][Cc][Cc][Tt].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)";

    public static String[] senderId_keyword_array = {"AxisBk", "ANDBNK", "ALBANK", "ALBANK", "BOB", "BOBMSG", "BOBSMS", "BOBTXN", "BOI", "BOIIND", "MAHABK", "CANBNK", "CENTBK",
            "CUBANK", "CITIBANK", "CITIBK", "CORPBK", "DCBBNK", "HDFCBK", "HdfcBk", "ICICIB", "IDBIBK", "INDBNK", "IndBnk"
            , "IOB", "IOBCHN", "INDUSB", "KOTAKB", "OBCBNK", "PNBSMS", "PSBANK", "SYNBNK", "SYNMOB", "SARBNK", "SBIPSG", "SBIINB"
            , "CBSSBI", "SBIUPI", "ATMSBI", "GRCSBI", "UCO", "UCOBNK", "UnionB", "AIRBNK", "YESBNK", "VIJBNK", "VMOBIL", "FDRLBK", "FEDBNK", "DNABK", "RBLBK", "DIGIBK"
            , "DBSBNK", "CorpBk", "DHANBK", "BKDENA", "DenaBk", "KTKBNK", "KVBANK", "KVBUPI", "KVBMPY", "AUBANK", "FROMSC", "SGBSMS"
            , "RBLBNK", "ABBANK", "UMOBIL", "PAYTMB"};


    //This will be used to plug in the images accordingly
    public static String[] bankImagesMapping = {"axis.png", "andra.png", "allahbad.png", "allahbad.png", "barodra.png", "barodra.png", "barodra.png", "barodra.png", "bol.png", "bol.png", "mahra.png", "canra.png", "cantral.png",
            "cub.png", "citi.png", "citi.png", "coprate.png", "dcb.jpeg", "hdfc.png", "hdfc.png", "icici.png", "idbi.png", "indian.png", "indian.png"
            , "indianover.png", "indianover.png", "induslnd.png", "kotak.png", "obc.png", "pnb.png", "punjabsind.png", "syndicate.png", "syndicate.png", "saraswat.png", "sbi.png", "sbi.png"
            , "sbi.png", "sbi.png", "sbi.png", "sbi.png", "uco.png", "uco.png", "UnionB.png", "airtel.png", "yes.png", "vijaya.png", "vijaya.png", "fedral.png", "fedral.png", "dena.png", "rbl.png", "digi.png"
            , "dbs.png", "coprate.png", "dhanlaxmi.png", "dena.png", "dena.png", "karnatka.png", "karurvyas.png", "karurvyas.png", "karurvyas.png", "au.png", "sc.png", "sgb.png"
            , "rbl.png", "ab.png", "phpltqxli_1538390458.png", "phpPWMFTh_1538393042.png"};

    //This will be used to plug in the bank name images
    public static String[] bankNamesMapping = {"Axis Bank", "Andhra Bank", "Allahabad Bank", "Allahabad Bank", "Bank Of Baroda", "Bank Of Baroda", "Bank Of Baroda", "Bank Of Baroda", "Bank of India", "Bank of India", "Bank of Maharastra", "Canara Bank", "Central Bank Of India"
            , "City Union Bank", "Citi Bank", "Citi Bank", "Corporation Bank", "DCB Bank", "HDFC Bank", "HDFC Bank", "ICICI Bank", "IDBI Bank", "Indian Bank", "Indian Bank",
            "Indian Overseas Bank", "Indian Overseas Bank", "IndusInd Bank", "Kotak Mahindra Bank", "Oriental Bank Of Commerce", "Punjab National Bank", "Punjab Sind Bank", "Syndicate Bank", "Syndicate Bank", "Saraswat Bank", "State Bank Of India", "State Bank Of India"
            , "State Bank Of India", "State Bank Of India", "State Bank Of India", "State Bank Of India", "UCO Bank", "UCO Bank", "Union Bank of India", "Airtel Bank", "YES Bank", "Vijaya Bank", "Vijaya Bank", "Fedral Bank", "Fedral Bank", "Dena Bank", "RBL Bank", "DIGI Bank"
            , "DBS Bank", "corporation Bank", "Dhanlaxmi Bank", "DENA Bank", "DENA Bank", "Karnataka Bank", "Karur Vysya Bank", "Karur Vysya Bank", "Karur Vysya Bank", "AU Small Finance Bank", "Standard Chartered", "Saurashtra Gramin Bank",
            "RBL BANK", "AB Bank", "Union Bank", "Paytm"};

    public static String[] credit_keyword_array = {"credit", "credited", "credits", "credited by", "credited to your", "balance credited", "Deposited", "CREDITED"};
    public static String[] debit_keyword_array = {"txn of", "transaction of", "was spent", "withdrawn", "purchase", "purchase worth", "debit", "debit by transfer", "debited for", "debited", "debited with"};
    //public static String[] ecs_keyword_array = {"ecs", "insufficient fund", "maintain sufficient fund"};
    //public static String[] loan_keyword_array = {"loan", "loan emi", "loan due","emi paid","emi"};


    public void startLoanAppListWork(Activity context) {
        userAppArray = new ArrayList<>();
        for (int i = 0; i < appListArray.length(); i++) {
            try {
                AppData appData = new AppData();
                userAppArray.add(appListArray.getJSONObject(i).get("pakageNm"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String jsonLocation = readJSONFromAsset(context, "loan_app.json");
        try {
            AppList appList = (AppList) getObject(jsonLocation, AppList.class);
            masterLoanappListArray = (ArrayList<AppData>) appList.getData();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getLoanAppData() {
        ArrayList<Object> masterAppArray = new ArrayList<>();


        for (int i = 0; i < masterLoanappListArray.size(); i++) {
            masterAppArray.add(masterLoanappListArray.get(i).getPackage_id());
            // Log.i("masterAppArray",masterAppArray.toString());
        }

        for (Object temp : userAppArray) {
            matchedLoanAppArray.add(masterAppArray.contains(temp) ? "1" : "0");
            // Log.i("matchArray",matchedAppArray.toString());
        }
        int financialAppCount = Collections.frequency(matchedLoanAppArray, "1");
        return matchedLoanAppArray;
    }


    public void startGamblingAppListWork(Activity context) {
        userAppArray =new ArrayList<>();
        for (int i = 0; i < appListArray.length(); i++) {
            try {
                AppData appData = new AppData();
                userAppArray.add(appListArray.getJSONObject(i).get("pakageNm"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String jsonLocation = readJSONFromAsset(context, "gambling_app.json");
        try {
            AppList appList = (AppList) getObject(jsonLocation, AppList.class);
            masterGamblingappListArray = (ArrayList<AppData>) appList.getData();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Object> getGamblingAppData() {
        ArrayList<Object> masterAppArray = new ArrayList<>();

        for (int i = 0; i < masterGamblingappListArray.size(); i++) {
            masterAppArray.add(masterGamblingappListArray.get(i).getPackage_id());
            // Log.i("masterAppArray",masterAppArray.toString());
        }

        for (Object temp : userAppArray) {
            matchedGamblingAppArray.add(masterAppArray.contains(temp) ? "1" : "0");
            // Log.i("matchArray",matchedAppArray.toString());
        }
        int gamblingAppCount = Collections.frequency(matchedGamblingAppArray, "1");
        return matchedLoanAppArray;
    }



    public static Object getObject(String json, Class<?> aClass) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, aClass);
    }


}
