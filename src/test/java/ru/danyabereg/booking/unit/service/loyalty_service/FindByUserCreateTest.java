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
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;
import ru.danyabereg.booking.service.LoyaltyService;
import ru.danyabereg.booking.service.StatusDiscountService;

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

    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            DiscountStatus.BRONZE, 5);
    private static final LoyaltyDto LOYALTY_DTO = new LoyaltyDto(
            "test", 1, STATUS_DISCOUNT_DTO);
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            DiscountStatus.BRONZE, 5);
    private static final Loyalty LOYALTY = new Loyalty(
            "test", 1, STATUS_DISCOUNT);

    @Test
    void findByUserCreateTest() {
        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyMapper).mapToDto(LOYALTY);
        Mockito.doReturn(STATUS_DISCOUNT_DTO)
                .when(statusDiscountService).findByStatus(STATUS_DISCOUNT.getStatus());
        Mockito.doReturn(LOYALTY)
                .when(loyaltyRepository).findByUserName(LOYALTY.getUserName());

        LoyaltyDto actualResult = loyaltyService.findByUserCreate(LOYALTY.getUserName());


    }
}
