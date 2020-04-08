package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;


public class WSCallCustomerProfile extends AsyncTask<String, Void, Customer> {
    private final static String TAG = "CustomerProfile";
    private TextView customerName;
    private TextView customerNif;
    private TextView customerBirthdate;
    private TextView customerAddress;
    private TextView insurancePolicyNumber;
    private int sessionId;
    private Context _context;

    public WSCallCustomerProfile(TextView customerName, TextView customerNif,
                                 TextView customerAddress, TextView customerBirthdate, TextView insurancePolicyNumber,
                                 int sessionId, Context context) {
        this.sessionId = sessionId;
        this.customerName = customerName;
        this.customerNif = customerNif;
        this.customerAddress = customerAddress;
        this.customerBirthdate = customerBirthdate;
        this.insurancePolicyNumber = insurancePolicyNumber;
        _context = context;
    }

    @Override
    protected Customer doInBackground(String... String) {
       //publishProgress("Testing method call getCustomerInfo...");
        try {
            Customer customer = WSHelper.getCustomerInfo(sessionId);
            if (customer == null) {
                Log.d(TAG, "Get customer info result => null");
                return customer;
            } else {
                Log.d(TAG, "Get customer info result => " + customer.toString());
                return customer;
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
        if (!customer.equals(null)) {
            int nif = customer.getFiscalNumber();
            customerNif.setText(String.valueOf(nif));
            customerName.setText(customer.getName());
            customerAddress.setText(customer.getAddress());
            customerBirthdate.setText(customer.getDateOfBirth());
            insurancePolicyNumber.setText(String.valueOf(customer.getPolicyNumber()));
        } else  {
            Toast.makeText(_context, "This Customer's data is not available.", Toast.LENGTH_LONG).show();
        }
    }
}
