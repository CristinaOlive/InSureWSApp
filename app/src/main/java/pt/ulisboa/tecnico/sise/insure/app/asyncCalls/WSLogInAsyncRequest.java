package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.os.AsyncTask;
import android.util.Log;

import pt.ulisboa.tecnico.sise.insure.app.JsonCodec;
import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.app.activities.LogInActivity;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class WSLogInAsyncRequest extends AsyncTask<String, String, Integer> {
    public final static String TAG = "CallTask";
    private String username;
    private String password;
    private LogInActivity _main;
    private GlobalState gState;

    public WSLogInAsyncRequest(String email_res, String password_res, LogInActivity main, GlobalState gState) {
        this.username = email_res;
        this.password = password_res;
        this._main = main;
        this.gState = gState;
    }

    protected Integer doInBackground(String... params) {
        int sessionId = -1;
        try {
            sessionId = WSHelper.login(username, password);
            gState.setSessionId(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = null;
        try {
            customer = WSHelper.getCustomerInfo(sessionId);
            gState.writeFile(customer);
            gState.setClaimItemList(WSHelper.listClaims(sessionId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionId;
    }

    @Override
    protected void onPostExecute(Integer sessionId) {
       _main.testSession(sessionId);
       this.cancel(true);
        }
    }

