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
        
        if (checkUsername(username) || checkPassword(password)) {
            return true;
        }
        
        return false;
    }
    
    private boolean checkUsername(String username) {
        if (username.length() < 3) {
            return true;
        }
        
        for (char c : username.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkPassword(String password) {
        if (password.length() < 8) {
            return true;
        }
        
        for (char p : password.toCharArray()) {
            if (Character.isDigit(p) || !Character.isLetterOrDigit(p)) {
                return false;
            }
        }
        
        return true;
    }
    
}
