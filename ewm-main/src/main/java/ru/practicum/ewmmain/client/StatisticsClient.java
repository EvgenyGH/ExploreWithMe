package ru.practicum.ewmmain.client;

public interface StatisticsClient {
    void sendStatistics(String ip, String uri);

    Integer getViews(Integer eventId);

    Integer getConfRequests(Integer eventId);
}
