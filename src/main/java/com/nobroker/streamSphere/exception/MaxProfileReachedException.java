package com.nobroker.streamSphere.exception;

import com.nobroker.streamSphere.models.Account;

public class MaxProfileReachedException extends RuntimeException{
    public MaxProfileReachedException(Account account){
        super("Max number of profile for the user " + account.getName()  + " reached!");
    }
}
