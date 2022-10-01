package ru.practicum.ewmmain.utils.client;

public interface StatisticsClient {
    void sendStatistics(String ip, String uri);

    Integer getViews(Integer eventId);
}
