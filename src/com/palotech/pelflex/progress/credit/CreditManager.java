package com.palotech.pelflex.progress.credit;

import com.palotech.pelflex.progress.credit.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CreditManager {

    private static Credit balance = new Credit(getInitialBalance());

    private static List<Credit> creditList = new ArrayList<>();
    private static List<Transaction> transactionsList = new ArrayList<>();

    public static void executeTransaction(Transaction transaction) {
        boolean transactionTestSuccess = transaction.test(balance);
        if (transaction.test(balance)) {
            transaction.execute(balance);
        }
    }

    public static void addCredit(Credit credit) {
        creditList.add(credit);
    }

    public static Credit getBalance() {
        return balance;
    }

    public static int getInitialBalance() {
        return 0;
    }

}
