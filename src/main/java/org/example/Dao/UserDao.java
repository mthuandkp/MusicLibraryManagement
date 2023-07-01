package org.example.Dao;

import org.example.Constant.AppConstant;
import org.example.Dto.User;
import org.example.Utils.FileUtils;
import org.example.Utils.SHA256;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Optional;

public class UserDao {
    public List<User> findAll() {
        String json =  FileUtils.readJsonFile(AppConstant.USER_FILE_PATH);
        List<User> userList = new Gson().fromJson(json,new TypeToken<List<User>>(){}.getType());
        return userList;
    }

    public User login(String username, String password) {
        String json = FileUtils.readJsonFile(AppConstant.USER_FILE_PATH);
        List<User> userList = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
        Optional<User> userOptional = userList.stream()
                .filter(user -> {
                    String hashInputPassword = SHA256.hash(password);
                    return user.getUsername().equals(username) && user.getPassword().equals(hashInputPassword);
                })
                .findFirst();

        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public User findById(String userId) {
        String json = FileUtils.readJsonFile(AppConstant.USER_FILE_PATH);
        List<User> userList = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
        Optional<User> userOptional = userList.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();

        return userOptional.isPresent() ? userOptional.get() : null;
    }
}
