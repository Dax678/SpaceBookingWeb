package com.example.spacebookingweb.Configuration.Security.auth;

import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser implements HttpSessionBindingListener {
    private String username;

    public LoggedUser() {
    }

    public LoggedUser(String username) {
        this.username = username;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        username = ((LoggedUser) event.getValue()).getUsername();
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        if(username == ((LoggedUser) event.getValue()).getUsername()) {
            username = null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoggedUser{" +
                "username='" + username + '\'' +
                '}';
    }
}
