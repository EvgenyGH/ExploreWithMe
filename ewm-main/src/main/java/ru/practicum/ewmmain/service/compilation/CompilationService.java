package ru.practicum.ewmmain.service.compilation;

import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.model.compilation.dto.CompilationNewDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с {@link ru.practicum.ewmmain.model.compilation.Compilation}
 * @author Evgeny S
 * @see CompilationServiceImpl
 */
public interface CompilationService {
    /**
     * Получение подборок событий.
     * @param pinned подборка закреплена на главной странице.
     * @param from начальный элемент выборки.
     * @param size размер выборки.
     * @return возвращает выборку событий {@link List}<{@link CompilationDto}>
     * в соответствии с заданными фильтрами.
     */
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получение подборки событий по его id.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @return возвращает подборку событий {@link CompilationDto} с заданным id.
     */
    CompilationDto getCompilationById(Integer compId);

    /**
     * Добавление новой подборки.
     * @param compilationNewDto экземпляр класса данных для создания новой подборки. Значение
     *                          поля pinned по умолчанию false.
     * @return возвращает созданную подборку {@link CompilationDto}
     */
    CompilationDto addCompilation(CompilationNewDto compilationNewDto);

    /**
     * Удаление подборки.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    void deleteCompilation(Integer compId);

    /**
     * Удалить событие из подборки.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @param eventId Id удаляемого события {@link ru.practicum.ewmmain.model.event.Event}
     */
    void deleteCompilationEvent(Integer compId, Integer eventId);

    /**
     * Добавить событие в подборку.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     * @param eventId Id добавляемого события {@link ru.practicum.ewmmain.model.event.Event}
     */
    void addCompilationEvent(Integer compId, Integer eventId);

    /**
     * Открепить подборку на главной странице.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    void unpinCompilation(Integer compId);

    /**
     * Закрепить подборку на главной странице.
     * @param compId Id подборки {@link ru.practicum.ewmmain.model.compilation.Compilation}
     */
    void pinCompilation(Integer compId);
}
