package com.example.dailywin.messages;

/**
 * Created by zorana on 2/8/14.
 */
public class Message {


    private String name;
    private MessageStrategyApi strategy;

    public Message (String name) {
        this.name = name;
    }


    public void setStrategy (MessageStrategyApi strategy) {
        this.strategy = strategy;
    }

    public String getMessage () {
        return strategy.getMessage();
    }




}
