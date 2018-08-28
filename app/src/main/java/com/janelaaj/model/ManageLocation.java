package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 12/06/18.
 */

public class ManageLocation {

    private String status;

    public String getStatus() {
        return status;
    }

     public ArrayList<LOC> locations;

    public ArrayList<LOC> getLocations() {
        return locations;
    }

    public class LOC{
        public String lname;
        public String lflagservice;
        public String dlmid;
        public String ladrline1;
        public String lcity;
        public String lid;
        public String did;

        public String getLname() {
            return lname;
        }

        public String getLflagservice() {
            return lflagservice;
        }

        public String getDlmid() {
            return dlmid;
        }

        public String getLadrline1() {
            return ladrline1;
        }

        public String getLcity() {
            return lcity;
        }

        public String getLid() {
            return lid;
        }

        public String getDid() {
            return did;
        }
    }
}
