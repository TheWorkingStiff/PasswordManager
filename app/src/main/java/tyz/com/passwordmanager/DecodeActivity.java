package tyz.com.passwordmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class DecodeActivity extends AppCompatActivity implements ScreenPasswordDecodeFragment.OnMasterPasswordSetListener{


    @Override
    public void onMasterPasswordSet(int position) {
        Toast.makeText(this, "PasswordSet in activity", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_decode);
    }
    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_decode);

    }



}
