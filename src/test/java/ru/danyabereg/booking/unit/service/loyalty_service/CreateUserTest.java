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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateUserTest {
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

    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            "BRONZE", 5, 0, 9);
    private static final LoyaltyDto LOYALTY_DTO = new LoyaltyDto(
            "test", 1, STATUS_DISCOUNT_DTO);
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            "BRONZE", 5, 0, 9);
    private static final Loyalty LOYALTY = new Loyalty(
            "test", 1, STATUS_DISCOUNT);

    @Test
    void createUserTest() {
        Mockito.doReturn(LOYALTY)
                .when(loyaltyRepository).saveAndFlush(LOYALTY);
        Mockito.doReturn(STATUS_DISCOUNT_DTO)
                .when(statusDiscountService).findMinStatus();
        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyMapper).mapToDto(LOYALTY);

        LoyaltyDto actualResult = loyaltyService.createUser(LOYALTY.getUserName());

        assertEquals(actualResult, LOYALTY_DTO);
    }
}
