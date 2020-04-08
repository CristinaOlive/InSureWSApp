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
    private String _claimTitle;
    private String _claimDescripton;
    private String _plateNumber;
    private String _accuranceDate;
    private Context _context;
    private int sessionId = -1;

    public WSClaimTask(Context context, String claimTitle, String accuranceDate, String plateNumber, String claimDescripton, int sessionId) {
        _context = context;
        _claimTitle = claimTitle;
        _claimDescripton = claimDescripton;
        _plateNumber = plateNumber;
        _accuranceDate = accuranceDate;
        this.sessionId = sessionId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
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