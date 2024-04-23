package ru.danyabereg.booking.unit.service.loyalty_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;
import ru.danyabereg.booking.service.LoyaltyService;
import ru.danyabereg.booking.service.StatusDiscountService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class FindByUserDeleteTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private StatusDiscountService statusDiscountService;
    @Mock
    private LoyaltyRepository loyaltyRepository;
    @InjectMocks
    private LoyaltyService loyaltyService;

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
    void findByUserDeleteDefaultTest() {
        var loyalty = new Loyalty("test", 1, STATUS_DISCOUNT_BRONZE);
        Mockito.doReturn(Optional.of(loyalty))
                .when(loyaltyRepository).findByUserName("test");

        loyaltyService.findByUserDelete("test");

        verify(loyaltyRepository).findByUserName("test");
        assertEquals(loyalty.getBookingQuantity(), 0);
    }

    @Test
    void findByUserDeleteZeroTest() {
        var loyalty = new Loyalty("test", 0, STATUS_DISCOUNT_BRONZE);
        Mockito.doReturn(Optional.of(loyalty))
                .when(loyaltyRepository).findByUserName("test");

        loyaltyService.findByUserDelete("test");

        verify(loyaltyRepository).findByUserName("test");
        assertEquals(loyalty.getBookingQuantity(), 0);
    }

    @Test
    void findByUserDeleteToSilverTest() {
        var loyalty = new Loyalty("test", 20, STATUS_DISCOUNT_GOLD);
        Mockito.doReturn(STATUS_DISCOUNT_SILVER_DTO)
                .when(statusDiscountService).findPreviousStatus(STATUS_DISCOUNT_GOLD.getDiscount());
        Mockito.doReturn(Optional.of(loyalty))
                .when(loyaltyRepository).findByUserName("test");

        loyaltyService.findByUserDelete("test");

        verify(loyaltyRepository).findByUserName("test");
        verify(statusDiscountService).findPreviousStatus(STATUS_DISCOUNT_GOLD.getDiscount());
        verify(statusDiscountMapper).mapToEntity(STATUS_DISCOUNT_SILVER_DTO);
        assertEquals(loyalty.getBookingQuantity(), 19);
        assertEquals(loyalty.getStatus(), STATUS_DISCOUNT_SILVER);
    }

    @Test
    void findByUserDeleteToBronzeTest() {
        var loyalty = new Loyalty("test", 10, STATUS_DISCOUNT_SILVER);
        Mockito.doReturn(STATUS_DISCOUNT_BRONZE_DTO)
                .when(statusDiscountService).findPreviousStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        Mockito.doReturn(Optional.of(loyalty))
                .when(loyaltyRepository).findByUserName("test");

        loyaltyService.findByUserDelete("test");

        verify(loyaltyRepository).findByUserName("test");
        verify(statusDiscountService).findPreviousStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        verify(statusDiscountMapper).mapToEntity(STATUS_DISCOUNT_BRONZE_DTO);
        assertEquals(loyalty.getBookingQuantity(), 9);
        assertEquals(loyalty.getStatus(), STATUS_DISCOUNT_BRONZE);
    }
}
