package web.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.config.HttpHeaderConfig;
import web.dto.RoleDTO;
import web.model.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaderConfig header;

    String url  = "http://localhost:8080/roles/";

    @Override
    public void addRole(Role role) {
        HttpEntity<RoleDTO> request = new HttpEntity<>(modelMapper.map(role, RoleDTO.class),
                header.getHeaders());

        restTemplate.exchange(url + "save",
                HttpMethod.POST,
                request,
                RoleDTO.class);
    }

    @Override
    public Role getRoleById(Long id) {
        return restTemplate.exchange(url + id, HttpMethod.GET,
                header.getEntity(), Role.class).getBody();
    }

    @Override
    public void deleteRole(Long id) {
        restTemplate.exchange(url + "delete/" + id, HttpMethod.DELETE, header.getEntity(), Void.class);
    }

    @Override
    public Set<Role> getAuthorityById(Long id) {
        return restTemplate.exchange(url + "getset/" + id, HttpMethod.GET,
                header.getEntity(),
                new ParameterizedTypeReference<Set<Role>>() {
                }).getBody();
    }
}
