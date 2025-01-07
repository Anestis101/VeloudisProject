package com.booking.book.repository;

import com.booking.book.model.Slot;
import com.booking.book.model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByStatus(SlotStatus status);
}
