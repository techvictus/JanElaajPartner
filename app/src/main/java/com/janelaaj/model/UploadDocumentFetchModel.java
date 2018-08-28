package com.janelaaj.model;

/**
 * Created by apple on 13/07/18.
 */

public class UploadDocumentFetchModel {
    public String status;

    public String getAadhar_number() {
        return aadhar_number;
    }

    public String getVoterid_number() {
        return voterid_number;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public String getPassport_flag() {
        return passport_flag;
    }

    public String getAadhar_flag() {
        return aadhar_flag;
    }

    public String getVoterid_flag() {
        return voterid_flag;
    }

    public String getMbbs_flag() {
        return mbbs_flag;
    }

    public String getMd_flag() {
        return md_flag;
    }

    public String getMs_flag() {
        return ms_flag;
    }

    public String getDiploma_flag() {
        return diploma_flag;
    }

    public String aadhar_number;
    public String voterid_number;
    public String passport_number;
    public String passport_flag;
    public String aadhar_flag;
    public String voterid_flag;
    public String mbbs_flag;
    public String md_flag;
    public String ms_flag;
    public String diploma_flag;



    public String getStatus() {
        return status;
    }

}
