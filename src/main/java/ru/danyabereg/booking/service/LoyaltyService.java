package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.LoyaltyStatus;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;

import java.net.ConnectException;
import java.util.Optional;

@RequiredArgsConstructor
@Retryable(
        retryFor = { ConnectException.class },
        maxAttempts = 4,
        backoff = @Backoff(delay = 800)
)
@Service
public class LoyaltyService {
    private final LoyaltyMapper loyaltyMapper;
    private final LoyaltyRepository loyaltyRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public LoyaltyDto createUser(String userName) {
        Loyalty loyalty = loyaltyRepository.save(Loyalty.builder()
                .userName(userName)
                .bookingQuantity(1)
                .status(LoyaltyStatus.BRONZE)
                .build());
        return loyaltyMapper.mapToDto(loyalty);
    }

    @Transactional(readOnly = true)
    public Optional<LoyaltyDto> findByUserName(String userName) {
        return loyaltyRepository.findByUserName(userName)
                .map(loyaltyMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public Optional<LoyaltyDto> findByUserCreate(String userName) {
        return loyaltyRepository.findByUserName(userName)
                .map(Loyalty::incrementQuantity)
                .map(loyaltyMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public void findByUserDelete(String userName) {
        loyaltyRepository.findByUserName(userName)
                .map(Loyalty::decrementQuantity)
                .map(loyaltyMapper::mapToDto);
    }
}
