package com.palotech.pelflex.progress.credit.transaction;

import com.palotech.pelflex.progress.credit.Credit;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(Credit credit) {
        super(credit);
    }

    @Override
    public boolean test(Credit balance) {
        return balance.getValue() - credit.getValue() >= 0;
    }

    @Override
    public void execute(Credit balance) {
        balance.setValue(balance.getValue() - credit.getValue());
    }


}
