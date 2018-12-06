package com.kingsoft.touchdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/12/6.
 */

public class GeekPagerAdapter extends FragmentStatePagerAdapter {

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
        return fragment;
    }

    @Override
    public int getCount() {
        return conversations == null ? 0 : conversations.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return getRealPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = getRealPosition(object);
        super.destroyItem(container, realPosition, object);
    }

    public void addItem(Conversation conversation) {
        conversations.addFirst(conversation);
        notifyDataSetChanged();
    }

    private int getRealPosition(Object object) {
        PagerFragment fragment = (PagerFragment) object;
        Conversation c = fragment.getConversation();
        return conversations.indexOf(c);
    }

}
