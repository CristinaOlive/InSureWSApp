package pt.ulisboa.tecnico.sise.insure.datamodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import pt.ulisboa.tecnico.sise.insure.app.JsonCodec;
import pt.ulisboa.tecnico.sise.insure.app.JsonFileManager;

public class GlobalState extends Application {
    private final static String TAG = "GlobalState";
    private int sessionId;
    Context _context;
    private Customer customer;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int id) {
        this.sessionId = id;
        Log.d(TAG, "SessionId" + sessionId);
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void writeFile(Customer customerLocal) {
        String customerFileName = "customer.json";
        String customerJson = null;
        try {
            customerJson = JsonCodec.encodeCustomerInfo(customerLocal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonFileManager.jsonWriteToFile(_context, customerFileName, customerJson);
        Log.d(TAG, "customerInfo: written to - " + customerFileName);
    }

    public Customer readFile() {
        String customerFileName = "customer.json";

        String customerJson = JsonFileManager.jsonReadFromFile(_context, customerFileName);
        Log.d(TAG, "customerInfo: read from - " + customerFileName);

        Customer jsonCustomer = JsonCodec.decodeCustomerInfo(customerJson);
        Log.d(TAG, "customerInfo: jsonCustomer - " + jsonCustomer);
        return jsonCustomer;
    }
}
