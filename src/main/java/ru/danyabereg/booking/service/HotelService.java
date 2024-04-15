package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.HotelMapper;
import ru.danyabereg.booking.model.dto.HotelDto;
import ru.danyabereg.booking.model.repository.HotelRepository;

import java.net.ConnectException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
@Retryable(
        retryFor = { ConnectException.class },
        maxAttempts = 4,
        backoff = @Backoff(delay = 800)
)
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Optional<HotelDto> findById(UUID id) {
        return hotelRepository.findById(id)
                .map(hotelMapper::mapToDto);
    }

}
