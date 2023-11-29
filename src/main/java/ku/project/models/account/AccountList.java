package ku.project.models.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class AccountList {
    private ArrayList<Account> accountList;

    public AccountList() {
        accountList = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public ArrayList<Account> getAllAccount(){

        return accountList;
    }

    public Account findAccount(String username){
        Account account = new Account();
        for (Account temp : accountList){
            if(username.equals(temp.getUsername())){
                account = temp;
            }
        }
        return account;
    }



    public Account changePassword(String username){
        for (Account temp : accountList) {
            if (username.equals(temp.getUsername())) {
                return temp;
            }
        }
        return null;
    }

    public Account changeProfile(String username) {
        for (Account temp : accountList) {
            if (username.equals(temp.getUsername())) {
                return temp;
            }
        }
        return null;
    }

    public boolean canLogin(String username, String password){
        Account account = searchAccountByUsername(username);
        if (account != null && account.canLogin(username,password))
            return true;
        return false;
    }
    public Account searchAccountByUsername(String username) {
        for (Account account: accountList)
            if (account.checkAccount(username)) return account;
        return null;
    }


    public boolean ExistUsername(String username){
        for(Account account: accountList) {
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }


    public boolean checkUsername(String username) {
        for (Account account : accountList) {
            if (account.checkAccount(username)) return true;
        }
        return false;
    }
    public void sort(Comparator<Account> accountComparator) {
        Collections.sort(this.accountList, accountComparator);
    }
}

