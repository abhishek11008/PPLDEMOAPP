package com.phoneparloan.demoapp.Objects;

import java.util.ArrayList;

public class SmsParsedBankData {
    private String bankName="";
    private String bankImage = "";
    private String salarySMS = "";
    private String LastBal ="";
    private String LastMsgDateTimeStamp ="";
    private ArrayList majorChunkTxnMessagesList = new ArrayList();


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankImage() {
        return bankImage;
    }

    public void setBankImage(String bankImage) {
        this.bankImage = bankImage;
    }

    public String getLastBal() {
        return LastBal;
    }

    public void setLastBal(String lastBal) {
        LastBal = lastBal;
    }

    public String getLastMsgDateTimeStamp() {
        return LastMsgDateTimeStamp;
    }

    public void setLastMsgDateTimeStamp(String lastMsgDateTimeStamp) {
        LastMsgDateTimeStamp = lastMsgDateTimeStamp;
    }

    public ArrayList getMajorChunkTxnMessagesList() {
        return majorChunkTxnMessagesList;
    }

    public void setMajorChunkTxnMessagesList(ArrayList majorChunkTxnMessagesList) {
        this.majorChunkTxnMessagesList = majorChunkTxnMessagesList;
    }

    public String getSalarySMS() {
        return salarySMS;
    }

    public void setSalarySMS(String salarySMS) {
        this.salarySMS = salarySMS;
    }
}
