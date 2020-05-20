package web.service;

import web.dto.UserResponseDTO;
import web.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
    void saveUser(User user, Long role);
    void updateUser(User user, Long role);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void deleteUser(Long id);
}
