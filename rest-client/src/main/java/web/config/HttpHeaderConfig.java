package web.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderConfig {

    private final String login;

    private final String password;

    @Autowired
    public HttpHeaderConfig(@Value("${basic.login}") String login, @Value("${basic.password}") String password) {
        this.login = login;
        this.password = password;
    }

    public HttpHeaders getHeaders(){
        String credentials = login + ":" + password;
        String base64Credentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public HttpEntity<String> getEntity() {
        return new HttpEntity<>(getHeaders());
    }
}
