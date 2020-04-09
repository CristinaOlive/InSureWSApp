package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class WSPlatesTask extends AsyncTask<Void, String, List<String>> {
    public final static String TAG = "CallTask";
    private Context _context;
    private Spinner _spin;
    private  int sessionId = -1;
    GlobalState _global;

    public WSPlatesTask(Context context, Spinner spin, GlobalState global) {
        _context = context;
        _spin = spin;
        _global = global;
        this.sessionId = _global.getSessionId();
    }

    @Override
    protected List<String> doInBackground(Void ... params) {
        /*
         * Test method call invocation: listPlates
         */
        List<String> plateList = null;
        try {
            plateList = WSHelper.listPlates(sessionId);
            if (plateList != null) {
                String m = plateList.size() > 0 ? "" : "empty array";
                for (String plate : plateList) {
                    m += " (" + plate + ")";
                }
                Log.d(TAG, "List plates result => " + m);
            } else {
                Log.d(TAG, "List plates result => null.");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return plateList;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(List<String> list) {
        if (list==null){
            Toast.makeText(_context, "No plates available", Toast.LENGTH_LONG).show();
        } else {
            //Creating the ArrayAdapter instance having the plates list
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            _spin.setAdapter(dataAdapter);
            Log.d(TAG, "plates finished");
        }
    }

}