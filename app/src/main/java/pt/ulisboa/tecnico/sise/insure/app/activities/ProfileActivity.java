package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSCallCustomerProfile;
import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSLogOutTask;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class ProfileActivity extends AppCompatActivity {
    private final String TAG = "profileActivity";

    private Button buttonProfileMenu;
    private Button buttonProfileLogout;
    private TextView customerName;
    private TextView customerNif;
    private TextView customerBirthdate;
    private TextView customerAddress;
    private TextView insurancePolicyNumber;
    private int sessionId;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonProfileMenu = (Button)  findViewById(R.id.profile_menu_btn);
        buttonProfileLogout  = (Button) findViewById(R.id.profile_logout_btn);
        customerName  = (TextView) findViewById(R.id.customer_name);
        customerNif  = (TextView) findViewById(R.id.customer_nif);
        customerBirthdate  = (TextView) findViewById(R.id.customer_birthdate);
        customerAddress  = (TextView) findViewById(R.id.customer_address);
        insurancePolicyNumber  = (TextView) findViewById(R.id.customer_inc_pol_numb);
        sessionId = getIntent().getIntExtra("sessionId", 0);
        GlobalState _global = (GlobalState) getApplicationContext();

        new WSCallCustomerProfile(customerName, customerNif, customerAddress,
                customerBirthdate, insurancePolicyNumber, _context, isNetworkAvailable(), _global).execute();

        //Menu Button
        buttonProfileMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Menu Button in profile activity message!");
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                intent.putExtra("sessionId", sessionId);
                startActivity(intent);
            }
        });

        //Logout Button
        buttonProfileLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new WSLogOutTask(_context).execute();
                Log.d(TAG, "Logout debug message!");
                Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = connectivityManager.getActiveNetworkInfo();
        Log.d(TAG, "Network: " + (nf != null && nf.isConnected()));
        return (nf != null && nf.isConnected());
    }

}