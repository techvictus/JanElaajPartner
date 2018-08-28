package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 20/06/18.
 */

public class OneViewModel {

    String status;
    ArrayList<timeinfo> timeinfo;
    ArrayList<serviceinfo> serviceinfo;

    public class timeinfo{

        public String getMondayflag() {
            return mondayflag;
        }

        public void setMondayflag(String mondayflag) {
            this.mondayflag = mondayflag;
        }

        public ArrayList<time> getMonday() {
            return monday;
        }

        public void setMonday(ArrayList<time> monday) {
            this.monday = monday;
        }

        public String getTuesdayflag() {
            return tuesdayflag;
        }

        public void setTuesdayflag(String tuesdayflag) {
            this.tuesdayflag = tuesdayflag;
        }

        public ArrayList<time> getTuesday() {
            return tuesday;
        }

        public void setTuesday(ArrayList<time> tuesday) {
            this.tuesday = tuesday;
        }

        public String getWednesdayflag() {
            return wednesdayflag;
        }

        public void setWednesdayflag(String wednesdayflag) {
            this.wednesdayflag = wednesdayflag;
        }

        public ArrayList<time> getWednesday() {
            return wednesday;
        }

        public void setWednesday(ArrayList<time> wednesday) {
            this.wednesday = wednesday;
        }

        public String getThursdayflag() {
            return thursdayflag;
        }

        public void setThursdayflag(String thursdayflag) {
            this.thursdayflag = thursdayflag;
        }

        public ArrayList<time> getThursday() {
            return thursday;
        }

        public void setThursday(ArrayList<time> thursday) {
            this.thursday = thursday;
        }

        public String getFridayflag() {
            return fridayflag;
        }

        public void setFridayflag(String fridayflag) {
            this.fridayflag = fridayflag;
        }

        public ArrayList<time> getFriday() {
            return friday;
        }

        public void setFriday(ArrayList<time> friday) {
            this.friday = friday;
        }

        public String getSaturdayflag() {
            return saturdayflag;
        }

        public void setSaturdayflag(String saturdayflag) {
            this.saturdayflag = saturdayflag;
        }

        public ArrayList<time> getSaturday() {
            return saturday;
        }

        public void setSaturday(ArrayList<time> saturday) {
            this.saturday = saturday;
        }

        public String getSundayflag() {
            return sundayflag;
        }

        public void setSundayflag(String sundayflag) {
            this.sundayflag = sundayflag;
        }

        public ArrayList<time> getSunday() {
            return sunday;
        }

        public void setSunday(ArrayList<time> sunday) {
            this.sunday = sunday;
        }

        String mondayflag;
        ArrayList<time> monday;
        String tuesdayflag;
        ArrayList<time> tuesday;
        String wednesdayflag;
        ArrayList<time> wednesday;
        String thursdayflag;
        ArrayList<time> thursday;
        String fridayflag;
        ArrayList<time> friday;
        String saturdayflag;
        ArrayList<time> saturday;
        String sundayflag;
        ArrayList<time> sunday;

        public class time{
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

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            String to;
            String from;
            String flag;
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OneViewModel.timeinfo> getTimeinfo() {
        return timeinfo;
    }

    public void setTimeinfo(ArrayList<OneViewModel.timeinfo> timeinfo) {
        this.timeinfo = timeinfo;
    }

    public ArrayList<OneViewModel.serviceinfo> getServiceinfo() {
        return serviceinfo;
    }

    public void setServiceinfo(ArrayList<OneViewModel.serviceinfo> serviceinfo) {
        this.serviceinfo = serviceinfo;
    }

    public class serviceinfo{
        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getNamount() {
            return namount;
        }

        public void setNamount(String namount) {
            this.namount = namount;
        }

        public String getDamount() {
            return damount;
        }

        public void setDamount(String damount) {
            this.damount = damount;
        }

        String sname;
        String namount;
        String damount;
    }

}
