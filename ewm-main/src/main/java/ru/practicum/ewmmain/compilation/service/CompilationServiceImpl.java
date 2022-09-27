package ru.practicum.ewmmain.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.client.StatisticsClient;
import ru.practicum.ewmmain.compilation.model.Compilation;
import ru.practicum.ewmmain.compilation.model.CompilationDto;
import ru.practicum.ewmmain.compilation.model.CompilationDtoMapper;
import ru.practicum.ewmmain.compilation.model.CompilationNewDto;
import ru.practicum.ewmmain.compilation.repository.CompilationRepository;
import ru.practicum.ewmmain.event.model.event.Event;
import ru.practicum.ewmmain.event.model.event.EventDtoMapper;
import ru.practicum.ewmmain.event.repository.EventRepository;
import ru.practicum.ewmmain.participationrequest.service.ParticipationReqService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;
    private final StatisticsClient client;
    private final ParticipationReqService reqService;


    @Override
    public CompilationDto getCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        return null;
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

    }

    @Override
    public void addCompilationEvent(Integer compId, Integer eventId) {

    }

    @Override
    public void unpinCompilation(Integer compId) {

    }

    @Override
    public void pinCompilation(Integer compId) {

    }
}
