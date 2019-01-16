package com.example.adina.theatretickets.Models.Builders;

import com.example.adina.theatretickets.Models.User;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(long id) {
        user.setUserID(id);
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(int role) {
        user.setRole(role);
        return this;
    }

    public User build(){
        return user;
    }
}
