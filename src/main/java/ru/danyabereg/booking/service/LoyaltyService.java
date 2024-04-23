package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;

import java.net.ConnectException;

@RequiredArgsConstructor
@Retryable(
        retryFor = {ConnectException.class},
        maxAttemptsExpression = "${db.connection.retry.max_attempts}",
        backoff = @Backoff(delayExpression = "{db.connection.retry.backoff_ms}")
)
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
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
                .status(discountMapper
                        .mapToEntity(discountService
                                .findMinStatus()))
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
        if (loyalty.getStatus().getMaxQuantity() != null &&
                loyalty.getBookingQuantity() > loyalty.getStatus().getMaxQuantity()) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findNextStatus(loyalty.getStatus().getDiscount())));
        }
        return loyaltyMapper.mapToDto(loyalty);
    }

    public void findByUserDelete(String userName) {
        Loyalty loyalty = loyaltyRepository.findByUserName(userName)
                .map(Loyalty::decrementQuantity).orElseThrow();
        if (loyalty.getStatus().getMinQuantity() != 0 &&
                loyalty.getBookingQuantity() < loyalty.getStatus().getMinQuantity()) {
            loyalty.setStatus(discountMapper.mapToEntity(
                    discountService.findPreviousStatus(loyalty.getStatus().getDiscount())));
        }
    }
}
