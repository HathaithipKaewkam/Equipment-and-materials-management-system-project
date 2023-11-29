package ku.project.services;

import ku.project.models.account.Account;

import java.util.Comparator;

public class AccountDateTimeComparator implements Comparator<Account> {
    @Override
    public int compare(Account o1, Account o2) {
        return o1.getLoginDateTime().compareTo(o2.getLoginDateTime());
    }
}
