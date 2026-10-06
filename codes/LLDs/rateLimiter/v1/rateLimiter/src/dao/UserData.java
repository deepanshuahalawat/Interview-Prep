package dao;

import entity.User;
import enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserData {

    private HashMap<Long, User> userMap = new HashMap<>();

    public boolean register(User user){
        if(userMap.containsKey(user.getId())){
            return false;
        }
        userMap.put(user.getId(), user);
        return true;
    }

    public User getUserById(long id){
        return userMap.get(id);
    }

    public UserType getUserType(Long id){
        if(userMap.containsKey(id)){
            return userMap.get(id).getUserType();
        }
        return null;
    }
}
