package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class WSListClaimTask extends AsyncTask<Void, String,  List<ClaimItem>> {
    public final static String TAG = "CallTask";
    private Context _context;
    private ListView _listView;
    private int sessionId = -1;
    private GlobalState gState;

    public WSListClaimTask(Context context, ListView listView, int sessionId, GlobalState gState) {
        this.sessionId = sessionId;
        _context = context;
        _listView = listView;
        this.gState= gState;

    }

    @Override
    protected  List<ClaimItem> doInBackground(Void... params) {
        /*
         * Test method call invocation: listClaims
         */
        List<ClaimItem> claimItemList = null;
        if(gState.isNetworkAvailable()){
        try {
            claimItemList = WSHelper.listClaims(sessionId);
            if (claimItemList != null) {
                String m = claimItemList.size() > 0 ? "" : "empty array";
                for (ClaimItem claimItem : claimItemList ) {
                    m += " ("+ claimItem.toString() + ")";
                }
                Log.d(TAG, "List claim item result => " + m);
                return claimItemList;
            } else {
                Log.d(TAG, "List claim item result => null.");
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        } else {
            claimItemList = gState.getClaimItemList();
            if (claimItemList != null) {
                String m = claimItemList.size() > 0 ? "" : "empty array";
                for (ClaimItem claimItem : claimItemList) {
                    m += " (" + claimItem.toString() + ")";
                }
                Log.d(TAG, "List claim item result => " + m);
                return claimItemList;
            }
        }
        return claimItemList;
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected void onPostExecute(List<ClaimItem> result) {
        if(result == null){
            Toast.makeText(_context, "No Claims available", Toast.LENGTH_LONG).show();
        } else {
            // assign adapter to list view
            ArrayAdapter<ClaimItem> adapter = new ArrayAdapter<>(_context,
                    android.R.layout.simple_list_item_1, android.R.id.text1, result);
            _listView.setAdapter(adapter);
        }
    }
}