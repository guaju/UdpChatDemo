package com.guaju.udpchatdemo;

import android.app.Application;

import java.io.IOException;

/**
 * Created by guaju on 2017/11/3.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UdpServer udpServer = new UdpServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
