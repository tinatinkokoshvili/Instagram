package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse models
        ParseObject.registerSubclass(IgPost.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wlcAnzeewkLYRXScCiJL6fsTtg8QbJOP8Y8IgphY")
                .clientKey("jHMyw77uCJtmKN3Z6Dpt5J3vSxoDKs55e3GBVW0E")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
