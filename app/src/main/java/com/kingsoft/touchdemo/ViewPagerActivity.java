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

    private ViewPager mViewPager;
    private GeekPagerAdapter mPagerAdapter;
    private LinkedList<Conversation> mConversationList = new LinkedList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = findViewById(R.id.viewPager);
        mPagerAdapter = new GeekPagerAdapter(getSupportFragmentManager(), mConversationList);
        mViewPager.setAdapter(mPagerAdapter);
        PagerFragment.sIndex = 0;

        generateDataSet();
        mPagerAdapter.swapDataSet(mConversationList);
        mViewPager.setCurrentItem(3);
//        mViewPager.setOffscreenPageLimit(2);
//        mPagerAdapter.notifyDataSetChanged();

    }

    private void generateDataSet() {


        mConversationList.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        mConversationList.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        mConversationList.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
        mConversationList.add(new Conversation(String.valueOf("ID: " + PagerFragment.addAndGet()), " name: "));
    }


    public void addConversation(Conversation conversation) {
//        mPagerAdapter.addFirst(conversation);
//        mPagerAdapter.addLast(conversation);
//        mPagerAdapter.add(mViewPager.getCurrentItem(), conversation);


    }

    public void removeConversation(Conversation conversation) {
        mPagerAdapter.removeItem(conversation);
    }

    public void addConversation(int position, Conversation conversation) {
        mPagerAdapter.add(position, conversation);
    }

    public void swapConversation() {
        mConversationList.clear();
        generateDataSet();
        mPagerAdapter.swapDataSet(mConversationList);
    }

}
