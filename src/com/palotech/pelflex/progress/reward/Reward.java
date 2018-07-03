package com.palotech.pelflex.progress.reward;

import com.palotech.pelflex.progress.credit.Credit;

import java.time.LocalDateTime;

public class Reward {

    private Credit credit;
    private LocalDateTime date;

    public Reward(Credit credit) {
        this.credit = credit;
        this.date = LocalDateTime.now();
    }

    public Credit getCredit() {
        return credit;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
