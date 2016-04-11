package com.nextslides.queue;

import android.app.Application;

public class ServerURL extends Application {

    String url_server = "http://10.194.123.233:8080/";
    String url_getCurrentNo = url_server + "get_current_num.php";
    String url_getLogin = url_server + "get_login.php";
    String url_CheckIn = url_server + "post_check_in.php";
    String url_UpdateGCMToken = url_server + "post_update_gcm_token.php";

    /**
     * getCurrentNo URL getter
     */
    public String getUrl_getCurrentNo() {
        return url_getCurrentNo;
    }

    /**
     * getLogin URL getter
     */
    public String getUrl_getLogin() {
        return url_getLogin;
    }

    /**
     * CheckIn URL getter
     */
    public String getUrl_CheckIn() {
        return url_CheckIn;
    }

    /**
     * UpdateGCMToken URL getter
     */
    public String getUrl_UpdateGCMToken() {
        return url_UpdateGCMToken;
    }
}