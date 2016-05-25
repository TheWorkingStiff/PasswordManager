package tyz.com.passwordmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ScreenNotImplemented extends BaseFragment {
    private static final String ARG_SCREEN_TYPE = "screenType";


    public static ScreenNotImplemented newInstance() {
        final ScreenNotImplemented fragment = new ScreenNotImplemented();

        final Bundle args = new Bundle();
        args.putString(ARG_SCREEN_TYPE, "Bogus");
        fragment.setArguments(args);
        return fragment;

    }


    public ScreenNotImplemented() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_not_implemented, container, false);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
