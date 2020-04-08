package pt.ulisboa.tecnico.sise.insure.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.sise.insure.app.asyncCalls.LogInAsyncRequest;

public class LogInActivity extends AppCompatActivity {
    Button btnLogIn;
    EditText email_res, password_res;
    int sessionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogIn = findViewById(R.id.btnLogIn);
        email_res = findViewById(R.id.Email_Registry);
        password_res = findViewById(R.id.Password_Registry);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogInAsyncRequest(email_res.getText().toString(), password_res.getText().toString(), LogInActivity.this).execute();
            }
        });
    }

    public void testSession(Integer sessionId){
        if (sessionId>0){
            this.sessionId = sessionId;
            String[] logInfo = {email_res.getText().toString(), password_res.getText().toString()};
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
            Intent logIn = new Intent(LogInActivity.this, MenuActivity.class);
            logIn.putExtra("sessionId", sessionId);
            logIn.putExtra("logInfo", logInfo);
            startActivity(logIn);
            // this.finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_LONG).show();
        }
    }
    public int getSessionId(){
        return this.sessionId;
    }
    public void setSessionId(Integer sessionId){
        this.sessionId = sessionId;
    }
}