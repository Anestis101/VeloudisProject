package com.appointment.appointment_cloud.service;

import com.appointment.appointment_cloud.model.Slots;
import com.appointment.appointment_cloud.model.SlotStatus;
import com.appointment.appointment_cloud.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    // Retrieve all slots
    public List<Slots> getAllSlots() {
        return slotRepository.findAll();
    }

    // Retrieve a slot by ID
    public Slots getSlotById(String id) {
        return slotRepository.findById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
    }

    // Create a new slot
    public Slots createSlot(Slots slot) {
        return slotRepository.save(slot);
    }

    // Update an existing slot
    public Slots updateSlot(String id, Slots updatedSlot) { 
        Slots slot = getSlotById(id);
        slot.setDate(updatedSlot.getDate());
        slot.setTime(updatedSlot.getTime());
        slot.setRoomNumber(updatedSlot.getRoomNumber());
        slot.setRegistrarName(updatedSlot.getRegistrarName());
        slot.setStatus(updatedSlot.getStatus());
        return slotRepository.save(slot);
    }

    // Delete a slot
    public void deleteSlot(String id) { 
        slotRepository.deleteById(id);
    }

    // Retrieve all available slots
    public List<Slots> getAvailableSlots() {
        return slotRepository.findByStatus(SlotStatus.AVAILABLE);
    }

    // Schedule an appointment for a slot
    public Slots scheduleAppointment(String id) { 
        Slots slot = getSlotById(id);
        if (slot.getStatus() == SlotStatus.BOOKED) { 
            throw new IllegalStateException("Slot already has an appointment");
        }
        slot.setStatus(SlotStatus.BOOKED);
        return slotRepository.save(slot);
    }

    // Cancel an appointment for a slot
    public Slots cancelAppointment(String id) { 
        Slots slot = getSlotById(id);
        if (slot.getStatus() == SlotStatus.AVAILABLE) {
            throw new IllegalStateException("Slot is already available");
        }
        slot.setStatus(SlotStatus.AVAILABLE);
        return slotRepository.save(slot);
    }
}
