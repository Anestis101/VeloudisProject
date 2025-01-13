package com.appointment.appointment_cloud.repository;

import com.appointment.appointment_cloud.model.Slots;
import com.appointment.appointment_cloud.model.SlotStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends MongoRepository<Slots, String> { // Changed JpaRepository to MongoRepository and Long to String
    List<Slots> findByStatus(SlotStatus status);
}
