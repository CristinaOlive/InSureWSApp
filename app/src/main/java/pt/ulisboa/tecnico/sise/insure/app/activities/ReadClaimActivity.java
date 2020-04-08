package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSLogOutTask;
import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSReadClaimTask;

public class ReadClaimActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SISE - ListNotes";
    private Button _buttonHome;
    private Button _buttonLogOut;
    private Context _context = this;
    private TextView _claimId;
    private TextView _accuranceDate;
    private TextView _claimTitle;
    private TextView _claimBody;
    private TextView _claimPlate;
    private TextView _claimStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_claim);
        _buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        _buttonHome = (Button) findViewById(R.id.buttonHome);

        _claimId = (TextView) findViewById(R.id.textViewId);
        _accuranceDate = (TextView) findViewById(R.id.textViewDate);
        _claimTitle = (TextView) findViewById(R.id.textViewTitle);
        _claimBody = (TextView) findViewById(R.id.textViewDesc);
        _claimPlate = (TextView) findViewById(R.id.textViewPlate);
        _claimStatus = (TextView) findViewById(R.id.textViewStatus);

        int position = getIntent().getIntExtra("Position", 0);
        int sessionId = getIntent().getIntExtra("sessionId", 0);

        new WSReadClaimTask(position, _claimId, _accuranceDate, _claimTitle,
                _claimBody, _claimPlate, _claimStatus, _context, sessionId).execute();

        _buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // return the return code only; no intent message is required
                setResult(Activity.RESULT_CANCELED);
                // finish activity
                finish();
            }
        });

        _buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new WSLogOutTask(_context).execute();
                Log.d(LOG_TAG, "Logout debug message!");
                Intent intent = new Intent(ReadClaimActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}