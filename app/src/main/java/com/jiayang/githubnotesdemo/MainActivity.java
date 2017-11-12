package com.jiayang.githubnotesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jiayang.commonlibs.commonListAdapter.BaseAdapterHelper;
import com.jiayang.commonlibs.commonListAdapter.QuickAdapter;
import com.jiayang.githubnotesdemo.CardView.CardViewActivity;
import com.jiayang.githubnotesdemo.Notification.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private String[] strings = new String[]{"CardView" ,"Notification"};
    private QuickAdapter<Model> mAdapter;
    private List<Model> mStringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();
        mListView = (ListView) findViewById(R.id.listView);

        initAdapter();
        mListView.setAdapter(mAdapter);


    }

    private void initData() {
        for (int i = 0; i < strings.length; i++) {
            mStringList.add(new Model(strings[i], i));
        }

    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<Model>(this, R.layout.item_adapter, mStringList) {


            @Override
            protected void convert(BaseAdapterHelper helper, final Model item) {
                helper.setText(R.id.item, item.mName);
                helper.setOnClickListener(R.id.item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (item.mInt) {
                            case 0:
                                goToActivity(CardViewActivity.class);
                                break;
                            case 1:
                                goToActivity(NotificationActivity.class);
                                break;
                            default:
                                break;
                        }

                    }
                });
            }
        };
    }

    private void goToActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);

    }

}
