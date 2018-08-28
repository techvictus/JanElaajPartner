package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 04/07/18.
 */

public class ManageDiscountModel {

    public String status;

    public String getStatus() {
        return status;
    }


        public String getMondayid() {
            return mondayid;
        }

        public ArrayList<time> getMonday() {
            return monday;
        }

        public String getTuesdayid() {
            return tuesdayid;
        }


        public ArrayList<time> getTuesday() {
            return tuesday;
        }


        public String getWednesdayid() {
            return wednesdayid;
        }


        public ArrayList<time> getWednesday() {
            return wednesday;
        }


        public String getThursdayid() {
            return thursdayid;
        }



        public ArrayList<time> getThursday() {
            return thursday;
        }


        public String getFridayid() {
            return fridayid;
        }


        public ArrayList<time> getFriday() {
            return friday;
        }



        public String getSaturdayid() {
            return saturdayid;
        }


        public ArrayList<time> getSaturday() {
            return saturday;
        }

        public String getSundayid() {
            return sundayid;
        }

        public ArrayList<time> getSunday() {
            return sunday;
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

            public String getTo() {
                return to;
            }

            public String getDiscountflag() {
                return discountflag;
            }

            public String getTimeid() {
                return timeid;
            }

            public String from;
            public String to;
            public String discountflag;
            public String timeid;


        }
    }


