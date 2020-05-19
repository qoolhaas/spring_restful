package web.service;

import web.dto.UserResponseDTO;
import web.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    void saveUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void deleteUser(Long id);
}
