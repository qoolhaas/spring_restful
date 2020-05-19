package web.dto;

import web.model.Role;

import java.util.Set;

public class UserStoreDTO {
    private String firstName;

    private String lastName;

    private String email;

    private Long age;

    private String password;

    private Set<RoleStoreDTO> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleStoreDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleStoreDTO> roles) {
        this.roles = roles;
    }
}
