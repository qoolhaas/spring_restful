package web.dto;

import web.model.User;

import java.util.Set;

public class RoleStoreDTO {
    private Long id;

    private String authority;

    private Set<UserStoreDTO> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<UserStoreDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserStoreDTO> users) {
        this.users = users;
    }
}
