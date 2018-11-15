package ohtu.authentication;

import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

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

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
            return status;
        }

        if (username.length() < 3) {
            status.addError("username should have at least 3 characters");
            return status;
        }

        for (char c : username.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isLowerCase(c)) {
                status.addError("username should consist of only lowercase letters");
                return status;
            }
        }

        if (password.length() < 8) {
            status.addError("password should have at least 8 characters");
            return status;
        }

        if (!password.equals(passwordConfirmation)) {
            status.addError("password and password confirmation do not match");
            return status;
        }

        boolean hasSpec = false;
        for (char p : password.toCharArray()) {
            if (Character.isDigit(p) || !Character.isLetterOrDigit(p)) {
                hasSpec = true;
            }
        }

        if (!hasSpec) {
            status.addError("password should have at least one digit or special character");
            return status;
        }

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }

        return status;
    }

}
