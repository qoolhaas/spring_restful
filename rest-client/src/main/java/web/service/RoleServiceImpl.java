package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.model.Role;
import web.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RestTemplate restTemplate;
    String url  = "http://localhost:8080/roles/";

    //todo сделать эту штуку с дто
    @Override
    public void addRole(Role role) {
        restTemplate.postForObject(url + "save", role, Role.class);
    }

    @Override
    public Role getRoleById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return restTemplate.getForObject(url + "{id}", Role.class, params);
    }

    @Override
    public void deleteRole(Long id) {
        //todo сделать конкатенацию
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        restTemplate.delete(url + "delete/{id}", params);
    }

    @Override
    public Set<Role> getAuthorityById(Long id) {
        return restTemplate.exchange(url + "getset/" + id, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Role>>() {
                }).getBody();
    }
}
