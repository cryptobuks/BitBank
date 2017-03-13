package edu.svsu.bitbank;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Topgun on 3/12/2017.
 */

public class AccountManager {

    private Account real_account;
    private String current_user;
    private FirebaseUser user;

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;

    private BankActivity main_activity;

    public AccountManager(BankActivity context) {
        //Gets Firebase Database instance
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("my_account");

        main_activity = context;

        //Gets Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();

        //Setting initial Firebase data
        //saveToFirebase();

        //Retrieve Account data from Firebase
        retrieveAccount();
    }

    private void retrieveAccount(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                real_account = dataSnapshot.getValue(Account.class);
                main_activity.setBalance(real_account.getBalance());

                //System.out.println(acct);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public String deposit(double amount, String pin) {
        String result = "";
        if (checkAccess(current_user)) {   //check Access first
            //Deposit the funds
            //real_account.setBalance(real_account.getBalance() + amount);
            real_account.deposit(amount);
            sendData();
            result = "Deposit Successful";
        } else {
            result = "No Access to Account!";
        }
        return result;
    }

    public String withdraw(double amount, String pin) {
        String result = "";
        if (checkAccess(current_user)) {   //check Access first
            if (checkBalance() >= amount) {   //check Balance for funds
                if(checkPIN(pin)){
                    //Withdraw the funds
                    real_account.withdraw(amount);
                    //real_account.setBalance(real_account.getBalance() - amount);
                    sendData();
                    result = "Withdraw Successful";
                }else{
                    result = "Incorrect PIN!";
                }
            } else {
                result = "Insufficient funds!";
            }
        } else {
            result = "No Access to Account!";
        }
        return result;
    }




    public String toggleFreezeAccount(){
        real_account.toggleFrozen();
        String result = "";
        if(real_account.isFrozen()){
            result = "Account is Frozen!";
        }else{
            result = "Account is Thawed!";
        }
        sendData();
        return result;
    }

    /*********Private/Auxillary Functions**********/

    private boolean checkPIN(String pin){
        if(pin.equals(real_account.getPin())){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkAccess(String user){
        if(real_account.isFrozen()){
            return false;
        }else if(real_account.getAccess_list().contains(user)){
            return true;    //Has access
        }else {
            return false;   //No access
        }
    }

    private double checkBalance(){
        return real_account.getBalance();
    }

    private void sendData(){
        ref.setValue(real_account);
    }

    private void saveToFirebase(){
        double bal = 1500.00;
        Account acct = new Account();
        acct.setAccount_type("CHECKING");
        acct.setAcounnt_number("0098764505");
        acct.setBalance(1500.00);
        acct.setFrozen(false);
        acct.setPin("1234");
        List<String> lst = new ArrayList<String>();
        lst.add("8uRsT2dUrpTt09dpeznm6Lg3yPE3"); //logitechnoob@gmail.com
        lst.add("3FoWJjGcTdhrEOrX0DJihg18WDk2"); //mdroof@svsuignitecs.org
        acct.setAccess_list(lst);
        ref.setValue(acct);
    }


}
