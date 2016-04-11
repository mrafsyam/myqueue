package com.nextslides.queue;

import android.app.Application;

public class ServerURL extends Application {

    String url_server = "http://10.194.123.233:8080/";
    String url_getCurrentNo = url_server + "get_current_num.php";
    String url_getLogin = url_server + "get_login.php";
    String url_CheckIn = url_server + "post_check_in.php";

    //URL getters
    public String getUrl_getCurrentNo() {
        return url_getCurrentNo;
    }

    public String getUrl_getLogin() {
        return url_getLogin;
    }

    public String getUrl_CheckIn() {
        return url_CheckIn;
    }
}