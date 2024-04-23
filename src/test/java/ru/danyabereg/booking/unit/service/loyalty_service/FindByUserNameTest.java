package ru.danyabereg.booking.unit.service.loyalty_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.StatusDiscount;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;
import ru.danyabereg.booking.service.LoyaltyService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FindByUserNameTest {
    @Mock
    private LoyaltyMapper loyaltyMapper;
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
    void findByUserNameTest() {
        Mockito.doReturn(Optional.of(LOYALTY))
                .when(loyaltyRepository).findByUserName(LOYALTY_DTO.getUserName());
        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyMapper).mapToDto(LOYALTY);

        LoyaltyDto actualResult = loyaltyService.findByUserName(LOYALTY_DTO.getUserName());

        assertEquals(actualResult, LOYALTY_DTO);
    }

    @Test
    void findByUserNameThrowsTest() {
        Mockito.doReturn(Optional.empty())
                .when(loyaltyRepository).findByUserName(LOYALTY_DTO.getUserName());
        Mockito.doReturn(LOYALTY_DTO)
                .when(loyaltyMapper).mapToDto(LOYALTY);

        assertThrows(NoSuchElementException.class, () ->
                loyaltyService.findByUserName(LOYALTY_DTO.getUserName()));
    }
}
