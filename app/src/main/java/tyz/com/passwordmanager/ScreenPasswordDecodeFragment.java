package tyz.com.passwordmanager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ScreenPasswordDecodeFragment extends BaseFragment {
    static EditText mEditMaster, mEditKey;
    static Button mBtnValidMaster, mBtnValidKey, mBtnShowMaster, mBtnShowKey;
    boolean bShowMaster = false, bShowKey = false;
    TextView mTranslate;
    OnMasterPasswordSetListener mCallback;

    private static final String ARG_SCREEN_TYPE = "screenType";

    // Container Activity must implement this interface
    public interface OnMasterPasswordSetListener {
        void onMasterPasswordSet(int position);
    }

    public static ScreenPasswordDecodeFragment newInstance() {
        final ScreenPasswordDecodeFragment fragment = new ScreenPasswordDecodeFragment();

        final Bundle args = new Bundle();
        args.putString(ARG_SCREEN_TYPE, "Bogus");
        fragment.setArguments(args);
        return fragment;

    }


    public ScreenPasswordDecodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_password_decode, container, false);

        mTranslate = (TextView) rootView.findViewById(R.id.txtTranslate) ;
        mEditMaster = (EditText) rootView.findViewById(R.id.masterPwd);
        mEditKey = (EditText) rootView.findViewById(R.id.newPassword);
        mBtnShowKey = (Button)  rootView.findViewById(R.id.show_key);
        mBtnShowMaster = (Button)  rootView.findViewById(R.id.show_master);
        mEditMaster.setFocusable(true);
        mEditKey.setFocusable(true);

        mBtnValidMaster = (Button) rootView.findViewById(R.id.btnValidMaster);
        mBtnValidKey = (Button) rootView.findViewById(R.id.btnValidKey);
        mBtnShowKey = (Button)  rootView.findViewById(R.id.show_key);
        mBtnShowMaster = (Button)  rootView.findViewById(R.id.show_master);

        final InputMethodManager inputManager =
                (InputMethodManager)  getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        TextWatcher watchMaster = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                // TODO Auto-generated method stub
                if (validateMaster(mEditMaster.getText().toString())) {
                    mBtnValidMaster.setBackgroundResource(R.drawable.btngreen);
                    inputManager.hideSoftInputFromWindow(
                            mEditMaster.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditKey.setEnabled(true);
                } else {
                    mBtnValidMaster.setBackgroundResource(R.drawable.btnred);
                    mEditKey.setEnabled(false);

//                    mEditMaster.setBackgroundColor(Color.RED);
                }
            }

        };
        TextWatcher watchKey = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                int[] which = {};
                //If all the characters in the key match characters in the Master
                // then translate
                if (validateKey(mEditKey.getText().toString(), mEditMaster.getText().toString(),false, which)){
                    //mEditKey.setBackgroundColor(Color.GREEN);
                    mBtnValidKey.setBackgroundResource(R.drawable.btngreen);
                    mTranslate.setText(getMasterOffsets(mEditKey.getText().toString(), mEditMaster.getText().toString()));
                } else
                //If the characters in the key are numeric only then reverse
                // translation
                if(mEditKey.getText().toString().matches("^[0-9]+$")) {
                    //mEditKey.setBackgroundColor(Color.BLUE);
                    mTranslate.setText(getMasterLetters(mEditKey.getText().toString(), mEditMaster.getText().toString()));
                }else
                //If the entry in the key field is invalid.
                {
                    //mEditKey.setBackgroundColor(Color.RED);
                    mBtnValidKey.setBackgroundResource(R.drawable.btnred);
                    Toast.makeText(getContext(),"something failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {

            }

        };

        mBtnShowMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bShowMaster = !bShowMaster;
                if(bShowMaster){
                    mEditMaster.setTransformationMethod(null);
                }else{
                    mEditMaster.setTransformationMethod(new PasswordTransformationMethod());
                }
                ((Button)v).setBackgroundResource(bShowMaster?R.drawable.btneye:R.drawable.btneyeclosed);


            }
        });

        mBtnShowKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bShowKey = !bShowKey;
                if(bShowKey){
                    mEditKey.setTransformationMethod(null);
                }else{
                    mEditKey.setTransformationMethod(new PasswordTransformationMethod());
                }
                ((Button)v).setBackgroundResource(bShowKey?R.drawable.btneye:R.drawable.btneyeclosed);


            }
        });

        mEditMaster.addTextChangedListener(watchMaster);
        mEditKey.addTextChangedListener(watchKey);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMasterPasswordSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMasterPasswordSetListener");
        }
    }



    /*
    With input of a string of numbers
    Find the characters in the master at those offsets
     */
    static String getMasterLetters(String inKey, String master){
        StringBuilder sbOut = new StringBuilder(inKey.length());
        for(int i = 0;i<inKey.length();i++){
            int offset = Integer.parseInt(inKey.substring(i,i+1));
            sbOut.append(master.charAt(offset));
        }
        return sbOut.toString();
    }

    /**
     * Test algorhithm
     StringBuilder sbOut = new StringBuilder(inKey.length());
     char[] offsets = inKey.toCharArray();
     char [] masterArr = new char[mEditMaster.length()];
     mEditMaster.getText().getChars(0,mEditMaster.length(),masterArr,0);
     for(char a : offsets){
     sbOut.append(masterArr[Integer.parseInt(String.valueOf(a))]);
     }
     return sbOut.toString();
     */

    /*
    With input of a alpha string (prechecked above)
    Find the offsets of those characters in the master and build a
    string from them.
     */
    static String getMasterOffsets(String inKey, String master){
        StringBuilder sbOut = new StringBuilder(inKey.length());
        char [] myKey = inKey.toCharArray();
        for(char a : myKey){
            sbOut.append(master.indexOf(a));
        }
        return sbOut.toString();
    }

    /**
     * All chars in key must exist in master
     * Future: may allow case insensitivity and
     *  typed escape characters (cap, digit, sep, etc.)
     *
     *  Note isCaseSenstive and invalidBits are not implemented
     */
    static boolean validateKey(String inKey, String inMaster, boolean isCaseSensitive, int[] invalidBits){
        for(int count = 0; count < inKey.length(); count++ ){
            if(!inMaster.contains(inKey.subSequence(count, count+1))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Master Password must have
     *  length 10
     *  no repeats
     *  all alpha - consider what other chars to allow (_,-,#,^)
     *      digits are problematic re representation.
     *  all lc
     */
    static boolean validateMaster(String master){
        if(master==null) return false;
        return (master.length() == 10 && // Length = 10
                master.matches("^[a-zA-Z_]*$") && // alpha, allow underscore
                master.toLowerCase().equals(master) &&  // all upper case
                checkUniqueBV(master)); // is unique

    }

    /**
     * Very efficient uniqueness check. Should test using intuitive algorithm.
     * Each letter is assigned a bit location. If bit is set
     * character exists in inStr
     *
     * Note: that this will not differentiate case.
     */
    static boolean checkUniqueBV(String inStr){
        int flags = 0, // use integer as bit vector
            flagIndex;
        boolean wasSeen;

        for (int i = 0; i < inStr.length(); i++) {
            flagIndex = inStr.charAt(i) - 'a';
            wasSeen = ((flags>>flagIndex) % 2 != 0);
            if (wasSeen)
                return false;
            else
                // mark offset seen
                flags |= (1 << flagIndex);
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean("showMaster", bShowMaster);
        savedInstanceState.putBoolean("showKey", bShowKey);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            boolean shouldShow = savedInstanceState.getBoolean("showMaster", false);
            mEditMaster.setTransformationMethod(shouldShow? null: new PasswordTransformationMethod());
            shouldShow = savedInstanceState.getBoolean("showKey", false);
            mEditKey.setTransformationMethod(shouldShow? null:  new PasswordTransformationMethod());
        }
    }
}