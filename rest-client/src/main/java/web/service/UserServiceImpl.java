package web.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import web.dto.UserResponseDTO;
import web.dto.UserStoreDTO;
import web.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    HttpEntity<String> getRequest = new HttpEntity<>(getHeaders());

    String url  = "http://localhost:8080/users/";

    private HttpHeaders getHeaders(){
        String credentials = "admis:paccword";
        String base64Credentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    @Override
    public List<User> listUsers() {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());

        return restTemplate.exchange(url + "list", HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    @Override
    public void saveUser(User user, Long role) {
        HttpEntity<UserStoreDTO> request = new HttpEntity<>(modelMapper.map(user, UserStoreDTO.class),
                getHeaders());
        /*restTemplate.postForObject(url + "save/" + role,
                modelMapper.map(user, UserStoreDTO.class),
                UserStoreDTO.class);*/
        restTemplate.exchange(url + "save/" + role,
                HttpMethod.POST,
                request,
                UserStoreDTO.class);
    }

    @Override
    public void updateUser(User user, Long role) {
        HttpEntity<UserResponseDTO> request = new HttpEntity<>(modelMapper.map(user, UserResponseDTO.class),
                getHeaders());
        /*restTemplate.postForObject(url + "update/" + role,
                modelMapper.map(user, UserResponseDTO.class),
                UserResponseDTO.class);*/
        restTemplate.exchange(url + "update/" + role,
                HttpMethod.POST,
                request,
                UserResponseDTO.class);
    }

    @Override
    public User getUserById(Long id) {
        return restTemplate.exchange(url + id, HttpMethod.GET,
                getRequest, User.class).getBody();
    }

    @Override
    public User getUserByEmail(String email) {
        return restTemplate.exchange(url + "email/" + email,
                HttpMethod.GET, getRequest, User.class).getBody();
    }
    //todo может загрузка в джейкьюэри связана с нахождением методов, т.е. в какое время что прогружается
    @Override
    public void deleteUser(Long id) {
        //restTemplate.delete(url + "delete/" + id, getRequest);
        restTemplate.exchange(url + "delete/" + id, HttpMethod.DELETE, getRequest, Void.class);
    }
}
