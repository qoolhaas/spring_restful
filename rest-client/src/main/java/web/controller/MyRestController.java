package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @PostMapping("add/{role}")
    public ResponseEntity<Void> saveUser(@RequestBody User user, @PathVariable Long role) {
        ResponseEntity<Void> resp;

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getFirstName().isEmpty()
                || user.getLastName().isEmpty() || user.getAge() == null) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                user.setPassword(bCryptEncoder.encode(user.getPassword()));
                userService.saveUser(user, role);
                resp = new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                resp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        return resp;
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("admin")
    public ResponseEntity<User> getAuthAdmin(@AuthenticationPrincipal User admin) {
        return ResponseEntity.ok(admin);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        ResponseEntity<User> resp;

        try {
            User user = userService.getUserById(id);

            resp = ResponseEntity.ok(user);
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

    @PostMapping(value = "/update/{role}")
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable Long role) {
        ResponseEntity<Void> resp;

        /*User oldUser = userService.getUserById(user.getId());

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

        if(user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(bCryptEncoder.encode(user.getPassword()));
        }*/
        //todo здесь могут быть проблемы
        user.setPassword(bCryptEncoder.encode(user.getPassword()));

        try {
            userService.updateUser(user, role);
            resp = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }
}
