package com.kingsoft.touchdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PagerFragment extends Fragment {

    public final static String TAG = "PagerFragment";
    private static final String EXTRA_CONVERSATION = "extra_conversation";

    private ViewPagerActivity activity;
    private TextView tvTitle, tvFragment;

    private static final String EXTRA_ID = "extra_id";
    public static int sIndex = 0;

    private Conversation conversation;


    public static PagerFragment newInstance(Conversation conversation) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CONVERSATION, conversation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ViewPagerActivity) {
            activity = (ViewPagerActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversation = getArguments().getParcelable(EXTRA_CONVERSATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvFragment = view.findViewById(R.id.tvFragment);

        TextView tvState = view.findViewById(R.id.tvState);

        if (savedInstanceState != null) {
            String id = savedInstanceState.getString(EXTRA_ID);
            tvState.setText("savedState: " + id);
        }

        if (conversation != null) {
            tvTitle.setText(conversation.id);
        }

        tvFragment.setText(this.toString());

        view.findViewById(R.id.tvAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null) {
                    activity.addConversation(new Conversation(String.valueOf("id: " + addAndGet()), " name: "));
                }

            }
        });

        view.findViewById(R.id.tvDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.reConversation(conversation);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String s = tvTitle.getText().toString().trim();
        Log.d(TAG, "onSaveInstanceState " + s);
        outState.putString(EXTRA_ID, s);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        String s = null;
        if (savedInstanceState != null) {
            s = savedInstanceState.getString(EXTRA_ID);
        }
        Log.d(TAG, "onViewStateRestored " + s);
    }

    public static int addAndGet() {
        return sIndex++;
    }

    public Conversation getConversation() {
        return conversation;
    }


}
