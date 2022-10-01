package ru.practicum.ewmmain.setlocation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmmain.client.StatisticsClient;
import ru.practicum.ewmmain.event.controller.SortOption;
import ru.practicum.ewmmain.event.model.category.Category;
import ru.practicum.ewmmain.event.model.event.Event;
import ru.practicum.ewmmain.event.model.event.EventDtoMapper;
import ru.practicum.ewmmain.event.model.event.State;
import ru.practicum.ewmmain.event.repository.EventRepository;
import ru.practicum.ewmmain.participationrequest.service.ParticipationReqService;
import ru.practicum.ewmmain.setlocation.exception.SetLocationNotFoundException;
import ru.practicum.ewmmain.setlocation.model.SetLocDtoMapper;
import ru.practicum.ewmmain.setlocation.model.SetLocation;
import ru.practicum.ewmmain.setlocation.model.SetLocationDto;
import ru.practicum.ewmmain.setlocation.repository.SetLocationRepository;
import ru.practicum.ewmmain.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetLocationServiceImplTest {
    @InjectMocks
    private SetLocationServiceImpl service;
    @Mock
    private SetLocationRepository repository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ParticipationReqService reqService;
    @Mock
    private StatisticsClient client;

    private SetLocationDto locationDto;

    private SetLocation location;

    @BeforeEach
    void initialize() {
        this.location = new SetLocation(1, "name", "description",
                10f, 20f, 1f);
        this.locationDto = SetLocDtoMapper.toDto(location);
    }

    @Test
    void whenAddLocationThenReturnSetLocationDto() {
        when(repository.save(any())).thenReturn(location);
        assertThat(service.addLocation(locationDto)).isEqualTo(locationDto);
    }

    @Test
    void whenGetLocationByIdThenReturnSetLocationDto() {
        when(repository.findById(location.getId())).thenReturn(Optional.of(location));
        assertThat(service.getLocationById(location.getId())).isEqualTo(locationDto);
    }

    @Test
    void whenGetLocationByIdThenThrowSetLocationNotFoundException() {
        when(repository.findById(location.getId()))
                .thenThrow(new SetLocationNotFoundException(
                        String.format("Set location id=%d not found", location.getId())));
        assertThatThrownBy(() -> service.getLocationById(location.getId()))
                .isInstanceOf(SetLocationNotFoundException.class);
    }

    @Test
    void whenGetAllLocationsThenReturnListSetLocationDto() {
        when(repository.findAll(PageRequest.of(0 / 10, 10)))
                .thenReturn(new PageImpl<>(List.of(location)));

        assertThat(service.getAllLocations(0, 10)).isEqualTo(List.of(locationDto));
    }

    @Test
    void whenGetEventsInLocationThenReturnListEventDtoShort() {
        Event event = new Event(2, "annotation", null, null, null,
                new User(1, "name", "email@nj.rt"), null, null, null,
                null, null, State.PUBLISHED, null, new Category(11,
                "theatre"));

        when(repository.findById(locationDto.getId())).thenReturn(Optional.of(location));
        when(eventRepository.getEventsInLoc(any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any())).thenReturn(List.of(event));
        when(reqService.getConfRequests(event.getId())).thenReturn(10);
        when(client.getViews(event.getId())).thenReturn(200);

        assertThat(service.getEventsInLocation(location.getId(), SortOption.EVENT_DATE, null, null,
                null, null, null, null, false, 0, 10))
                .isEqualTo(List.of(EventDtoMapper.toDtoShort(event, 10, 200)));
    }

    @Test
    void whenGetEventsInLocationAvailThenReturnListEventDtoShort() {
        Event event = new Event(2, "annotation", null, null, null,
                new User(1, "name", "email@nj.rt"), null, null, null,
                null, null, State.PUBLISHED, null, new Category(11,
                "theatre"));

        when(repository.findById(locationDto.getId())).thenReturn(Optional.of(location));
        when(eventRepository.getAvailEventsInLoc(any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any())).thenReturn(List.of(event));
        when(reqService.getConfRequests(event.getId())).thenReturn(10);
        when(client.getViews(event.getId())).thenReturn(200);

        assertThat(service.getEventsInLocation(location.getId(), SortOption.EVENT_DATE, null, null,
                null, null, null, null, true, 0, 10))
                .isEqualTo(List.of(EventDtoMapper.toDtoShort(event, 10, 200)));
    }

    @Test
    void whenGetEventsInLocationThenThrowSetLocationNotFoundException() {
        when(repository.findById(locationDto.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getEventsInLocation(location.getId(), SortOption.EVENT_DATE, null,
                null, null, null, null, null, true, 0,
                10)).isInstanceOf(SetLocationNotFoundException.class);
    }

    @Test
    void whenUpdateDtoThenReturnSetLocationDto() {
        locationDto.setName("updated");
        when(repository.findById(locationDto.getId())).thenReturn(Optional.ofNullable(location));

        assertThat(service.updateDto(location.getId(), locationDto)).isEqualTo(locationDto);
        //new SetLocationNotFoundException(String.format("Set location id=%d not found", locId)));
    }

    @Test
    void whenUpdateDtoThenThrowSetLocationNotFoundException() {
        locationDto.setName("updated");
        when(repository.findById(locationDto.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateDto(location.getId(), locationDto))
                .isInstanceOf(SetLocationNotFoundException.class);
    }

    @Test
    void whenDeleteLocationThenCallRepositoryDeleteById() {
        service.deleteLocation(location.getId());
        verify(repository, times(1)).deleteById(location.getId());
    }
}
