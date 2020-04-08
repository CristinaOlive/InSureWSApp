package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSClaimTask;
import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSLogOutTask;
import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.WSPlatesTask;

public class NewClaimActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = "SISE - NewNote";
    private Button buttonSubmit;
    private Button buttonCancel;
    private Button buttonLogOut;
    private EditText editClaimTitle;
    private EditText descriptionClaim;
    private DatePickerDialog picker;
    private EditText dateText;
    private Spinner spin;
    private Context _context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);
        Log.d(LOG_TAG, String.valueOf("Start:"));
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        editClaimTitle = (EditText) findViewById(R.id.editTextTitle);
        descriptionClaim = (EditText) findViewById(R.id.descriptionClaim);
        dateText=(EditText) findViewById(R.id.datePickerAccurance);
        dateText.setInputType(InputType.TYPE_NULL);
        spin = (Spinner) findViewById(R.id.plateNumber);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NewClaimActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        addItemsOnSpineer();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Hello this is my first debug message!");
                Log.d(LOG_TAG, editClaimTitle.getText().toString());
                String claimTitle = editClaimTitle.getText().toString();
                String claimBody = descriptionClaim.getText().toString();
                String plate = String.valueOf(spin.getSelectedItem());
                String accuranceDate = dateText.getText().toString();
                new WSClaimTask(_context, claimTitle, accuranceDate, plate, claimBody).execute();
                Intent intent = new Intent(NewClaimActivity.this, ListClaimsActivity.class);
                startActivity(intent);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // return the return code only; no intent message is required
                setResult(Activity.RESULT_CANCELED);
                Toast.makeText(_context, "Claim cancelled", Toast.LENGTH_LONG).show();
                // finish activity
                finish();
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new WSLogOutTask(_context).execute();
                finish();
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addItemsOnSpineer(){
        new WSPlatesTask(_context, spin).execute();
    }

}
