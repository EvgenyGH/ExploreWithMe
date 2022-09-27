package ru.practicum.ewmmain.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StatisticsClientImpl implements StatisticsClient{
    private RestTemplate restTemplate;
    private HttpHeaders defaultHeaders;

    // TODO: 25.09.2022 check autowired
    public StatisticsClientImpl(@Value("${statistics.server.url}") String url, RestTemplateBuilder builder) {
        this.restTemplate = new RestTemplate();

    /*builder
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                        .uriTemplateHandler(new DefaultUriBuilderFactory(url + "/items"))
                .build();*/
        this.defaultHeaders = new HttpHeaders();
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    }

    @Override
    public void sendStatistics(String ip, String uri) {
        // TODO: 25.09.2022 encode the date
        makeRequest(null, "/hit", null, HttpMethod.POST, null);
    }

    @Override
    public Integer getViews(Integer eventId) {

        return null;
    }

    @Override
    public Integer getConfRequests(Integer eventId) {

        return null;
    }


    //Отправка REST запроса и получение ответа
    protected <T> ResponseEntity<Object> makeRequest(Integer eventId, String statsUrl, T body,
                                                     HttpMethod method, Map<String, Object> params) {
        HttpEntity<Object> request = new HttpEntity<>(body, defaultHeaders);
        ResponseEntity<Object> response;

        try {
            if (params == null) {
                response = restTemplate.exchange(statsUrl, method, request, Object.class);
            } else {
                response = restTemplate.exchange(statsUrl, method, request, Object.class, params);
            }
        } catch (HttpStatusCodeException exception) {
            response = new ResponseEntity<>(exception.getResponseBodyAsByteArray(),
                    exception.getStatusCode());
        }

        return response;
    }


}

