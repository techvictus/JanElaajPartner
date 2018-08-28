package com.janelaaj.model;

import java.util.ArrayList;

/**
 * Created by apple on 17/07/18.
 */

public class Sign_in_Model2 {

    public String status;
    public String getStatus() {
        return status;
    }
    public ArrayList<Sign_in_Model2.info> getRole() {
        return role;
    }


    public ArrayList<info> role;



    public  class  info
    {
        public String getPld_role() {
            return role;
        }

        public String getPld_partner_id() {
            return roleid;
        }

        String role;
        String roleid;



    }


}
