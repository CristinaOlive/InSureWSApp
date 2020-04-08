package pt.ulisboa.tecnico.sise.insure.app.asyncCalls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.WSHelper;
import pt.ulisboa.tecnico.sise.insure.datamodel.Customer;
import pt.ulisboa.tecnico.sise.insure.datamodel.GlobalState;


public class WSCallCustomerProfile extends AsyncTask<String, Void, Customer> {
    private final static String TAG = "CustomerProfile";
    private TextView customerName;
    private TextView customerNif;
    private TextView customerBirthdate;
    private TextView customerAddress;
    private TextView insurancePolicyNumber;
    private Context _context;
    boolean _network;
    GlobalState _global;

    public WSCallCustomerProfile(TextView customerName, TextView customerNif,
                                 TextView customerAddress, TextView customerBirthdate, TextView insurancePolicyNumber,
                                Context context, boolean network, GlobalState global) {
        this.customerName = customerName;
        this.customerNif = customerNif;
        this.customerAddress = customerAddress;
        this.customerBirthdate = customerBirthdate;
        this.insurancePolicyNumber = insurancePolicyNumber;
        _context = context;
        _network = network;
        _global = global;
    }

    @Override
    protected Customer doInBackground(String... String) {
        //publishProgress("Testing method call getCustomerInfo...");
        if(_network) {
            try {
                Customer customer = WSHelper.getCustomerInfo(_global.getSessionId());
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
        } else  {
           return _global.readFile();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Customer customer) {
        if (!customer.equals(null)) {
            int nif = customer.getFiscalNumber();
            customerNif.setText(String.valueOf(nif));
            customerName.setText(customer.getName());
            customerAddress.setText(customer.getAddress());
            customerBirthdate.setText(customer.getDateOfBirth());
            insurancePolicyNumber.setText(String.valueOf(customer.getPolicyNumber()));
        } else {
            Toast.makeText(_context, "This Customer's data is not available.", Toast.LENGTH_LONG).show();
        }
    }
}
