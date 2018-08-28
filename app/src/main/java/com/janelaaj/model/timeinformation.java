package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 18/06/18.
 */

public class timeinformation {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<timeinformation.info> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<timeinformation.info> info) {
        this.info = info;
    }

    public String status;
    public ArrayList<info> info;
    public class info{
        public String getMondayid() {
            return mondayid;
        }

        public void setMondayid(String mondayid) {
            this.mondayid = mondayid;
        }

        public ArrayList<time> getMonday() {
            return monday;
        }

        public void setMonday(ArrayList<time> monday) {
            this.monday = monday;
        }

        public String getTuesdayid() {
            return tuesdayid;
        }

        public void setTuesdayid(String tuesdayid) {
            this.tuesdayid = tuesdayid;
        }

        public ArrayList<time> getTuesday() {
            return tuesday;
        }

        public void setTuesday(ArrayList<time> tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesdayid() {
            return wednesdayid;
        }

        public void setWednesdayid(String wednesdayid) {
            this.wednesdayid = wednesdayid;
        }

        public ArrayList<time> getWednesday() {
            return wednesday;
        }

        public void setWednesday(ArrayList<time> wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursdayid() {
            return thursdayid;
        }

        public void setThursdayid(String thursdayid) {
            this.thursdayid = thursdayid;
        }

        public ArrayList<time> getThursday() {
            return thursday;
        }

        public void setThursday(ArrayList<time> thursday) {
            this.thursday = thursday;
        }

        public String getFridayid() {
            return fridayid;
        }

        public void setFridayid(String fridayid) {
            this.fridayid = fridayid;
        }

        public ArrayList<time> getFriday() {
            return friday;
        }

        public void setFriday(ArrayList<time> friday) {
            this.friday = friday;
        }

        public String getSaturdayid() {
            return saturdayid;
        }

        public void setSaturdayid(String saturdayid) {
            this.saturdayid = saturdayid;
        }

        public ArrayList<time> getSaturday() {
            return saturday;
        }

        public void setSaturday(ArrayList<time> saturday) {
            this.saturday = saturday;
        }

        public String getSundayid() {
            return sundayid;
        }

        public void setSundayid(String sundayid) {
            this.sundayid = sundayid;
        }

        public ArrayList<time> getSunday() {
            return sunday;
        }

        public void setSunday(ArrayList<time> sunday) {
            this.sunday = sunday;
        }


        String mondayid;
        ArrayList<time> monday;
        String tuesdayid;
        ArrayList<time> tuesday;
        String wednesdayid;
        ArrayList<time> wednesday;
        String thursdayid;
        ArrayList<time> thursday;
        String fridayid;
        ArrayList<time> friday;
        String saturdayid;
        ArrayList<time> saturday;
        String sundayid;
        ArrayList<time> sunday;
        public class time{

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getTimeid() {
                return timeid;
            }

            public void setTimeid(String timeid) {
                this.timeid = timeid;
            }

            public String from;
            public String to;
            public String timeid;
        }
    }

}
