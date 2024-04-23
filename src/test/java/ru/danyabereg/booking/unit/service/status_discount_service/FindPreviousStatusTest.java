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
public class FindPreviousStatusTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private StatusDiscountRepository statusDiscountRepository;
    @InjectMocks
    private StatusDiscountService statusDiscountService;

    private static final StatusDiscountDto STATUS_DISCOUNT_BRONZE_DTO = new StatusDiscountDto(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscountDto STATUS_DISCOUNT_SILVER_DTO = new StatusDiscountDto(
            "SILVER", 7, 10, 19);
    private static final StatusDiscount STATUS_DISCOUNT_BRONZE = new StatusDiscount(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscount STATUS_DISCOUNT_SILVER = new StatusDiscount(
            "SILVER", 7, 10, 19);
    private static final StatusDiscount STATUS_DISCOUNT_GOLD = new StatusDiscount(
            "GOLD", 10, 20, null);

    @Test
    void findPreviousStatusSilverTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT_SILVER))
                .when(statusDiscountRepository).findPreviousStatus(STATUS_DISCOUNT_GOLD.getDiscount());

        Mockito.doReturn(STATUS_DISCOUNT_SILVER_DTO)
                .when(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_SILVER);

        var actualResult = statusDiscountService.findPreviousStatus(STATUS_DISCOUNT_GOLD.getDiscount());

        assertEquals(actualResult, STATUS_DISCOUNT_SILVER_DTO);
        verify(statusDiscountRepository).findPreviousStatus(STATUS_DISCOUNT_GOLD.getDiscount());
        verify(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_SILVER);
    }

    @Test
    void findPreviousStatusBronzeTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT_BRONZE))
                .when(statusDiscountRepository).findPreviousStatus(STATUS_DISCOUNT_SILVER.getDiscount());

        Mockito.doReturn(STATUS_DISCOUNT_BRONZE_DTO)
                .when(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_BRONZE);

        var actualResult = statusDiscountService.findPreviousStatus(STATUS_DISCOUNT_SILVER.getDiscount());

        assertEquals(actualResult, STATUS_DISCOUNT_BRONZE_DTO);
        verify(statusDiscountRepository).findPreviousStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        verify(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_BRONZE);
    }
}
