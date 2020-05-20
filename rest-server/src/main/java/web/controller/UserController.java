package web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @PostMapping("save/{id}")
    public ResponseEntity<Void> saveUser(@RequestBody User user, @PathVariable Long id) {
        ResponseEntity<Void> resp;

        try {
            user.setRoles(roleService.getAuthorityById(id));
            userService.saveUser(user);
            resp = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return resp;
    }

    @PostMapping("update/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable Long id) {
        ResponseEntity<Void> resp;

        User oldUser = userService.getUserById(user.getId());

        if(user.getFirstName().isEmpty()) {
            user.setFirstName(oldUser.getFirstName());
        }

        if(user.getLastName().isEmpty()) {
            user.setLastName(oldUser.getLastName());
        }

        if(user.getAge() == null) {
            user.setAge(oldUser.getAge());
        }

        if (user.getEmail().isEmpty()) {
            user.setEmail(oldUser.getEmail());
        }
        //todo вот тут скорее всего дублируется шифровка
        if(user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(bCryptEncoder.encode(user.getPassword()));
        }

        try {
            user.setRoles(roleService.getAuthorityById(id));
            userService.saveUser(user);
            resp = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return resp;
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        ResponseEntity<User> resp;

        try {
            resp = ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        ResponseEntity<User> resp;

        try {
            resp = ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
