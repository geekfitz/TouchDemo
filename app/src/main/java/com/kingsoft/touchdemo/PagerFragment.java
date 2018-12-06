package com.kingsoft.touchdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {

    private static final String EXTRA_CONVERSATION = "extra_conversation";
    private TextView textView;
    private Conversation conversation;

    private ViewPagerActivity activity;


    public PagerFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    public Conversation getConversation() {
        return conversation;
    }


    public static int i = 0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.tvTitle);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null) {
                    activity.addConversation(new Conversation(String.valueOf("ID: " + addAndGet()), " name: "));
                }

            }
        });

        if (conversation != null) {
            textView.setText(conversation.id);
        }

    }

    public static int addAndGet() {
        return ++i;
    }

}
