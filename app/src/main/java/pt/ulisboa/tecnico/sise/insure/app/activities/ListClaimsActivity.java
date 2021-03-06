package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSListClaimTask;
import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSLogOutTask;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class ListClaimsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SISE - ListNotes";
    private ListView _listView;
    private Button _buttonHome;
    private Button _buttonLogOut;
    private Context _context = this;
    private int sessionId;
    private GlobalState gState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_claims);
        _buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        _buttonHome = (Button) findViewById(R.id.buttonHome);
        _listView = (ListView) findViewById(R.id.list_claims_list);
        gState = (GlobalState) getApplicationContext();
        sessionId = gState.getSessionId();
        addItemsOnList();

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create the read note activity, passing to it the index position as parameter
                Log.d("position", position+"");
                Intent intent = new Intent(ListClaimsActivity.this, ReadClaimActivity.class);
                intent.putExtra("sessionId", sessionId);
                intent.putExtra("Position", position+1);
                startActivity(intent);
            }
        });

        _buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Logout debug message!");
                Intent intent = new Intent(ListClaimsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        _buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gState.removeFiles();
                new WSLogOutTask(_context, gState).execute();
                Log.d(LOG_TAG, "Logout debug message!");
                Intent intent = new Intent(ListClaimsActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Colocar algo que destrua o ficheiro JSON ou o limpe
                startActivity(intent);
            }
        });
    }

    public void addItemsOnList(){
        new WSListClaimTask(_context, _listView, sessionId, gState).execute();
    }
}