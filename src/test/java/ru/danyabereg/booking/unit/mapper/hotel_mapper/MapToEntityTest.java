package ru.danyabereg.booking.unit.mapper.hotel_mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.entity.Hotel;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapToEntityTest {
    private static final UUID ID = UUID.randomUUID();
    private static final HotelDto HOTEL_DTO = new HotelDto(
            ID, "test", "test", BigDecimal.ONE);
    private static final Hotel HOTEL = new Hotel(
            ID, "test", "test", BigDecimal.ONE);

    @Autowired
    private HotelMapper hotelMapper;

    @Test
    void mapToEntityTest() {
        Hotel actualResult = hotelMapper.mapToEntity(HOTEL_DTO);

        assertEquals(actualResult, HOTEL);
    }
}
