package com.jiayang.githubnotesdemo.Notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.jiayang.githubnotesdemo.R;

import java.lang.reflect.Method;

/**
 * Created by 张 奎 on 2017-11-12 13:52.
 */

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_nomal;
    private TextView tv_fold;
    private TextView tv_hang;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofification);

        tv_nomal = (TextView) findViewById(R.id.tv_nomal);
        tv_fold = (TextView) findViewById(R.id.tv_fold);
        tv_hang = (TextView) findViewById(R.id.tv_hang);
        radioButton1 = (RadioButton) findViewById(R.id.rb_public);
        radioButton2 = (RadioButton) findViewById(R.id.rb_private);
        radioButton3 = (RadioButton) findViewById(R.id.rb_secret);
        radioGroup = (RadioGroup) findViewById(R.id.rg_all);

        tv_nomal.setOnClickListener(this);
        tv_fold.setOnClickListener(this);
        tv_hang.setOnClickListener(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nomal:
                sendNomalNotification();
                break;
            case R.id.tv_fold:
                sendFoldNotification();
                break;

            case R.id.tv_hang:
                sendHangNotification();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNomalNotification() {

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JiaYang627"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        selectNotofovatiomLevel(builder);
        notificationManager.notify(0, builder.build());

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendFoldNotification() {

        // 部分手机可以需要两个手指同时按住通知栏 下拉。

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JiaYang627"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("折叠式通知");
        selectNotofovatiomLevel(builder);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_fold);
        Notification build = builder.build();
        build.bigContentView = remoteViews;
        notificationManager.notify(1, build);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendHangNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JiaYang627"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        selectNotofovatiomLevel(builder);
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, NotificationActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        notificationManager.notify(2, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectNotofovatiomLevel(Notification.Builder builder) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_public:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("public");
                break;
            case R.id.rb_private:
                builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                builder.setContentText("private");
                break;
            case R.id.rb_secret:
                builder.setVisibility(Notification.VISIBILITY_SECRET);
                builder.setContentText("secret");
                break;
            default:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("public");
                break;

        }
    }


}
