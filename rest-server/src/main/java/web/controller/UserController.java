package web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.dto.RoleResponseDTO;
import web.dto.UserResponseDTO;
import web.dto.UserStoreDTO;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

/*    @PostMapping("save")
    public ResponseEntity<Void> saveUser(@RequestBody UserStoreDTO user) {
        ResponseEntity<Void> resp;

        try {

            user.getRoles().forEach(x -> System.out.println(x.getAuthority()));
            User userSecond = modelMapper.map(user, User.class);
            userSecond.getRoles().forEach(x -> System.out.println(x.getAuthority()));
            userService.saveUser(userSecond);
            resp = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return resp;
    }*/

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

/*    @GetMapping("list")
    public ResponseEntity<List<UserResponseDTO>> getList() {
        return ResponseEntity.ok(userService.listUsers().stream()
                .map(x -> modelMapper.map(x, UserResponseDTO.class))
                .collect(Collectors.toList())
        );
    }*/

    @GetMapping("list")
    public ResponseEntity<List<User>> getList() {
        return ResponseEntity.ok(userService.listUsers());
    }

/*    @GetMapping("email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        ResponseEntity<UserResponseDTO> resp;

        try {
            UserResponseDTO user = modelMapper.map(userService.getUserByEmail(email), UserResponseDTO.class);
            resp = ResponseEntity.ok(user);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }*/

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

/*    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        ResponseEntity<UserResponseDTO> resp;

        try {
            UserResponseDTO userDto = modelMapper.map(userService.getUserById(id), UserResponseDTO.class);
            resp = ResponseEntity.ok(userDto);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }*/

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
