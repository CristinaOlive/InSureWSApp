package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;

public class WSLogOutTask extends AsyncTask<Void, String, Boolean> {
    public final static String TAG = "CallTask";
    Context _context;

    public WSLogOutTask(Context context) {
        _context = context;
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