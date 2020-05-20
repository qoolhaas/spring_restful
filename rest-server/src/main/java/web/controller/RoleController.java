package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.service.RoleService;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("save")
    public ResponseEntity<Void> saveUser(@RequestBody Role role) {
        ResponseEntity<Void> resp;

        try {
            roleService.addRole(role);
            resp = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            resp = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return resp;
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("getset/{id}")
    public ResponseEntity<Set<Role>> getSet(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getAuthorityById(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
