package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password

        if (username.length() < 3) {
            return true;
        }

        if (!username.matches("^[a-z]+$")) {
            return true;
        }

        if (password.length() < 8) {
            return true;
        }

        if (hasSpecialCharacter(password) == true) {
            return false;
        }
        
        return isNumber(password) != true;
    }

    private boolean isNumber(String s) {
        char[] a = s.toCharArray();
        for (char c : a) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSpecialCharacter(String s) {
        return !s.matches("[a-zA-Z0-9]*");
    }
}
