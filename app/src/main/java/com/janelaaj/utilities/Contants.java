package com.janelaaj.utilities;

import android.os.Environment;

/**
 * Created by Neeraj on 3/8/2017.
 */
public class Contants {
    public static final Boolean IS_DEBUG_LOG = true;

    public static final String LOG_TAG = "JanElaajLog";
    public static final String APP_NAME = "appName"; // Do NOT change this value; meant for user preference

    public static final String DEFAULT_APPLICATION_NAME = "janelaaj";

    public static final String APP_DIRECTORY = "/E" + DEFAULT_APPLICATION_NAME + "Directory/";
    public static final String DATABASE_NAME = "janelaaj.db";// Environment.getExternalStorageDirectory() +  APP_DIRECTORY + "epharma_client.db";


    public static String SERVICE_BASE_URL = " http://35.200.243.43:3000/";

    public static String outputBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String outputDirectoryLocation = outputBasePath + "/janelaajUnzipped/";


    public static boolean isProd_PN_TOKEN = true;
    public static final String SENDER = "HP-MOBKNT";
    public static final int LIST_PAGE_SIZE = 100;
    public static String InternetMessage = "Oops! You are Offline. Please check your Internet Connection.";
    public static final String BAD_NETWORK_MESSAGE = "We are trying hard to get latest content but the network seems to be slow. " +
            "You may continue to use app and get latest with in the app itself. ";

    public static final String OFFLINE_MESSAGE = "Oops! You are Offline. Please check your Internet Connection.";
    public static final String Error = "Server Side error!";
    public static final String Send_Otp = "https://www.txtguru.in/imobile/api.php?username=techvictus&password=97058255&source=JNELAJ&dmobile=91";
    public static final String numberVerify = "numberverify";
    public static final String registeruser = "registeruser";
    public static final String login = "signin";
    public static final String checkpoint2 = "fetchcheckpoint";
    public static final String clinicaddlocation = "clinicaddlocation";
    public static final String managelocation = "managelocation";
    public static final String timing = "timeinsert";
    public static final String timeinformation = "timeinformation";
    public static final String checkpoint = "checkpoint";
    public static final String services = "serviceinfo";
    public static final String oneview = "oneviewinfo";
    public static final String quicksets = "alllocdis";
    public static final String quicksetscurrent = "currentlocdis";
    public static final String profilingcomplete = "updateproffesion";
    public static final String chooseLocation = "chooselocation";
    public static final String all_information = "allinformation";
    public static final String update_information = "updateintroduction";
    public static final String update_information2 = "updatepicture";
    public static final String TimmingEditActivityFromHome = "fettimings2";
    public static final String deleteTimming = "deletetime";
    public static final String getServicesNames = "serviceinfo";
    public static final String getServicesNames2 = "vitalservicesinfo";
    public static final String getServicesInformation = "serviceselected";
    public static final String addNewService = "serviceinsert";
    public static final String updateService = "updateservice";
    public static final String deleteService = "deleteservice";
    public static final String ManageDiscount = "managediscount";
    public static final String updateManageDiscount = "updatemanagediscount";
    public static final String getLocationData = "fetchlocation";
    public static final String updateLocation = "updatelocation";
    public static final String checkIfTimingExist = "iftimeexist";
    public static final String uploadDocumentFetch = "getdetails";
    public static final String forgotPassword = "fpnumberpresent";
    public static final String changePassword = "passwordupdate";
    public static final String signUpService2 = "vitalregiteruser";
    public static final String vitalsignupinfo = "vitalsignupinfo";
    public static final String vitalprofilingcomple = "vitalprofilingcomple";
    public static final String checkIfDetailExist = "patientnumberidinfo";
    public static final String featurePhoneUserIDGenerate = "getpatientid";
    public static final String userIDDataFetch = "patientidinfo";
    public static final String getpatientid = "getpatientid";
    public static final String getDependentDetails = "patientdependent";
    public static final String dataEntryone = "registerpatientdep";
    public static final String dataEntrytwo = "insertappointment";
    public static final String allDetailsFetch = "getdiscountinfo";
    public static final String quicksethelp = "quicksethelp";
    public static final String allspecialization = "getspeciality";
    public static final String imagefetch = "getimage";

}



