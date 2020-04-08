package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class ProfileActivity extends AppCompatActivity {
    private final String TAG = "profileActivity";

    private Activity activity = this;
    private Button buttonProfileMenu;
    private Button buttonProfileLogout;
    //private GlobalState globalState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //globalState = (GlobalState) getApplicationContext();

        buttonProfileMenu = (Button)  findViewById(R.id.profile_menu_btn);
        buttonProfileLogout  = (Button) findViewById(R.id.profile_logout_btn);

        //Menu Button
        buttonProfileMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Menu Button in profile activity message!");
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        //Logout Button
        buttonProfileLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Logout Button in profile activity message!");
                Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}