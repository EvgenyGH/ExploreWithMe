package ru.practicum.ewmmain.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.exception.compilation.CompilationNotFoundException;
import ru.practicum.ewmmain.model.compilation.Compilation;
import ru.practicum.ewmmain.model.compilation.dto.CompilationDto;
import ru.practicum.ewmmain.model.compilation.dto.CompilationNewDto;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.participationrequest.ParticipationRequest;
import ru.practicum.ewmmain.repository.compilation.CompilationRepository;
import ru.practicum.ewmmain.repository.event.EventRepository;
import ru.practicum.ewmmain.service.event.EventService;
import ru.practicum.ewmmain.service.participationrequest.ParticipationReqService;
import ru.practicum.ewmmain.utils.client.StatisticsClient;
import ru.practicum.ewmmain.utils.mapper.CompilationDtoMapper;
import ru.practicum.ewmmain.utils.mapper.EventDtoMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация Интерфейса {@link CompilationService}
 * @author Evgeny S
 * @see CompilationService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    /**
     * Репозиторий сущности {@link Compilation}
     */
    private final CompilationRepository repository;
    /**
     * Репозиторий сущности {@link Event}
     */
    private final EventRepository eventRepository;
    /**
     * Сервис для работы с {@link Event}.
     */
    private final EventService eventService;
    /**
     * Клиент сервиса статистики.
     */
    private final StatisticsClient client;
    /**
     * Сервис для работы с {@link ParticipationRequest}.
     */
    private final ParticipationReqService reqService;


    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        Pageable pageable = PageRequest.of(from / size, size);

        if (pinned == null) {
            compilations = repository.findAll(pageable).toList();
        } else {
            compilations = repository.findAllByPinned(pinned, pageable);
        }

        return compilations.stream().map(compilation -> CompilationDtoMapper.toDto(compilation,
                compilation.getEvents().stream().map(event -> EventDtoMapper.toDtoShort(event,
                                reqService.getConfRequests(event.getId()),
                                client.getViews(event.getId())))
                        .collect(Collectors.toList()))).collect(Collectors.toList());

    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String.format("Compilation id=%d not found", compId)));

        return CompilationDtoMapper.toDto(compilation, compilation.getEvents().stream()
                .map(event -> EventDtoMapper.toDtoShort(event, reqService.getConfRequests(event.getId()),
                        client.getViews(event.getId())))
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public CompilationDto addCompilation(CompilationNewDto compilationDto) {
        List<Event> events = eventRepository.findAllById(compilationDto.getEvents());

        Compilation compilation = repository.save(new Compilation(null,
                compilationDto.getPinned(), compilationDto.getTitle(), events));

        log.trace("{} Compilation id={} added : {}", LocalDateTime.now(),
                compilation.getId(), compilation);

        return CompilationDtoMapper.toDto(compilation,
                events.stream().map(event -> EventDtoMapper.toDtoShort(event,
                                reqService.getConfRequests(event.getId()), client.getViews(event.getId())))
                        .collect(Collectors.toList()));
    }

    @Override
    public void deleteCompilation(Integer compId) {
        repository.deleteById(compId);
        log.trace("{} Compilation id={} deleted", LocalDateTime.now(), compId);
    }

    @Override
    public void deleteCompilationEvent(Integer compId, Integer eventId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String.format("Compilation id=%d not found", compId)));

        compilation.getEvents().remove(eventService.getEventById(eventId));
        repository.save(compilation);

        log.trace("{} Event id={} removed from compilation id={} : {}",
                LocalDateTime.now(), eventId, compId, compilation);
    }

    @Override
    public void addCompilationEvent(Integer compId, Integer eventId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new CompilationNotFoundException(String.format("Compilation id=%d not found", compId)));

        compilation.getEvents().add(eventService.getEventById(eventId));
        repository.save(compilation);

        log.trace("{} Event id={} added to compilation id={} : {}",
                LocalDateTime.now(), eventId, compId, compilation);
    }

    @Override
    public void unpinCompilation(Integer compId) {
        if (repository.pinUnpinCompilation(compId, false) != 1) {
            throw new CompilationNotFoundException(
                    String.format("Compilation id=%d not found", compId));
        }

        log.trace("{} Compilation id={} unpinned", LocalDateTime.now(), compId);
    }

    @Override
    public void pinCompilation(Integer compId) {
        if (repository.pinUnpinCompilation(compId, true) != 1) {
            throw new CompilationNotFoundException(
                    String.format("Compilation id=%d not found", compId));
        }

        log.trace("{} Compilation id={} pinned", LocalDateTime.now(), compId);
    }
}
