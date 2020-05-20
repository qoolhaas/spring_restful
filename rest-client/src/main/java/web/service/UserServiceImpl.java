package web.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.config.HttpHeaderConfig;
import web.dto.UserResponseDTO;
import web.dto.UserStoreDTO;
import web.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaderConfig header;

    String url  = "http://localhost:8080/users/";

/*    private HttpHeaders getHeaders(){
        //String credentials = "admis:paccword";
        String credentials = login + ":" + pass;
        String base64Credentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }*/

    @Override
    public List<User> listUsers() {
        return restTemplate.exchange(url + "list", HttpMethod.GET,
                header.getEntity(),
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    @Override
    public void saveUser(User user, Long role) {
        HttpEntity<UserStoreDTO> request = new HttpEntity<>(modelMapper.map(user, UserStoreDTO.class),
                header.getHeaders());

        restTemplate.exchange(url + "save/" + role,
                HttpMethod.POST,
                request,
                UserStoreDTO.class);
    }

    @Override
    public void updateUser(User user, Long role) {
        HttpEntity<UserResponseDTO> request = new HttpEntity<>(modelMapper.map(user, UserResponseDTO.class),
                header.getHeaders());

        restTemplate.exchange(url + "update/" + role,
                HttpMethod.POST,
                request,
                UserResponseDTO.class);
    }

    @Override
    public User getUserById(Long id) {
        return restTemplate.exchange(url + id, HttpMethod.GET,
                header.getEntity(), User.class).getBody();
    }

    @Override
    public User getUserByEmail(String email) {
        return restTemplate.exchange(url + "email/" + email,
                HttpMethod.GET, header.getEntity(), User.class).getBody();
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.exchange(url + "delete/" + id, HttpMethod.DELETE, header.getEntity(), Void.class);
    }
}
