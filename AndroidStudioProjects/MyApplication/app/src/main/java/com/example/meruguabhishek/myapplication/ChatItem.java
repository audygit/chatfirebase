package com.example.meruguabhishek.myapplication;

import java.io.Serializable;

/**
 * Created by meruguabhishek on 2017-03-16.
 */

public class ChatItem implements Serializable{
    private String message;
    private String user;
    private String type;
    public ChatItem(String message,String user,String type){
        setMessage(message);
        setType(type);
        setUser(user);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
