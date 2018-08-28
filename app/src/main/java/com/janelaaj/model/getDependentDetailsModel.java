package com.janelaaj.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Arshil on 27/07/18.
 */

public class getDependentDetailsModel {

    public String getStatus() {
        return status;
    }

    public ArrayList<getDependentDetailsModel.getDetails> getGetDetails() {
        return dependents;
    }

    public String status;

    public ArrayList<getDetails> dependents;

    public class getDetails
    {
        public String getPdmid() {
            return pdmid;
        }

        public String getPatientid() {
            return patientid;
        }

        public String getDependentid() {
            return dependentid;
        }

        public String getName() {
            return name;
        }

        public String getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getPhoto() {
            return photo;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String pdmid;
        public String patientid;
        public String dependentid;
        public String name;
        public String dob;
        public String gender;
        public String photo;
        public String email;
        public String mobile;
    }
}
