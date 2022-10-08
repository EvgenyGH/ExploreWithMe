package ru.practicum.ewmmain.model.setlocation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.ewmmain.model.setlocation.dto.SetLocationDto;
import ru.practicum.ewmmain.utils.mapper.SetLocDtoMapper;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class SetLocationModelTest {

    @Autowired
    private JacksonTester<SetLocationDto> jsonDto;

    @Test
    void whenSetLocationMappedToDtoAndJsonAndParsedThenResultIsEqualSetLocationDto() throws IOException {
        SetLocation location = new SetLocation(1, "location 1", "description 1",
                10.1f, 10.1f, 1f);
        SetLocationDto locationDto = SetLocDtoMapper.toDto(location);
        JsonContent<SetLocationDto> json = jsonDto.write(locationDto);

        assertThat(json).extractingJsonPathNumberValue("$.id")
                .isEqualTo(locationDto.getId());
        assertThat(json).extractingJsonPathStringValue("$.name")
                .isEqualTo(locationDto.getName());
        assertThat(json).extractingJsonPathStringValue("$.description")
                .isEqualTo(locationDto.getDescription());
        assertThat(json).extractingJsonPathNumberValue("$.latitude")
                .isEqualTo(Double.parseDouble(locationDto.getLatitude().toString()));
        assertThat(json).extractingJsonPathNumberValue("$.longitude")
                .isEqualTo(Double.parseDouble(locationDto.getLongitude().toString()));
        assertThat(json).extractingJsonPathNumberValue("$.radius")
                .isEqualTo(Double.parseDouble(locationDto.getRadius().toString()));
    }
}
