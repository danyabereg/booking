package ru.danyabereg.booking.unit.service.hotel_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.entity.Hotel;
import ru.danyabereg.booking.model.repository.HotelRepository;
import ru.danyabereg.booking.service.HotelService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FindByIdTest {
    private static final UUID HOTEL_ID = UUID.randomUUID();
    public static final Hotel HOTEL = new Hotel(HOTEL_ID, "test", "test", BigDecimal.ONE);
    @Mock
    private HotelRepository hotelRepository;
    @Spy
    private HotelMapper hotelMapper;
    @InjectMocks
    private HotelService hotelService;

    @Test
    void findByIdTest() {
        Mockito.doReturn(Optional.of(HOTEL))
                .when(hotelRepository).findById(HOTEL_ID);

        Optional<HotelDto> actualResult = hotelService.findById(HOTEL_ID);
        assertTrue(actualResult.isPresent());
        HotelDto expectedResult = new HotelDto(HOTEL_ID, "test", "test", BigDecimal.ONE);
        assertEquals(expectedResult, actualResult.get());
    }
}
