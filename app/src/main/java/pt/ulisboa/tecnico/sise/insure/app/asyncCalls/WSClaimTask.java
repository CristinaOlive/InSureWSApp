package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.JsonCodec;
import pt.ulisboa.tecnico.sise.insure.app.JsonFileManager;
import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.app.activities.ListClaimsActivity;
import pt.ulisboa.tecnico.sise.insure.app.activities.NewClaimActivity;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

import static pt.ulisboa.tecnico.sise.insure.app.activities.NewClaimActivity.*;

public class WSClaimTask extends AsyncTask<Void, String, Boolean> {
    public final static String TAG = "CallTask";
    private TextView _textView;
    private String _claimTitle;
    private String _claimDescripton;
    private String _plateNumber;
    private String _accuranceDate;
    private Context _context;
    private int sessionId = -1;
    private GlobalState gState;

    public WSClaimTask(Context context, String claimTitle, String accuranceDate, String plateNumber, String claimDescripton, GlobalState gState) {
        _context = context;
        _claimTitle = claimTitle;
        _claimDescripton = claimDescripton;
        _plateNumber = plateNumber;
        _accuranceDate = accuranceDate;
        this.sessionId = gState.getSessionId();
        this.gState = gState;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        /*
         * Test method call invocation: submitNewClaim
         */
        if(gState.isNetworkAvailable()) {
            try {
                boolean r = WSHelper.submitNewClaim(sessionId, _claimTitle, _accuranceDate, _plateNumber, _claimDescripton);
                Log.d(TAG, "Submit new claim result => " + r);
                return r;
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            Log.d(TAG, "finished testing");
            gState.updateFile(gState.getSessionId());
            try {
                gState.setClaimItemList(WSHelper.listClaims(sessionId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(_context, ListClaimsActivity.class);
            _context.startActivity(intent);
        } else {
            //Call toast from ativity metho
            Toast.makeText(_context, "You can't submit claims offline!", Toast.LENGTH_LONG);
        }
    }
}