package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.repository.StatusDiscountRepository;

import java.net.ConnectException;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Retryable(
        retryFor = {ConnectException.class},
        maxAttemptsExpression = "${db.connection.retry.max_attempts}",
        backoff = @Backoff(delayExpression = "{db.connection.retry.backoff_ms}")
)
@Service
public class StatusDiscountService {
    private final StatusDiscountRepository repository;
    private final StatusDiscountMapper mapper;

    public Integer getDiscountByDiscountStatus(String discountStatus) {
        return repository.findByDiscountStatus(discountStatus)
                .orElseThrow()
                .getDiscount();
    }

    public StatusDiscountDto findByStatus(String discountStatus) {
        return repository.findByDiscountStatus(discountStatus)
                .map(mapper::mapToDto)
                .orElseThrow();
    }

    public StatusDiscountDto findMinStatus() {
        return repository.findMinDiscountStatus()
                .map(mapper::mapToDto)
                .orElseThrow();
    }

    public StatusDiscountDto findNextStatus(Integer discount) {
        return repository.findNextStatus(discount)
                .map(mapper::mapToDto)
                .orElseThrow();
    }

    public StatusDiscountDto findPreviousStatus(Integer discount) {
        return repository.findPreviousStatus(discount)
                .map(mapper::mapToDto)
                .orElseThrow();
    }

}
