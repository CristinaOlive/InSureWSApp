package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class WSLogOutTask extends AsyncTask<Void, String, Boolean> {
    public final static String TAG = "CallTask";
    Context _context;
    GlobalState _global;

    public WSLogOutTask(Context context, GlobalState global) {
        _context = context;
        _global = global;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int sessionId = _global.getSessionId();
        /*
         * Test method call invocation: logout
         */
        boolean result = false;
        try {
            result = WSHelper.logout(sessionId);
            Log.d(TAG, "Logout result => " + result);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(_context, "Log Out Successful", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(_context, "Log Out Failed", Toast.LENGTH_LONG).show();
        }
    }
}