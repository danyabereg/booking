package ru.danyabereg.booking.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danyabereg.booking.model.entity.Hotel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    Optional<Hotel> findByName(String name);
}
