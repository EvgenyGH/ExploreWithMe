package ru.practicum.ewmmain.event.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmain.model.event.Event;
import ru.practicum.ewmmain.model.event.State;
import ru.practicum.ewmmain.repository.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/test-data.sql"})
public class EventRepositorySetLocationTest {
    private final EventRepository eventRepository;

    @Test
    @Transactional
    void whenGetEventsInLocThenReturnEventList() {
        List<Event> events = eventRepository.getEventsInLoc(State.PUBLISHED, "looking for it",
                List.of(2, 3), true, LocalDateTime.now(), LocalDateTime.now().plusYears(1000),
                10f, 10f, 2f, PageRequest.of(0, 10));

        assertThat(events.size()).isEqualTo(2);
        assertThat(events).map(Event::getId).containsExactly(7, 8);
    }


    @Test
    @Transactional
    void whenGetAvailEventsInLocThenReturnEventList() {
        List<Event> events = eventRepository.getAvailEventsInLoc(State.PUBLISHED, "looking for it",
                List.of(2, 3), true, LocalDateTime.now(), LocalDateTime.now().plusYears(1000),
                10f, 10f, 2f, PageRequest.of(0, 10));

        assertThat(events.size()).isEqualTo(1);
        assertThat(events).map(Event::getId).containsExactly( 8);
    }
}
