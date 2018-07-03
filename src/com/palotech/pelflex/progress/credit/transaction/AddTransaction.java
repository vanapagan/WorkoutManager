package com.palotech.pelflex.progress.credit.transaction;

import com.palotech.pelflex.progress.credit.Credit;

public class AddTransaction extends Transaction {

    public AddTransaction(int id, Credit credit) {
        super(credit);
    }

    @Override
    public boolean test(Credit balance) {
        return credit.getValue() > 0;
    }

    @Override
    public void execute(Credit balance) {
        balance.setValue(balance.getValue() + credit.getValue());
    }


}
