package ru.practicum.ewmmain.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmain.compilation.model.CompilationDto;
import ru.practicum.ewmmain.compilation.model.CompilationNewDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    @Override
    public CompilationDto getCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        return null;
    }

    @Override
    public CompilationDto addCompilation(CompilationNewDto compilationNewDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Integer compId) {

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
