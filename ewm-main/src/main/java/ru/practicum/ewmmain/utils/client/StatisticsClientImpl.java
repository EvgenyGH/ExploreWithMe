package ru.practicum.ewmmain.utils.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmmain.exception.StatServerUnacceptableResponseException;
import ru.practicum.ewmmain.model.statisticsclient.StatRequestDto;
import ru.practicum.ewmmain.model.statisticsclient.StatResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class StatisticsClientImpl implements StatisticsClient {
    private final RestTemplate restTemplate;
    private final HttpHeaders defaultHeaders;

    public StatisticsClientImpl(@Value("${statistics.server.url}") String url) {
        this.restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url));

        this.defaultHeaders = new HttpHeaders();
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    }

    @Override
    public void sendStatistics(String ip, String uri) {
        StatRequestDto body = new StatRequestDto(0, "ewm", uri, ip, LocalDateTime.now());
        makeRequest("/hit", body, HttpMethod.POST, null, new ParameterizedTypeReference<Object>() {
        });
        log.trace("{} Statistics sent ip={} uri={}", LocalDateTime.now(), ip, uri);
    }

    @Override
    public Integer getViews(Integer eventId) {
        ResponseEntity<List<StatResponseDto>> response = makeRequest("/stats?uris={uris}", null,
                HttpMethod.GET, Map.of("uris", "/events/" + eventId),
                new ParameterizedTypeReference<List<StatResponseDto>>() {
                });
        List<StatResponseDto> responseList = response.getBody();
        Integer hits = 0;

        if (responseList != null && responseList.size() > 0) {
            hits = responseList.stream().map(StatResponseDto::getHits).reduce(0, Integer::sum);
        }

        log.trace("{} Statistics received hits={} of uri={}", LocalDateTime.now(), hits, "/events/" + eventId);

        return hits;
    }


    //Отправка REST запроса и получение ответа
    protected <T, U> ResponseEntity<U> makeRequest(String statsUrl, T body, HttpMethod method,
                                                   Map<String, Object> params,
                                                   ParameterizedTypeReference<U> type) {

        HttpEntity<Object> request = new HttpEntity<>(body, defaultHeaders);
        ResponseEntity<U> response;

        try {
            if (params == null) {
                response = restTemplate.exchange(statsUrl, method, request, type);
            } else {
                response = restTemplate.exchange(statsUrl, method, request, type, params);
            }
        } catch (HttpStatusCodeException exception) {
            throw new StatServerUnacceptableResponseException(String.format("Statistics server response status: %s",
                    exception.getStatusCode()));
        }

        log.trace("{} Statistics: method={} response code={}", LocalDateTime.now(),
                method.name(), response.getStatusCode().name());

        return response;
    }
}

