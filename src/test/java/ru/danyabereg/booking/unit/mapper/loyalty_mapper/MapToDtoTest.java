package ru.danyabereg.booking.unit.mapper.loyalty_mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.StatusDiscount;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapToDtoTest {
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            DiscountStatus.SILVER, 7);
    private static final Loyalty LOYALTY = new Loyalty(
            "test", 10, STATUS_DISCOUNT);
    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            DiscountStatus.SILVER, 7);
    private static final LoyaltyDto LOYALTY_DTO = new LoyaltyDto(
            "test", 10, STATUS_DISCOUNT_DTO);

    @Autowired
    private LoyaltyMapper loyaltyMapper;

    @Test
    void mapToDtoTest() {
        LoyaltyDto actualResult = loyaltyMapper.mapToDto(LOYALTY);

        assertEquals(actualResult, LOYALTY_DTO);
    }
}
