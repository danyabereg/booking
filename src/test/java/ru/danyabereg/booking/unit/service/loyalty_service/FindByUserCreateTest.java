package ru.danyabereg.booking.unit.service.loyalty_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
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
public class FindByUserCreateTest {
    @Spy
    private StatusDiscountMapper statusDiscountMapper;
    @Mock
    private LoyaltyMapper loyaltyMapper;
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
    private static final StatusDiscountDto STATUS_DISCOUNT_GOLD_DTO = new StatusDiscountDto(
            "GOLD", 10, 20, null);
    private static final StatusDiscount STATUS_DISCOUNT_BRONZE = new StatusDiscount(
            "BRONZE", 5, 0, 9);
    private static final StatusDiscount STATUS_DISCOUNT_SILVER = new StatusDiscount(
            "SILVER", 7, 10, 19);
    private static final StatusDiscount STATUS_DISCOUNT_GOLD = new StatusDiscount(
            "GOLD", 10, 20, null);

    @Test
    void findByUserCreateDefaultTest() {
        var expectedResult = new LoyaltyDto("test", 2, STATUS_DISCOUNT_BRONZE_DTO);
        var loyalty = new Loyalty("test", 2, STATUS_DISCOUNT_BRONZE);
        Mockito.doReturn(expectedResult)
                .when(loyaltyMapper).mapToDto(loyalty);
        Mockito.doReturn(Optional.of(new Loyalty(
                        "test", 1, STATUS_DISCOUNT_BRONZE)))
                .when(loyaltyRepository).findByUserName("test");

        LoyaltyDto actualResult = loyaltyService.findByUserCreate("test");

        verify(loyaltyRepository).findByUserName("test");
        verify(loyaltyMapper).mapToDto(loyalty);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findByUserCreateToSilverTest() {
        var expectedResult = new LoyaltyDto("test", 10, STATUS_DISCOUNT_SILVER_DTO);
        var loyalty = new Loyalty("test", 10, STATUS_DISCOUNT_SILVER);
        Mockito.doReturn(expectedResult)
                .when(loyaltyMapper).mapToDto(loyalty);
        Mockito.doReturn(STATUS_DISCOUNT_SILVER_DTO)
                .when(statusDiscountService).findNextStatus(STATUS_DISCOUNT_BRONZE.getDiscount());
        Mockito.doReturn(Optional.of(new Loyalty(
                        "test", 9, STATUS_DISCOUNT_BRONZE)))
                .when(loyaltyRepository).findByUserName("test");

        LoyaltyDto actualResult = loyaltyService.findByUserCreate("test");

        verify(statusDiscountService).findNextStatus(STATUS_DISCOUNT_BRONZE.getDiscount());
        verify(statusDiscountMapper).mapToEntity(STATUS_DISCOUNT_SILVER_DTO);
        verify(loyaltyMapper).mapToDto(loyalty);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findByUserCreateToGoldTest() {
        var expectedResult = new LoyaltyDto("test", 20, STATUS_DISCOUNT_GOLD_DTO);
        var loyalty = new Loyalty("test", 20, STATUS_DISCOUNT_GOLD);
        Mockito.doReturn(expectedResult)
                .when(loyaltyMapper).mapToDto(loyalty);
        Mockito.doReturn(STATUS_DISCOUNT_GOLD_DTO)
                .when(statusDiscountService).findNextStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        Mockito.doReturn(Optional.of(new Loyalty(
                        "test", 19, STATUS_DISCOUNT_SILVER)))
                .when(loyaltyRepository).findByUserName("test");

        LoyaltyDto actualResult = loyaltyService.findByUserCreate("test");

        verify(statusDiscountService).findNextStatus(STATUS_DISCOUNT_SILVER.getDiscount());
        verify(statusDiscountMapper).mapToEntity(STATUS_DISCOUNT_GOLD_DTO);
        verify(loyaltyMapper).mapToDto(loyalty);
        assertEquals(actualResult, expectedResult);
    }
}
