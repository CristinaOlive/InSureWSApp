package pt.ulisboa.tecnico.sise.insure.datamodel;

import android.app.Application;

public class GlobalState extends Application {
    private int sessionId;

    private Customer customer;

//Terminar este método
    public int getSessionId() {
        this.sessionId = sessionId;
        return sessionId;
    }



    public void setSessionId(int id) {
        this.sessionId = id;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void writeFile(Customer customer, String customerInfo) {
    }
}
