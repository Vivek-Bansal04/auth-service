package com.okta.auth.security.practice.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//@Entity
@Getter
@Setter
public class BankTransfer implements Serializable {
    public enum State {
        CREATED,
        SETTLED
    }
    @Id
    private String id;

    private String reference;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account sender;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account receiver;

//    private int amount;

    @Enumerated(EnumType.STRING)
    private State state;

    public BankTransfer() {
    }

    public BankTransfer(String id, String reference, Account sender, Account receiver, int amount) {
        this.id = id;
        this.reference = reference;
        this.sender = sender;
        this.receiver = receiver;
//        this.amount = amount;
        this.state = State.CREATED;
    }

    public void settle() {
        this.state = State.SETTLED;
    }

    public String getId() {
        return id;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

//    public int getAmount() {
//        return amount;
//    }

    public State getState() {
        return state;
    }

    public String getReference() {
        return reference;
    }
}
