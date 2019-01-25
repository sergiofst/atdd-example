package pe.atdd.example.service;


import pe.atdd.example.domain.User;
import pe.atdd.example.exception.BlockedUserException;
import pe.atdd.example.exception.InvalidUserAndPasswordException;
import pe.atdd.example.repository.UserRepository;

public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userName, String password) {
        User foundUser = userRepository.findUser(userName);
        if ("password".equals(password)) {
            checkBlockedUser(foundUser);
            return foundUser;
        } else {
            throw new InvalidUserAndPasswordException();
        }
    }

    private void checkBlockedUser(User user) {
        if (user.getBlocked()) {
            throw new BlockedUserException();
        }
    }

}
