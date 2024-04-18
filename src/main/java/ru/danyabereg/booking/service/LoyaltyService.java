package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;

import java.sql.SQLException;
import java.util.Optional;

@RequiredArgsConstructor
@Retryable(
        retryFor = {SQLException.class},
        maxAttemptsExpression = "${db.connection.retry.max_attempts}",
        backoff = @Backoff(delayExpression = "{db.connection.retry.backoff_ms}")
)
@Service
@Transactional
public class LoyaltyService {
    private final LoyaltyMapper loyaltyMapper;
    private final LoyaltyRepository loyaltyRepository;
    private final StatusDiscountService discountService;
    private final StatusDiscountMapper discountMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public LoyaltyDto createUser(String userName) {
        Loyalty loyalty = loyaltyRepository.saveAndFlush(Loyalty.builder()
                .userName(userName)
                .bookingQuantity(1)
                .status(discountMapper.mapToEntity(discountService.findByStatus(DiscountStatus.BRONZE)))
                .build());
        return loyaltyMapper.mapToDto(loyalty);
    }

    public LoyaltyDto findByUserName(String userName) {
        return loyaltyRepository.findByUserName(userName)
                .map(loyaltyMapper::mapToDto).orElseThrow();
    }

    public LoyaltyDto findByUserCreate(String userName) {
        Loyalty loyalty = loyaltyRepository.findByUserName(userName)
                .map(Loyalty::incrementQuantity).orElseThrow();
        if (loyalty.getBookingQuantity() == 10) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findByStatus(DiscountStatus.SILVER)));
        } else if (loyalty.getBookingQuantity() == 20) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findByStatus(DiscountStatus.GOLD)));
        }
        return loyaltyMapper.mapToDto(loyalty);
    }

    public void findByUserDelete(String userName) {
        Loyalty loyalty = loyaltyRepository.findByUserName(userName)
                .map(Loyalty::decrementQuantity).orElseThrow();
        if (loyalty.getBookingQuantity() == 19) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findByStatus(DiscountStatus.SILVER)));
        } else if (loyalty.getBookingQuantity() == 9) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findByStatus(DiscountStatus.BRONZE)));
        }
    }
}
