package ru.danyabereg.booking.unit.service.status_discount_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.StatusDiscountRepository;
import ru.danyabereg.booking.service.StatusDiscountService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetDiscountByDiscountStatusTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private StatusDiscountRepository statusDiscountRepository;
    @InjectMocks
    private StatusDiscountService statusDiscountService;

    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            DiscountStatus.SILVER, 7);

    @Test
    void getDiscountByDiscountStatusTest() {
        Mockito.doReturn(Optional.of(STATUS_DISCOUNT))
                .when(statusDiscountRepository).findByStatus(STATUS_DISCOUNT.getStatus());

        Integer actualResult = statusDiscountService
                .getDiscountByDiscountStatus(STATUS_DISCOUNT.getStatus());

        assertEquals(actualResult, STATUS_DISCOUNT.getDiscount());
    }

    @Test
    void getDiscountByDiscountStatusThrowsTest() {
        Mockito.doReturn(Optional.empty())
                .when(statusDiscountRepository).findByStatus(STATUS_DISCOUNT.getStatus());

        assertThrows(NoSuchElementException.class, () ->
                statusDiscountService.getDiscountByDiscountStatus(STATUS_DISCOUNT.getStatus()));
    }
}
