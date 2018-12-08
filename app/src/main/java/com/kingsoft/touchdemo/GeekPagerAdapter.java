package com.kingsoft.touchdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.kingsoft.touchdemo.view.FragmentStatePagerAdapter;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/12/6.
 */

public class GeekPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = "GeekPagerAdapter";
    private LinkedList<Conversation> conversations;

    public GeekPagerAdapter(FragmentManager fm, LinkedList<Conversation> conversations) {
        super(fm);
        this.conversations = conversations;
    }

    @Override
    public Fragment getItem(int position) {
        Conversation conversation = null;
        if (conversations == null) {
            return null;
        }
        if (position >= 0 && position < conversations.size()) {
            conversation = conversations.get(position);
        }
        PagerFragment fragment = PagerFragment.newInstance(conversation);
        Log.d(TAG, " getItem: " + fragment + " position: " + position);
        return fragment;
    }

    @Override
    public int getCount() {
        return conversations == null ? 0 : conversations.size();
    }

    @Override
    public int getItemPosition(Object object) {
        int position = getRealPosition(object);
        if (position == -1) {
            return PagerAdapter.POSITION_NONE;
        } else {
            return position;
        }
    }

    public void addLast(Conversation conversation) {
        conversations.addLast(conversation);
        updateStateWhenAdd(conversations.size() - 1);
        notifyDataSetChanged();
    }


    public void addFirst(Conversation conversation) {
        conversations.addFirst(conversation);
        updateStateWhenAdd(0);
        notifyDataSetChanged();
    }

    public void add(int position, Conversation conversation) {
        conversations.add(position, conversation);
        updateStateWhenAdd(position);
        notifyDataSetChanged();
    }

    public void removeItem(Conversation conversation) {
        int index = conversations.indexOf(conversation);
        conversations.remove(index);
        updateStateWhenRemove(index);
        notifyDataSetChanged();
    }


    private int getRealPosition(Object object) {
        PagerFragment fragment = (PagerFragment) object;
        Conversation c = fragment.getConversation();
        return conversations.indexOf(c);
    }

}
