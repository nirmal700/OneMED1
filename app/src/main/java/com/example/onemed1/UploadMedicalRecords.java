package com.example.onemed1;

public class UploadMedicalRecords {
    private String mTitle;
    private String mImageURI;
    private String mPatientID;

    public UploadMedicalRecords(String mTitle, String mImageURI, String mPatientID) {
        this.mTitle = mTitle;
        this.mImageURI = mImageURI;
        this.mPatientID = mPatientID;
    }

    public UploadMedicalRecords() {
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImageURI() {
        return mImageURI;
    }

    public void setmImageURI(String mImageURI) {
        this.mImageURI = mImageURI;
    }

    public String getmPatientID() {
        return mPatientID;
    }

    public void setmPatientID(String mPatientID) {
        this.mPatientID = mPatientID;
    }
}
