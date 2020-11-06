package com.example.security.controller.exception.custom;

import javax.swing.plaf.SeparatorUI;

public class CUserNotFoundException extends RuntimeException {

    public CUserNotFoundException(String msg, Throwable t){
        super(msg, t);
    }

    public CUserNotFoundException(String msg){
        super(msg);
    }

    public CUserNotFoundException(){
        super();
    }



}
