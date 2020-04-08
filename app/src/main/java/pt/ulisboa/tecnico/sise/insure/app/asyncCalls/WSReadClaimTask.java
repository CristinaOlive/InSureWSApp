package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class WSReadClaimTask extends AsyncTask<Void, String, ClaimRecord> {
    public final static String TAG = "CallReadClaim";
    private TextView _claimId;
    private TextView _accuranceDate;
    private TextView _claimTitle;
    private TextView _claimBody;
    private TextView _claimPlate;
    private TextView _claimStatus;
    int _claimIdInt;
    Context _context;
    private GlobalState gState;
    private int sessionId = -1;

    public WSReadClaimTask(int claimIdInt, TextView claimId, TextView accuranceDate, TextView claimTitle,
                           TextView claimBody, TextView claimPlate, TextView claimStatus, Context context, int sessionId, GlobalState gState) {
       this.sessionId = sessionId;
        _claimId = claimId;
        _accuranceDate = accuranceDate;
        _claimBody = claimBody;
        _claimPlate = claimPlate;
        _claimStatus = claimStatus;
        _claimTitle = claimTitle;
        _claimIdInt = claimIdInt;
        _context = context;
        this.gState = gState;
    }

    @Override
    protected ClaimRecord doInBackground(Void... params) {
        /*
         * Test method call invocation: getClaimInfo
         */
        ClaimRecord claimRecord = null;
        if(gState.isNetworkAvailable()) {
            try {
                claimRecord = WSHelper.getClaimInfo(sessionId, _claimIdInt);
                if (claimRecord != null) {
                    Log.d(TAG, "Get Claim Info result claimId " + _claimIdInt + " => " + claimRecord.toString());
                } else {
                    Log.d(TAG, "Get Claim Info result claimId " + _claimIdInt + " => null.");
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }else{
            if (claimRecord != null) {
                claimRecord = (ClaimRecord) gState.getClaimItemList().get(_claimIdInt-1);
                Log.d(TAG, "Get Claim Info result claimId " + _claimIdInt + " => " + claimRecord.toString());
                return claimRecord;
            } else {
                Log.d(TAG, "Get Claim Info result claimId " + _claimIdInt + " => null.");
            }
        }
        return claimRecord;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(ClaimRecord result) {
        if(result == null){
            Toast.makeText(_context, "Claim is not available!", Toast.LENGTH_LONG).show();
        } else {
            _claimId.setText(result.getTitle());
            _claimTitle.setText(result.getTitle());
            _accuranceDate.setText(result.getOccurrenceDate());
            _claimPlate.setText(result.getPlate());
            _claimBody.setText(result.getDescription());
            _claimStatus.setText(result.getStatus());
        }
    }
}