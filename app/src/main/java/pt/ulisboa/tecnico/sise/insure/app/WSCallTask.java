package pt.ulisboa.tecnico.sise.insure.app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import pt.ulisboa.tecnico.sise.insure.datamodel.ClaimItem;
import pt.ulisboa.tecnico.sise.insure.datamodel.ClaimMessage;
import pt.ulisboa.tecnico.sise.insure.datamodel.ClaimRecord;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;

public class WSCallTask extends AsyncTask<Void, String, Void> {
    public final static String TAG = "CallTask";
    private TextView _textView;

    public WSCallTask(TextView textView) {
        _textView = textView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int sessionId = -1;
        /*
         * Test method call invocation: hello
         */
        publishProgress("Testing method call hello...");
        try {
            String r = WSHelper.hello("John Doe");
            Log.d(TAG, "Hello result => " + r);
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: login
         */
        publishProgress("Testing method call login wrong...");
        try {
            String username = "k";
            String password = "j";

            sessionId = WSHelper.login(username,password);        // username doesn't exist
            Log.d(TAG, "Login result => " + sessionId);
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }
        publishProgress("Testing method call login wrong2...");
        try{
            String username = "j";
            String password = "k";
            sessionId = WSHelper.login(username,password);        // username exists but wrong password
            Log.d(TAG, "Login result => " + sessionId);
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }
        publishProgress("Testing method call login right...");
        try{
            String username = "j";
            String password = "j";
            sessionId = WSHelper.login(username,password);        // exists and password correct
            Log.d(TAG, "Login result => " + sessionId);
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: getCustomerInfo
         */
        publishProgress("Testing method call getCustomerInfo...");
        try {
            Customer customer = WSHelper.getCustomerInfo(sessionId);
            if (customer == null) {
                Log.d(TAG, "Get customer info result => null");
            } else {
                Log.d(TAG, "Get customer info result => " + customer.toString());
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }

        /*
         * Test method call invocation: getClaimInfo With WRONG claimId
         */
        publishProgress("Testing method call getClaimInfo WrongId...");
        try {
            int claimId = 9999;
            ClaimRecord claimRecord = WSHelper.getClaimInfo(sessionId, claimId);
            if (claimRecord != null) {
                Log.d(TAG, "Get Claim Info result claimId " + claimId + " => " + claimRecord.toString());
            } else {
                Log.d(TAG, "Get Claim Info result claimId " + claimId + " => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: getClaimInfo
         */
        publishProgress("Testing method call getClaimInfo...");
        try {
            int claimId = 1;
            ClaimRecord claimRecord = WSHelper.getClaimInfo(sessionId, 1);
            if (claimRecord != null) {
                Log.d(TAG, "Get Claim Info result claimId " + claimId + " => " + claimRecord.toString());
            } else {
                Log.d(TAG, "Get Claim Info result claimId " + claimId + " => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }

        /*
         * Test method call invocation: listClaims
         */
        publishProgress("Testing method call listClaims...");
        try {
            List<ClaimItem> claimItemList = WSHelper.listClaims(sessionId);
            if (claimItemList != null) {
                String m = claimItemList.size() > 0 ? "" : "empty array";
                for (ClaimItem claimItem : claimItemList ) {
                    m += " ("+ claimItem.toString() + ")";
                }
                Log.d(TAG, "List claim item result => " + m);
            } else {
                Log.d(TAG, "List claim item result => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: listClaimMessages
         */
        publishProgress("Testing method call listClaimMessages...");
        try {
            int claimId = 1;
            List<ClaimMessage> claimMessageList = WSHelper.listClaimMessages(sessionId, claimId);
            if (claimMessageList != null) {
                String m = claimMessageList.size() > 0 ? "" : "empty array";
                for (ClaimMessage cm : claimMessageList ) {

                    m += " (Sender:" + cm.getSender() +
                            ", Date: " + cm.getDate() +
                            ", Message: " + cm.getMessage() + ")";
                }
                Log.d(TAG, "List claim messages for claimId " + claimId + " result =>" + m);
            } else {
                Log.d(TAG, "List claim messages for claimId " + claimId + " result => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: listPlates
         */
        publishProgress("Testing method call listPlates...");
        try {
            List<String> plateList = WSHelper.listPlates(sessionId);
            if (plateList != null) {
                String m = plateList.size() > 0 ? "" : "empty array";
                for (String plate: plateList) {
                    m += " ("+ plate + ")";
                }
                Log.d(TAG, "List plates result => " + m);
            } else {
                Log.d(TAG, "List plates result => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }

        /*
         * Test method call invocation: submitNewClaim
         */
        publishProgress("Testing method call submitNewClaim...");
        try {
            boolean r = WSHelper.submitNewClaim(sessionId, "Test Claim Title", "29-06-2018","ZZ-11-22","Test Claim Description");
            Log.d(TAG, "Submit new claim result => " + r);

            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }

        /*
         * Test method call invocation: listClaims
         */
        publishProgress("Testing method call listClaims after newClaim...");
        try {
            List<ClaimItem> claimItemList = WSHelper.listClaims(sessionId);
            if (claimItemList != null) {
                String m = claimItemList.size() > 0 ? "" : "empty array";
                for (ClaimItem claimItem : claimItemList ) {
                    m += " ("+ claimItem.toString() + ")";
                }
                Log.d(TAG, "List claim item result => " + m);
            } else {
                Log.d(TAG, "List claim item result => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }


        /*
         * Test method call invocation: submitNewMessage
         */
        publishProgress("Testing method call submitNewMessage...");
        try {
            int claimId = 1;
            String message = "Test Message from mobile app";
            boolean r = WSHelper.submitNewMessage(sessionId, claimId, message);
            Log.d(TAG, "Submit New Message claimId " + claimId + " message:" + message + " result => " + r);
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }

        /*
         * Test method call invocation: listClaimMessages
         */
        publishProgress("Testing method call listClaimMessages...");
        try {
            int claimId = 1;
            List<ClaimMessage> claimMessageList = WSHelper.listClaimMessages(sessionId, claimId);
            if (claimMessageList != null) {
                String m = claimMessageList.size() > 0 ? "" : "empty array";
                for (ClaimMessage cm : claimMessageList ) {

                    m += " (Sender:" + cm.getSender() +
                            ", Date: " + cm.getDate() +
                            ", Message: " + cm.getMessage() + ")";
                }
                Log.d(TAG, "List claim messages for claimId " + claimId + " result =>" + m);
            } else {
                Log.d(TAG, "List claim messages for claimId " + claimId + " result => null.");
            }
            publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }



        /*
         * Test method call invocation: logout
         */
        publishProgress("Testing method call logout...");
        try {
            boolean result = WSHelper.logout(sessionId);
            Log.d(TAG, "Logout result => " + result);
            publishProgress("    ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            publishProgress("failed.\n");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(values.length > 0) {
            _textView.append(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        _textView.append("finished testing");
        Log.d(TAG,"finished testing");
    }
}