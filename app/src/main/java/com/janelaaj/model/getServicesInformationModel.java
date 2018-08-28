package com.janelaaj.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by apple on 03/07/18.
 */

public class getServicesInformationModel implements Serializable {


    public String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<SERVICE> serviceinfo;

    public ArrayList<SERVICE> getServicesInformation() {
        return serviceinfo;
    }

    public class SERVICE
    {

        public String namount;
        public String sname;
        public String damount;
        public String flag;
        public String dcsmid;


        public String getNormalAmount() {
            return namount;
        }

        public String getDiscountAmount(){
            return damount;
        }

        public String getServiceName()
        {
            return sname;
        }

        public String getdcsmid()
        {
            return dcsmid;
        }

        public String IsDiscountOn()
        {
            return flag;
        }



    }
}
