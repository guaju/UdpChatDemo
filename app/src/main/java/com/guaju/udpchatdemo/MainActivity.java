package com.guaju.udpchatdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText et;
    private Button bt;
    private TextView tv;

    InetAddress inetAddress;
    DatagramPacket dp;
    int port=7777;
    int serverPort=6666;
    DatagramSocket ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    private String info;
                    private InetAddress serverInetAddress;

                    @Override
                    public void run() {
                        try {
                            ds=new DatagramSocket();
                            serverInetAddress = InetAddress.getByName("192.168.38.164");
                            while(true){
                                String clientMsg = et.getText().toString().trim();
                                if (TextUtils.isEmpty(clientMsg)){
                                    return;
                                }
                                byte[] bytes = clientMsg.getBytes();
                                //发送接收数据
                                dp=new DatagramPacket(bytes,bytes.length,serverInetAddress,serverPort);
                                ds.send(dp);
                                byte[] buf = new byte[1024];
                                DatagramPacket dp2 = new DatagramPacket(buf,0, buf.length);
                                ds.receive(dp2);
                                info += new String(dp2.getData())+"\n";
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv.setText(info);
                                        et.setText("");
                                    }
                                });

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    private void initview() {
        et = (EditText) findViewById(R.id.et);
        bt = (Button)findViewById(R.id.bt);
        tv = (TextView)findViewById(R.id.tv);

    }
}
