package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 31/07/18.
 */

public class specializationModel {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<specializationModel.sparr> getSparr() {
        return sparr;
    }

    public void setSparr(ArrayList<specializationModel.sparr> sparr) {
        this.sparr = sparr;
    }

    public String status;
    ArrayList<sparr> sparr;
    public class sparr{

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String id;
        String name;

    }
}
