package web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.RoleResponseDTO;
import web.dto.UserResponseDTO;
import web.dto.UserStoreDTO;
import web.model.Role;
import web.model.User;
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
    private ModelMapper modelMapper;

    @PostMapping("save")
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
    }

    @GetMapping("list")
    public ResponseEntity<List<UserResponseDTO>> getList() {
        return ResponseEntity.ok(userService.listUsers().stream()
                .map(x -> modelMapper.map(x, UserResponseDTO.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        ResponseEntity<UserResponseDTO> resp;

        try {
            UserResponseDTO user = modelMapper.map(userService.getUserByEmail(email), UserResponseDTO.class);
            resp = ResponseEntity.ok(user);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return resp;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        ResponseEntity<UserResponseDTO> resp;

        try {
            UserResponseDTO userDto = modelMapper.map(userService.getUserById(id), UserResponseDTO.class);
            resp = ResponseEntity.ok(userDto);
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
