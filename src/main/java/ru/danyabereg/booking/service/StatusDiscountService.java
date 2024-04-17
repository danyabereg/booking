package ru.danyabereg.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danyabereg.booking.mapper.StatusDiscountMapper;
import ru.danyabereg.booking.model.dto.StatusDiscountDto;
import ru.danyabereg.booking.model.entity.DiscountStatus;
import ru.danyabereg.booking.model.repository.StatusDiscountRepository;

import java.sql.SQLException;


@RequiredArgsConstructor
@Transactional
@Retryable(
        retryFor = {SQLException.class},
        maxAttemptsExpression = "${db.connection.retry.max_attempts}",
        backoff = @Backoff(delayExpression = "{db.connection.retry.backoff_ms}")
)
@Service
public class StatusDiscountService {
    private final StatusDiscountRepository repository;
    private final StatusDiscountMapper mapper;

    public Integer getDiscountByDiscountStatus(DiscountStatus discountStatus) {
        return repository.findByStatus(discountStatus).orElseThrow().getDiscount();
    }

    public StatusDiscountDto findByStatus(DiscountStatus discountStatus) {
        return repository.findByStatus(discountStatus).map(mapper::mapToDto).orElseThrow();
    }

}
