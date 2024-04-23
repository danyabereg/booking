package ru.danyabereg.booking.unit.service.status_discount_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.StatusDiscountRepository;
import ru.danyabereg.booking.service.StatusDiscountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class FindMinStatusTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private StatusDiscountRepository statusDiscountRepository;
    @InjectMocks
    private StatusDiscountService statusDiscountService;

    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            "BRONZE", 5, 0, 9);

    @Test
    void findMinStatusTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT))
                .when(statusDiscountRepository).findMinDiscountStatus();

        Mockito.doReturn(STATUS_DISCOUNT_DTO)
                .when(statusDiscountMapper).mapToDto(STATUS_DISCOUNT);

        var actualResult = statusDiscountService.findMinStatus();

        assertEquals(actualResult, STATUS_DISCOUNT_DTO);
        verify(statusDiscountRepository).findMinDiscountStatus();
        verify(statusDiscountMapper).mapToDto(STATUS_DISCOUNT);
    }
}
