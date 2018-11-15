package com.example.smith.arcs2018.Retrofit;

import com.example.smith.arcs2018.Interface.ArcsApiInterface;

/**
 * Created by SMITH on 28-Jan-18.
 */

public class Utils {
    private Utils(){}
    public static String BASE_URL = "https://arcs-api.herokuapp.com/";
    public static ArcsApiInterface getInterface(){
        return RetrofitClient.getRetrofitClient(BASE_URL).create(ArcsApiInterface.class);
    }
}
