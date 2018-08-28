package com.janelaaj.framework;

import android.content.Context;
import android.util.Log;

import com.janelaaj.utilities.Contants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Neeraj on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

    //call login data
    public void callLoginService(String phone, String token, final IAsyncWorkCompletedCallback workCompletedCallback) {

        final String url = Contants.SERVICE_BASE_URL;
        JSONObject obj = new JSONObject();
        try {
            obj.put("PhoneNumber", phone);
            obj.put("DeviceId", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    //parseAndSaveLoginData(result, workCompletedCallback);
                } else {
                    workCompletedCallback.onDone("loginService done", false);
                }
            }
        });
    }

    //parse and save login data
  /*  public void parseAndSaveLoginData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
        new AsyncTask<Void, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                Boolean flag = false;
                ContentData data = new Gson().fromJson(result, ContentData.class);
                Log.d(Contants.LOG_TAG, "---" + data);
                if (data != null) {
                    if (data.getData() != null) {
                        DbHelper dbhelper = new DbHelper(context);
                        dbhelper.upsertUserData(data.getData());
                        flag = true;
                    }
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                if (flag) {
                    workCompletedCallback.onDone("LoginService done", true);
                } else {
                    workCompletedCallback.onDone("LoginService done", false);
                }
            }
        }.execute();
    }
*/
    //call home data
  /*  public void callHomeService(final IAsyncWorkCompletedCallback workCompletedCallback) {

        final String url = Contants.SERVICE_BASE_URL + Contants.HomeData;
        new ServiceHelper().callService(url, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("homeService done", false);
                }
            }
        });
    }
*/
    public void IOTDataService(String url, final IAsyncWorkCompletedCallback workCompletedCallback) {

        new ServiceHelper().callService(url, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("OtpService done", false);
                }
            }
        });
    }

    public void sendOtpService(String url, final IAsyncWorkCompletedCallback workCompletedCallback) {

        new ServiceHelper().callService(url, null, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("OtpService done", false);
                }
            }
        });
    }

    public void userVerifyservice(String url, JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {

        new ServiceHelper().callService(url, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void SignUpService(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = Contants.SERVICE_BASE_URL + Contants.registeruser;
        new ServiceHelper().callService(url, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("SignUpService done", false);
                }
            }
        });
    }

    public void clinicaddlocation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.clinicaddlocation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void Timing(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.timing;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void ManageLocation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.managelocation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void checkPoint(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.checkpoint2;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void userLogin(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.login;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



    public void timeinformation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.timeinformation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void manageloctionchild(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.checkpoint;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



    public void services(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.services;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void oneview(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.oneview;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void quicksets(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.quicksets;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void profilingcomplete(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.profilingcomplete;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void chooseLocation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.chooseLocation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void dataEntryone(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.dataEntryone;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void allDetailsFetch(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.allDetailsFetch;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void quicksethelp(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.quicksethelp;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void vitalprofilingcomplete(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.vitalprofilingcomple;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void allspecialization(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.allspecialization;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void dataEntrytwo(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.dataEntrytwo;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void getDependentDetails(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getDependentDetails;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void getpatientid(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getpatientid;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void userIDDataFetch(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.userIDDataFetch;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void featurePhoneUserIDGenerate(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.featurePhoneUserIDGenerate;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void checkIfDetailExist(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.checkIfDetailExist;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void allinfo(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.all_information;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void updateInformation2(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.update_information2;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void updateInformation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.update_information;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void currentlocationdiscount(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.quicksetscurrent;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



    public void TimmingEditActivityFromHome(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.TimmingEditActivityFromHome;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void deleteTimming(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.deleteTimming;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void getServicesNames2(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getServicesNames2;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void getServicesNames(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getServicesNames;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void getServicesInformation(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getServicesInformation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void addNewService(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.addNewService;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



    public void updateService(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.updateService;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void deleteService(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.deleteService;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void ManageDiscount(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.ManageDiscount;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



    public void updateManageDiscount(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.updateManageDiscount;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void getLocationData(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.getLocationData;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void updateLocationData(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.updateLocation;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void checkIfTimingExist(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.checkIfTimingExist;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void uploadDocumentFetch(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.uploadDocumentFetch;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void ForgotPassword(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.forgotPassword;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void changePassword(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.changePassword;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void SignUpService2(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.signUpService2;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }


    public void vitalssignupinfo(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.vitalsignupinfo;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }

    public void imagefetch(JSONObject object, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url2 = Contants.SERVICE_BASE_URL + Contants.imagefetch;
        new ServiceHelper().callService(url2, object, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("UserVerifyService done", false);
                }
            }
        });
    }



}
