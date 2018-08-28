package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by Arshil on 30/07/18.
 */

public class allDetailsFetchModel {

    public String getStatus() {
        return status;
    }

    public String status;

    public ArrayList<allDetailsFetchModel.information> getAllInfo(){
        return info;
    }

    public ArrayList<information> info;


    public class information
    {
        public String getCurlocdflag() {
            return curlocdflag;
        }

        public String getDldmid() {
            return dldmid;
        }

        public String getSid() {
            return sid;
        }

        public String getNamt() {
            return namt;
        }

        public String getDflag() {
            return dflag;
        }

        public String getDamt() {
            return damt;
        }

        public String getDcsmid() {
            return dcsmid;
        }

        public String getDofferflag() {
            return dofferflag;
        }

        public String getDltmid() {
            return dltmid;
        }

        public String curlocdflag;
        public String dldmid;
        public String sid;
        public String namt;
        public String dflag;
        public String damt;
        public String dcsmid;
        public String dofferflag;
        public String dltmid;
    }
}
