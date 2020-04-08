package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;

public class WSClaimTask extends AsyncTask<Void, String, Boolean> {
    public final static String TAG = "CallTask";
    private TextView _textView;
    String _claimTitle;
    String _claimDescripton;
    String _plateNumber;
    String _accuranceDate;
    Context _context;

    public WSClaimTask(Context context, String claimTitle, String accuranceDate, String plateNumber, String claimDescripton) {
        _context = context;
        _claimTitle = claimTitle;
        _claimDescripton = claimDescripton;
        _plateNumber = plateNumber;
        _accuranceDate = accuranceDate;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int sessionId = -1;
        /*
         * Test method call invocation: login
         */
        try{
            String username = "j";
            String password = "j";
            sessionId = WSHelper.login(username,password);        // exists and password correct
            Log.d(TAG, "Login result => " + sessionId);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        /*
         * Test method call invocation: submitNewClaim
         */
        try {
            boolean r = WSHelper.submitNewClaim(sessionId, _claimTitle, _accuranceDate, _plateNumber, _claimDescripton);
            Log.d(TAG, "Submit new claim result => " + r);
            return r;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            Toast.makeText(_context, "Claim submited", Toast.LENGTH_LONG).show();
            Log.d(TAG, "finished testing");
        } else {
            Toast.makeText(_context, "FAILED TO SUBMIT!", Toast.LENGTH_LONG).show();
        }
    }
}