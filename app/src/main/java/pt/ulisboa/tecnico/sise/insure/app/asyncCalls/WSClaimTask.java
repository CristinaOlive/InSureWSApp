package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.app.activities.ListClaimsActivity;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

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
            Toast.makeText(_context, "Claim submited", Toast.LENGTH_LONG).show();
            Log.d(TAG, "finished testing");
            Customer customer = null;
            try {
                customer = WSHelper.getCustomerInfo(sessionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String customerJson = null;
            try {
                customerJson = JsonCodec.encodeCustomerInfo(customer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert customerJson != null;
            JsonFileManager.jsonWriteToFile(gState, "customer.json", customerJson);
            gState.setCustomer(customer);
            Intent intent = new Intent(_context, ListClaimsActivity.class);
            _context.startActivity(intent);
        } else {
            Toast.makeText(_context, "FAILED TO SUBMIT!", Toast.LENGTH_LONG).show();
        }
    }
}