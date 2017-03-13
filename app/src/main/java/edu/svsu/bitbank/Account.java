package edu.svsu.bitbank;

import java.util.List;

/**
 * Created by Topgun on 3/12/2017.
 */

public class Account {

    private String acounnt_number;
    private String account_type;
    private double balance;
    private String pin;
    private List<String> access_list;
    private boolean frozen;

    public Account() {
    }

    /*************** Getters and Setters **********************/
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void toggleFrozen() {
        if(isFrozen()){
            this.frozen = false;
        }else {
            this.frozen = true;
        }
    }

    public String getAcounnt_number() {
        return acounnt_number;
    }

    public void setAcounnt_number(String acounnt_number) {
        this.acounnt_number = acounnt_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<String> getAccess_list() {
        return access_list;
    }

    public void setAccess_list(List<String> access_list) {
        this.access_list = access_list;
    }

    public void deposit(double amt){
        setBalance(getBalance() + amt);
    }
    public void withdraw(double amt){
        setBalance(getBalance() - amt);
    }
}
