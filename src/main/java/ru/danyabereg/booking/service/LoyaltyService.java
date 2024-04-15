package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.LoyaltyMapper;
import ru.danyabereg.booking.model.dto.LoyaltyDto;
import ru.danyabereg.booking.model.entity.Loyalty;
import ru.danyabereg.booking.model.entity.LoyaltyStatus;
import ru.danyabereg.booking.model.repository.LoyaltyRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class LoyaltyService {
    private final LoyaltyMapper loyaltyMapper;
    private final LoyaltyRepository loyaltyRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public LoyaltyDto createUser(String userName) {
        Loyalty loyalty = loyaltyRepository.save(Loyalty.builder()
                        .userName(userName)
                        .bookingQuantity(0)
                        .status(LoyaltyStatus.BRONZE)
                .build());
        return loyaltyMapper.mapToDto(loyalty);
    }

    public Optional<LoyaltyDto> findByUserName(String userName) {
        return loyaltyRepository.findByUserName(userName)
                .map(loyaltyMapper::mapToDto);
    }
}
