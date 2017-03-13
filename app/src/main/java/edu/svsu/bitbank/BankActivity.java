package edu.svsu.bitbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BankActivity extends AppCompatActivity {

    Button deposit_btn;
    Button withdraw_btn;
    Button freeze_account_btn;
    TextView balance_tv;
    EditText pin_et;

    AccountManager myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAccount = new AccountManager(BankActivity.this);


        deposit_btn = (Button) findViewById(R.id.deposit_btn);
        withdraw_btn = (Button) findViewById(R.id.withdraw_btn);
        freeze_account_btn = (Button) findViewById(R.id.freeze_btn);
        pin_et = (EditText) findViewById(R.id.pin_et);
        balance_tv = (TextView) findViewById(R.id.balance_tv);

        /******************* Button Event Listeners ***************************/

        /***************** DEPOSIT *****************/
        deposit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String result = myAccount.deposit(100, pin_et.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        });

        /****************** WITHDRAW *******************/
        withdraw_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String result = myAccount.withdraw(100, pin_et.getText().toString());
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });

        /******************* FREEZE ACCOUNT ****************************/
        freeze_account_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String result = myAccount.toggleFreezeAccount();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setBalance(double balance){
        String result = new StringBuilder().append("$").append(Double.toString(balance)).append("0").toString();
        balance_tv.setText(result);
    }
}
