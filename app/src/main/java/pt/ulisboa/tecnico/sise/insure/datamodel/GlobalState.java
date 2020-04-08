package pt.ulisboa.tecnico.sise.insure.datamodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import pt.ulisboa.tecnico.sise.insure.app.JsonCodec;
import pt.ulisboa.tecnico.sise.insure.app.JsonFileManager;
import pt.ulisboa.tecnico.sise.insure.app.WSHelper;

public class GlobalState extends Application {
    private final static String TAG = "GlobalState";
    private int sessionId;
    private Customer customer;
    private List<ClaimItem> claimItemList;
    private List<ClaimRecord> claimRecordList;

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
        JsonFileManager.jsonWriteToFile(this, customerFileName, customerJson);
        Log.d(TAG, "customerInfo: written to - " + customerFileName);
    }

    public Customer readFile() {
        String customerFileName = "customer.json";

        String customerJson = JsonFileManager.jsonReadFromFile(this, customerFileName);
        Log.d(TAG, "customerInfo: read from - " + customerFileName);

        Customer jsonCustomer = JsonCodec.decodeCustomerInfo(customerJson);
        Log.d(TAG, "customerInfo: jsonCustomer - " + jsonCustomer);
        return jsonCustomer;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = connectivityManager.getActiveNetworkInfo();
        return (nf != null && nf.isConnected());
    }

    public void updateFile(int sessionId){
        Customer customer = null;
        try {
            customer = WSHelper.getCustomerInfo(sessionId);
            writeFile(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClaimItem> getClaimItemList() {
        return claimItemList;
    }

    public void setClaimItemList(List<ClaimItem> claimItemList) {
        this.claimItemList = claimItemList;
    }

    public List<ClaimRecord> getClaimRecordList() {
        return claimRecordList;
    }

    public void setClaimRecordList(List<ClaimRecord> claimRecordList) {
        this.claimRecordList = claimRecordList;
    }
}
