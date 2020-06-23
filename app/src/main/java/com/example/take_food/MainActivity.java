package com.example.take_food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*接收发送定义的常量*/
    private String mIp = "192.168.137.1";
    private int mPort = 8080;
    private SendThread sendthread;
    String receive_Msg;
    String l;
    String total0,total1,total2,total3;
    private Button button0;
    private Button button1;
    public ImageButton ibt_yxrs;
    public ImageButton ibt_gbjd;
    public ImageButton ibt_xcr;
    public ImageButton ibt_jjrs;
    public TextView tv_menu;
    public TextView tv_rx;
    public boolean[] food_flag = {false, false, false, false};
    static PrintWriter mPrintWriterClient = null;
    static BufferedReader mBufferedReaderClient	= null;
    Switch switch1,switch2;
    /*****************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.take_food);
         ibt_yxrs=findViewById(R.id.ibt_yxrs);
         ibt_gbjd=findViewById(R.id.ibt_gbjd);
         ibt_xcr=findViewById(R.id.ibt_xcr);
         ibt_jjrs=findViewById(R.id.ibt_jjrs);
         tv_menu = findViewById(R.id.tv_menu);
         tv_rx = findViewById(R.id.tv_rx);
        //        button0= (Button)findViewById(R.id.Water_W_N);
        //        button0.setOnClickListener(button0ClickListener);
        //        button1= (Button)findViewById(R.id.Water_W_O);
        //        button1.setOnClickListener(button1ClickListener);

        /***************连接*****************/
        sendthread = new SendThread(mIp, mPort, mHandler);
        Thread1();
        new Thread().start();
        /**********************************/

//        switch1 = (Switch) findViewById(R.id.Switch1);
//        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                // TODO Auto-generated method stub
//                if (isChecked) {
//                    sendthread.send("A");
//                } else {
//                    sendthread.send("B");
//                }
//            }
//
//        });
//
//        switch2 = (Switch) findViewById(R.id.Switch2);
//        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                // TODO Auto-generated method stub
//                if (isChecked) {
//                    sendthread.send("C");
//                } else {
//                    sendthread.send("D");
//                }
//            }
//
//        });
    }
    public void display_tx_menu()
    {
        String str="" ;
        if(food_flag[0])str= str.concat("鱼香肉丝|");
        if(food_flag[1])str= str.concat("宫保鸡丁|");
        if(food_flag[2])str= str.concat("小炒肉|");
        if(food_flag[3])str= str.concat("尖椒炒肉|");
        tv_menu.setText(str);
    }
    public void display_rx_menu(String menu)
    {
        String str="" ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        if(menu.indexOf("menu1:ok")!=-1)str=str.concat("鱼香肉丝完成|");
        if(menu.indexOf("menu2:ok")!=-1)str=str.concat("宫保鸡丁完成|");
        if(menu.indexOf("menu3:ok")!=-1)str=str.concat("小炒肉完成|");
        if(menu.indexOf("menu4:ok")!=-1)str=str.concat("尖椒炒肉完成|");
        l=l.format("%s:%s",simpleDateFormat.format(date),str);
        tv_rx.setText(l);
    }
    public void yuxiangrousi(View view) {
        food_flag[0] = !food_flag[0];
        if(food_flag[0]==true)
        {
            ibt_yxrs.setBackgroundColor(Color.parseColor("#4CB8FB"));
        }
        else
        {

            ibt_yxrs.setBackgroundColor(Color.parseColor("#416B6868"));
        }
        display_tx_menu();
    }
    public void gongbaojiding(View view) {
        food_flag[1] = !food_flag[1];
        if(food_flag[1]==true)
        {
            ibt_gbjd.setBackgroundColor(Color.parseColor("#4CB8FB"));
        }
        else
        {

            ibt_gbjd.setBackgroundColor(Color.parseColor("#416B6868"));
        }
        display_tx_menu();
    }
    public void xiaochaorou(View view) {
        food_flag[2] = !food_flag[2];
        if(food_flag[2]==true)
        {
            ibt_xcr.setBackgroundColor(Color.parseColor("#4CB8FB"));
        }
        else
        {

            ibt_xcr.setBackgroundColor(Color.parseColor("#416B6868"));
        }
        display_tx_menu();
    }

    public void jianjiaorousi(View view) {
        food_flag[3] = !food_flag[3];
        if(food_flag[3]==true)
        {
            ibt_jjrs.setBackgroundColor(Color.parseColor("#4CB8FB"));
        }
        else
        {

            ibt_jjrs.setBackgroundColor(Color.parseColor("#416B6868"));
        }
        display_tx_menu();
    }

    public void take_menu(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        //time1.setText("Date获取当前日期时间"+simpleDateFormat.format(date));
        String str = null;
        str=str.format("%s:menu1:%s,menu2:%s,menu3:%s,menu4:%s",simpleDateFormat.format(date),String.valueOf(food_flag[0]),String.valueOf(food_flag[1]),String.valueOf(food_flag[2]),String.valueOf(food_flag[3]));
        sendthread.send(str);
        tv_menu.setText(simpleDateFormat.format(date)+"下单成功");
    }
    //    private View.OnClickListener button0ClickListener = new View.OnClickListener() {
    //        public void onClick(View arg0) {
    //            mPrintWriterClient.print("j");
    //            mPrintWriterClient.flush();
    //
    //        }
    //    };
    //    private View.OnClickListener button1ClickListener = new View.OnClickListener() {
    //        public void onClick(View arg0) {
    //            mPrintWriterClient.print("m");
    //            mPrintWriterClient.flush();
    //        }
    //    };


    private class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }


    /*接收线程*******************************************************************************/
    /**
     * 开启socket连接线程
     */
    void Thread1(){
        //        sendthread = new SendThread(mIp, mPort, mHandler);
        new Thread(sendthread).start();//创建一个新线程
    }

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            TextView text0 = (TextView)findViewById(R.id.ttv2);
            super.handleMessage(msg);
            if (msg.what == 0x00) {
                Log.i("mr_收到的数据： ", msg.obj.toString());
                receive_Msg = msg.obj.toString();
                display_rx_menu(receive_Msg);
            }
        }
    };
}
