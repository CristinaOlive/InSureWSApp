package pt.ulisboa.tecnico.sise.insure.app.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.sise.insure.datamodel.InternalProtocol;

public class MenuActivity extends AppCompatActivity {

    private static final String LOG_TAG = "InSureApp - Menu";

    private Button buttonNewClaim;
    private Button buttonProfile;
    private Button buttonClaimsHistory;
    private Button buttonLogout;
    private  int sessionId = -1;

    //private GlobalState globalState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //globalState = (GlobalState) getApplicationContext();
        buttonProfile = (Button)  findViewById(R.id.main_menu_profile_btn);
        buttonClaimsHistory  = (Button) findViewById(R.id.main_menu_hst_btn);
        buttonLogout  = (Button) findViewById(R.id.main_menu_logout_btn);
        buttonNewClaim  = (Button)  findViewById(R.id.main_menu_new_claim_btn);

        sessionId = getIntent().getIntExtra("sessionId", 0);

        //New Claim Button
        buttonNewClaim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "New Claim debug message!");
                Intent intent = new Intent(MenuActivity.this, NewClaimActivity.class);
                intent.putExtra("sessionId", sessionId);
                startActivityForResult(intent, InternalProtocol.NEW_CLAIM_REQUEST);
            }
        });
        //Profile Activity Button
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Profile debug message!");
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                intent.putExtra("sessionId", sessionId);
                startActivity(intent);
            }
        });
        //Claim History Button
        buttonClaimsHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Claims History debug message!");
                Intent intent = new Intent(MenuActivity.this, ListClaimsActivity.class);
                intent.putExtra("sessionId", sessionId);
                startActivity(intent);
            }
        });

        //logout button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Logout debug message!");
                Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}