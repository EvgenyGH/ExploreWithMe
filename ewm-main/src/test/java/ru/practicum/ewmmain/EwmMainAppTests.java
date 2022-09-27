package ru.practicum.ewmmain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmstats.model.EndpointHit;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EwmMainAppTests {

    @Test
    void sd() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        EndpointHit body = new EndpointHit(0, "ewm", "http://localhost:9090/hit", "ip", LocalDateTime.now());
        HttpEntity<EndpointHit> request = new HttpEntity<>(body, headers);

        restTemplate.exchange("http://localhost:9090/hit",
                HttpMethod.POST,
                request, Object.class);

    }
}
