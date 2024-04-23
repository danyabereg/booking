package ru.danyabereg.booking.unit.mapper.status_discount_mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.StatusDiscount;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapToDtoTest {
    private static final StatusDiscount STATUS_DISCOUNT = new StatusDiscount(
            "SILVER", 7, 10, 19);
    private static final StatusDiscountDto STATUS_DISCOUNT_DTO = new StatusDiscountDto(
            "SILVER", 7, 10, 19);

    @Autowired
    private StatusDiscountMapper statusDiscountMapper;

    @Test
    void mapToDtoTest() {
        StatusDiscountDto actualResult = statusDiscountMapper.mapToDto(STATUS_DISCOUNT);
        StatusDiscountDto expectedResult = STATUS_DISCOUNT_DTO;

        assertEquals(actualResult.getDiscountStatus(), expectedResult.getDiscountStatus());
        assertEquals(actualResult.getDiscount(), expectedResult.getDiscount());
    }

}
