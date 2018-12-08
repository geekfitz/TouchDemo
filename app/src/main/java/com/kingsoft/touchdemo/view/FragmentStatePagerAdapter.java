package com.kingsoft.touchdemo.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/7.
 */

public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static final String TAG = "FragmentStatePagerAdapt";
    private static final boolean DEBUG = true;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private Fragment mCurrentPrimaryItem = null;

    private final static String ARG_REMOVE_FLAG = "remove_flag";

    public FragmentStatePagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    public abstract Fragment getItem(int position);

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        if (mFragments.size() > position) {
            Fragment f = mFragments.get(position);
            if (f != null) {
                return f;
            }
        }

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(position);
        if (DEBUG) Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
        if (mSavedState.size() > position) {
            Fragment.SavedState fss = mSavedState.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }

        while (mFragments.size() <= position) {
            mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        mFragments.set(position, fragment);
        mCurTransaction.add(container.getId(), fragment);

        printSaveStates();

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;

        // Different to destroyItem as normal, this flag indicates that the position-th data  has benn removed from
        // dataSet permanently, so we also need remove the fragment and saveState accordingly.
        boolean remove = fragment.getArguments() != null && fragment.getArguments().getBoolean(ARG_REMOVE_FLAG);

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        if (DEBUG) Log.v(TAG, "Removing item #" + position + ": f=" + object
                + " v=" + ((Fragment) object).getView());
        while (mSavedState.size() <= position) {
            mSavedState.add(null);
        }

        // if remove flag is true, as we mentioned above, it is not necessary to  call Fragment's onSaveInstanceState
        // if call it, we will get wrong saveState when instantiate new fragment in instantiateItem.
        if (position < mSavedState.size() && position >= 0 && !remove) {
            mSavedState.set(position, fragment.isAdded()
                    ? mFragmentManager.saveFragmentInstanceState(fragment) : null);
        }

        if (position < mFragments.size() && position >= 0) {
            mFragments.set(position, null);
        }

        if (remove) {
            if (position < mFragments.size() && position >= 0) {
                mFragments.remove(position);
            }
            mSavedState.remove(position);
        }

        mCurTransaction.remove(fragment);

        printSaveStates();
    }

    @Override
    @SuppressWarnings("ReferenceEquality")
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
            mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment f = mFragments.get(i);
            if (f != null && f.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                String key = "f" + i;
                mFragmentManager.putFragment(state, key, f);
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            mSavedState.clear();
            mFragments.clear();
            if (fss != null) {
                for (int i = 0; i < fss.length; i++) {
                    mSavedState.add((Fragment.SavedState) fss[i]);
                }
            }
            Iterable<String> keys = bundle.keySet();
            for (String key : keys) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (mFragments.size() <= index) {
                            mFragments.add(null);
                        }
                        f.setMenuVisibility(false);
                        mFragments.set(index, f);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }

    protected void updateStateWhenAdd(int position) {
        Log.d(TAG, " --------updateStateWhenAdd: " + position);

        if (mFragments.size() > position) {
            mFragments.add(position, null);
        } else {
            while (mFragments.size() <= position) {
                mFragments.add(mFragments.size(), null);
            }
        }

        if (mSavedState.size() > position) {
            mSavedState.add(position, null);
        } else {
            while (mSavedState.size() <= position) {
                mSavedState.add(mSavedState.size(), null);
            }
        }

        printSaveStates();
    }

    protected void updateStateWhenRemove(int position) {
        Log.d(TAG, " --------updateStateWhenRemove: " + position);
        Fragment fragment = mFragments.get(position);
        fragment.setArguments(getDeleteFlagBundle());
        printSaveStates();
    }

    protected void updateStateWhenSwap() {
        Log.d(TAG, " --------updateStateWhenSwap");
        for (Fragment fragment : mFragments) {
            if (fragment != null) {
                fragment.setArguments(getDeleteFlagBundle());
            }
        }
        mFragments.clear();
        mSavedState.clear();
        printSaveStates();
    }

    @NonNull
    private Bundle getDeleteFlagBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_REMOVE_FLAG, true);
        return bundle;
    }

    private void printSaveStates() {
        Log.d(TAG, " mFragments: " + mFragments);
        Log.d(TAG, " mSavedState: " + mSavedState);
    }

}
