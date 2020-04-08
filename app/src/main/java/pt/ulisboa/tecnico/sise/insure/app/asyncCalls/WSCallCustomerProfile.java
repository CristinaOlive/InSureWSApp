package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;


public class WSCallCustomerProfile extends AsyncTask<String, Void, Customer> {

    private final static String TAG = "CustomerProfile";

    private Context context;
    private TextView customerName;
    private TextView customerNif;
    private TextView customerBirthdate;
    private TextView customerAddress;
    private GlobalState globalState;
    private TextView insurancePolicyNumber;



    public WSCallCustomerProfile(Context applicationContext, TextView customerName, TextView customerNif, TextView customerAddress, TextView customerBirthdate, TextView insurancePolicyNumber, GlobalState globalState) {

        this.context = applicationContext;
        this.customerName = customerName;
        this.customerNif = customerNif;
        this.customerAddress = customerAddress;
        this.customerBirthdate = customerBirthdate;
        this.insurancePolicyNumber = insurancePolicyNumber;
        this.globalState = globalState;

    }

    @Override
    protected Customer doInBackground(String... String) {
        int sessionId = -1;
        //publishProgress("Testing method call getCustomerInfo...");
        try {
            Customer customer = WSHelper.getCustomerInfo(sessionId);
            if (customer == null) {
                Log.d(TAG, "Get customer info result => null");
            } else {
                Log.d(TAG, "Get customer info result => " + customer.toString());
            }
            //publishProgress("ok.\n");
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            //publishProgress("failed.\n");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Customer customer) {
        Log.d(TAG,"Customer: " + customer);
        if (customer != null) {
            globalState.writeFile(customer,"CustomerProfile");
            int nif = customer.getFiscalNumber();
            customerNif.setText(String.valueOf(nif));
            customerName.setText(customer.getName());
            customerAddress.setText(customer.getAddress());
            customerBirthdate.setText(customer.getDateOfBirth());

        }

    }
}
