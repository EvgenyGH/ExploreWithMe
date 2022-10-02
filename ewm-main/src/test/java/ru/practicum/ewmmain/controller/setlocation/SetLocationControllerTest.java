package ru.practicum.ewmmain.controller.setlocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewmmain.controller.admin.setlocation.SetLocationControllerAdm;
import ru.practicum.ewmmain.controller.client.event.SortOption;
import ru.practicum.ewmmain.controller.client.setlocation.SetLocationController;
import ru.practicum.ewmmain.model.event.dto.EventDtoShort;
import ru.practicum.ewmmain.utils.mapper.SetLocDtoMapper;
import ru.practicum.ewmmain.model.setlocation.SetLocation;
import ru.practicum.ewmmain.model.setlocation.dto.SetLocationDto;
import ru.practicum.ewmmain.service.setlocation.SetLocationService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SetLocationControllerAdm.class, SetLocationController.class})
@ActiveProfiles("test")
public class SetLocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private SetLocationService service;
    private SetLocationDto locationDto;

    @BeforeEach
    void initialize(){
        SetLocation location =  new SetLocation(1, "name", "description",
                10f, 20f, 1f);
        this.locationDto = SetLocDtoMapper.toDto(location);
    }


    @Test
    void whenAddLocationThenCallSetLocationServiceAddLocation() throws Exception {
        when(service.addLocation(locationDto)).thenReturn(locationDto);

        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(locationDto)))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(locationDto)));
    }

    @Test
    void whenAddLocationWithInvalidNameThenThenGetStatusBadRequest() throws Exception {
        locationDto.setName("");
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setName("12");
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setName("n".repeat(51));
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAddLocationWithInvalidDescriptionThenThenGetStatusBadRequest() throws Exception {
        locationDto.setDescription("");
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setDescription("9");
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setDescription("n".repeat(1001));
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAddLocationWithInvalidLatitudeThenThenGetStatusBadRequest() throws Exception {
        locationDto.setLatitude(null);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setLatitude(-91f);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setLatitude(91f);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAddLocationWithInvalidLongitudeThenThenGetStatusBadRequest() throws Exception {
        locationDto.setLongitude(null);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setLongitude(-181f);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setLongitude(181f);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAddLocationWithInvalidRadiusThenThenGetStatusBadRequest() throws Exception {
        locationDto.setRadius(null);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());

        locationDto.setRadius(-0.1f);
        mockMvc.perform(post("/admin/location").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetLocationByIdThenCallSetLocationServiceGetLocationById() throws Exception {
        when(service.getLocationById(locationDto.getId())).thenReturn(locationDto);

        mockMvc.perform(get("/location/{locId}", locationDto.getId()))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(locationDto)));
    }

    @Test
    void whenGetLocationByIdInvalidIdThenGetStatusBadRequest() throws Exception {
        locationDto.setId(-1);

        mockMvc.perform(get("/location/{locId}", locationDto.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAllLocationsThenCallSetLocationServiceGetAllLocations() throws Exception {
        when(service.getAllLocations(0, 10)).thenReturn(List.of(locationDto));

        mockMvc.perform(get("/location/all"))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(List.of(locationDto))));
    }

    @Test
    void whenGetEventsInLocationThenCallSetLocationServiceGetEventsInLocation() throws Exception {
        List<EventDtoShort> dtos = List.of(new EventDtoShort("annot", null,
                null, null, 1, null, true, "title", 10));

        when(service.getEventsInLocation(locationDto.getId(), SortOption.EVENT_DATE, null, null,
                null, null, null, null, false, 0,
                10)).thenReturn(dtos);

        mockMvc.perform(get("/location/{locId}/event", locationDto.getId()))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(dtos)));
    }

    @Test
    void whenGetEventsInLocationWithInvalidLocIdThenGetStatusBadRequest() throws Exception {
        mockMvc.perform(get("/location/{locId}/event", 0))
                .andExpectAll(status().isBadRequest());
    }

    @Test
    void whenUpdateDtoThenCallSetLocationServiceUpdateDto() throws Exception {
        when(service.updateDto(locationDto.getId(), locationDto)).thenReturn(locationDto);

        mockMvc.perform(patch("/admin/location/{locId}", locationDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpectAll(status().isOk(), content().json(mapper.writeValueAsString(locationDto)));
    }

    @Test
    void whenUpdateDtoWithInvalidIdThenGetBadRequestStatus() throws Exception {
        mockMvc.perform(patch("/admin/location/{locId}", -1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(locationDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteLocationThenCallSetLocationDeleteLocation() throws Exception {
        mockMvc.perform(delete("/admin/location/{locId}", locationDto.getId()))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteLocation(locationDto.getId());
    }

    @Test
    void whenDeleteLocationWithInvalidIdThenGetBadRequestStatus() throws Exception {
        mockMvc.perform(delete("/admin/location/{locId}", 0))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
