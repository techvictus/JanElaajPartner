package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 03/07/18.
 */

public class getServicesDetailsModel {

    public String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<SERVICE> info;

    public ArrayList<SERVICE> getServicesNames() {
        return info;
    }

    public class SERVICE{
        public String sid;
        public String sname;

        public String getSName() {
            return sname;
        }
        public String getSid() {
            return sid;
        }


    }




}
