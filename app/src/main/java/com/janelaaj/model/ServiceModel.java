package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by pratyushsingh on 19/06/18.
 */

public class ServiceModel {

    String status;
    ArrayList<info> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ServiceModel.info> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<ServiceModel.info> info) {
        this.info = info;
    }

    public class info{
        String sid;
        String sname;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }
    }

}
