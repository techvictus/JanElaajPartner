package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 29/06/18.
 */

public class TimmingEditActivityFromHomeModel {

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<TimmingEditActivityFromHomeModel.alltimings> getAlltimings() {
        return alltimings;
    }

    public void setAlltimings(ArrayList<TimmingEditActivityFromHomeModel.alltimings> alltimings) {
        this.alltimings = alltimings;
    }

    public String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<alltimings> alltimings;

    public class alltimings{

        public int getMonid() {
            return monid;
        }

        public void setMonid(int monid) {
            this.monid = monid;
        }

        public int getTueid() {
            return tueid;
        }

        public void setTueid(int tueid) {
            this.tueid = tueid;
        }

        public int getWedid() {
            return wedid;
        }

        public void setWedid(int wedid) {
            this.wedid = wedid;
        }

        public int getThuid() {
            return thuid;
        }

        public void setThuid(int thuid) {
            this.thuid = thuid;
        }

        public int getFriid() {
            return friid;
        }

        public void setFriid(int friid) {
            this.friid = friid;
        }

        public int getSatid() {
            return satid;
        }

        public void setSatid(int satid) {
            this.satid = satid;
        }

        public int getSunid() {
            return sunid;
        }

        public void setSunid(int sunid) {
            this.sunid = sunid;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMon() {
            return mon;
        }

        public void setMon(String mon) {
            this.mon = mon;
        }

        public String getTue() {
            return tue;
        }

        public void setTue(String tue) {
            this.tue = tue;
        }

        public String getWed() {
            return wed;
        }

        public void setWed(String wed) {
            this.wed = wed;
        }

        public String getThu() {
            return thu;
        }

        public void setThu(String thu) {
            this.thu = thu;
        }

        public String getFri() {
            return fri;
        }

        public void setFri(String fri) {
            this.fri = fri;
        }

        public String getSat() {
            return sat;
        }

        public void setSat(String sat) {
            this.sat = sat;
        }

        public String getSun() {
            return sun;
        }

        public void setSun(String sun) {
            this.sun = sun;
        }


        String to;
        String from;
        int monid;
        int tueid;
        int wedid;
        int thuid;
        int friid;
        int satid;
        int sunid;
        String mon;
        String tue;
        String wed;
        String thu;
        String fri;
        String sat;
        String sun;
    }

}
