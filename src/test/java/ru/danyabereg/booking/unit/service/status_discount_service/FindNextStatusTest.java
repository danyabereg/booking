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
public class FindNextStatusTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private StatusDiscountRepository statusDiscountRepository;
    @InjectMocks
    private StatusDiscountService statusDiscountService;

    private static final StatusDiscountDto STATUS_DISCOUNT_SILVER_DTO = new StatusDiscountDto(
            "SILVER", 7, 10, 19);
    private static final StatusDiscountDto STATUS_DISCOUNT_GOLD_DTO = new StatusDiscountDto(
            "GOLD", 10, 20, null);
    private static final StatusDiscount STATUS_DISCOUNT_BRONZE = new StatusDiscount(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscount STATUS_DISCOUNT_SILVER = new StatusDiscount(
            "SILVER", 7, 10, 19);
    private static final StatusDiscount STATUS_DISCOUNT_GOLD = new StatusDiscount(
            "GOLD", 10, 20, null);

    @Test
    void findNextStatusSilverTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT_SILVER))
                .when(statusDiscountRepository).findNextStatus(STATUS_DISCOUNT_BRONZE.getDiscount());

        Mockito.doReturn(STATUS_DISCOUNT_SILVER_DTO)
                .when(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_SILVER);

        var actualResult = statusDiscountService.findNextStatus(STATUS_DISCOUNT_BRONZE.getDiscount());

        assertEquals(actualResult, STATUS_DISCOUNT_SILVER_DTO);
        verify(statusDiscountRepository).findNextStatus(STATUS_DISCOUNT_BRONZE.getDiscount());
        verify(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_SILVER);
    }

    @Test
    void findNextStatusGoldTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT_GOLD))
                .when(statusDiscountRepository).findNextStatus(STATUS_DISCOUNT_SILVER.getDiscount());

        Mockito.doReturn(STATUS_DISCOUNT_GOLD_DTO)
                .when(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_GOLD);

        var actualResult = statusDiscountService.findNextStatus(STATUS_DISCOUNT_SILVER.getDiscount());

        assertEquals(actualResult, STATUS_DISCOUNT_GOLD_DTO);
        verify(statusDiscountRepository).findNextStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        verify(statusDiscountMapper).mapToDto(STATUS_DISCOUNT_GOLD);
    }

}
