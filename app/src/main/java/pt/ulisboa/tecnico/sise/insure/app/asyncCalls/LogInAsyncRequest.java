package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.os.AsyncTask;
import android.util.Log;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.app.activities.LogInActivity;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;

public class LogInAsyncRequest extends AsyncTask<String, String, Integer> {
    public final static String TAG = "CallTask";
    private String username;
    private String password;
    private LogInActivity _main;
    GlobalState _global;

    public LogInAsyncRequest(String email_res, String password_res, LogInActivity main, GlobalState global) {
        this.username = email_res;
        this.password = password_res;
        this._main = main;
        _global = global;
    }

    protected Integer doInBackground(String... params) {
        int sessionId = -1;
        try {
            sessionId = WSHelper.login(username, password);
            _global.setSessionId(sessionId);
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

