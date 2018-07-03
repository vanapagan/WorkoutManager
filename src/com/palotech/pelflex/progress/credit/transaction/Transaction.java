package com.palotech.pelflex.progress.credit.transaction;

import com.palotech.pelflex.progress.credit.Credit;

import java.time.LocalDateTime;

public abstract class Transaction {

    private static int idCount;

    private int id;
    protected Credit credit;
    private LocalDateTime date;

    public Transaction(Credit credit) {
        this.id = idCount++;
        this.credit = credit;
        this.date = LocalDateTime.now();
    }

    public abstract boolean test(Credit balance);

    public abstract void execute(Credit balance);

    public int getId() {
        return id;
    }

    public Credit getCredit() {
        return credit;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
