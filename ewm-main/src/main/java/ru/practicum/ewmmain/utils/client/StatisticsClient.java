package ru.practicum.ewmmain.utils.client;

/**
 * Интерфейс клиента сервиса статистики.
 * @author Evgeny S
 */
public interface StatisticsClient {
    /**
     * Отправка статистики.
     * @param ip ip адрес запросившего ресурс пользователя.
     * @param uri uri ресурса.
     */
    void sendStatistics(String ip, String uri);

    /**
     * Получить количество просмотров события.
     * @param eventId id события.
     * @return возвращает количество просмотров заданного события.
     */
    Integer getViews(Integer eventId);
}
