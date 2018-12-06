package com.kingsoft.touchdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/12/6.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GeekPagerAdapter pagerAdapter;

    private LinkedList<Conversation> conversations = new LinkedList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new GeekPagerAdapter(getSupportFragmentManager(), conversations);
        viewPager.setAdapter(pagerAdapter);


        conversations.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        conversations.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        conversations.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        pagerAdapter.notifyDataSetChanged();

    }

    public void addConversation(Conversation conversation) {
        pagerAdapter.addItem(conversation);
    }

}
