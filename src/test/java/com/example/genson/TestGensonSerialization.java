package com.example.genson;

import com.owlike.genson.Genson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestGensonSerialization {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int localServerPort;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + localServerPort + "/user";
    }

    @Test
    public void getUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");

        // Response deserialized to UserDto by Genson
        ResponseEntity<UserDto> responseEntity = testRestTemplate.exchange(baseUrl + "/" + 1,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDto.class);

        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).getId());
    }

    @Test
    public void addUser() {
        HttpHeaders headers = new HttpHeaders();
        // Ensure json is used for both request and response
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        UserDto userDto = new UserDto(3, "Adam Klowski", "adam.klowski@email.com");
        HttpEntity<UserDto> userDtoHttpEntity = new HttpEntity<>(userDto, headers);

        // Post request with UserDto - Genson used as message converter inside testRestTemplate
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(baseUrl,
                HttpMethod.POST,
                userDtoHttpEntity,
                String.class);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

}
