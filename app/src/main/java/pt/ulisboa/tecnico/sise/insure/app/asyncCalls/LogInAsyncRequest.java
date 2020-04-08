package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.os.AsyncTask;
import android.util.Log;

import pt.ulisboa.tecnico.sise.insure.app.JsonCodec;
import pt.ulisboa.tecnico.sise.insure.app.JsonFileManager;
import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.app.activities.LogInActivity;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;

public class LogInAsyncRequest extends AsyncTask<String, String, Integer> {
    public final static String TAG = "CallTask";
    private String username;
    private String password;
    private LogInActivity _main;

    public LogInAsyncRequest(String email_res, String password_res, LogInActivity main) {
        this.username = email_res;
        this.password = password_res;
        this._main = main;
    }

    protected Integer doInBackground(String... params) {
        int sessionId = -1;
        try {
            sessionId = WSHelper.login(username, password);
            Customer customer = WSHelper.getCustomerInfo(sessionId);
            String customerFileName = "customer.json";
            String customerJson = JsonCodec.encodeCustomerInfo(customer);
            JsonFileManager.jsonWriteToFile(_main, customerFileName, customerJson);
            Log.d(TAG, "customerInfo: written to - " + customerFileName);
            return sessionId;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return sessionId;
        }
    }

    @Override
    protected void onPostExecute(Integer sessionId) {
       _main.testSession(sessionId);
       this.cancel(true);
        }
    }

