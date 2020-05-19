package web.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
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

    String url  = "http://localhost:8080/users/";

    private HttpHeaders getHeaders(){
        String credentials = "admin:password";
        String base64Credentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public List<User> listUsers() {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());

        /*List<UserResponseDTO> list = restTemplate.exchange(url + "list", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserResponseDTO>>() {
                }).getBody();

        return list.stream()
                .map(x -> modelMapper.map(x, User.class))
                .collect(Collectors.toList());*/
        return restTemplate.exchange(url + "list", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    @Override
    public void saveUser(User user) {
        /*restTemplate.postForObject(url + "save",
                modelMapper.map(user, UserStoreDTO.class),
                UserStoreDTO.class);*/
        restTemplate.postForObject(url + "save", user, User.class);
    }

    @Override
    public User getUserById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        UserResponseDTO user = restTemplate.getForObject(url + "{id}", UserResponseDTO.class, params);

        return modelMapper.map(user, User.class);
    }

    @Override
    public User getUserByEmail(String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        UserResponseDTO user = restTemplate.getForObject(url + "email/{email}", UserResponseDTO.class, params);

        return modelMapper.map(user, User.class);
    }

    @Override
    public void deleteUser(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        restTemplate.delete(url + "delete/{id}", params);
    }
}
