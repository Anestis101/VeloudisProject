package com.booking.book.service;

import com.booking.book.model.Slot;
import com.booking.book.model.SlotStatus;
import com.booking.book.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public Slot getSlotById(Long id) {
        return slotRepository.findById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
    }

    public Slot createSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    public Slot updateSlot(Long id, Slot updatedSlot) {
        Slot slot = getSlotById(id);
        slot.setDate(updatedSlot.getDate());
        slot.setTime(updatedSlot.getTime());
        slot.setRoomNumber(updatedSlot.getRoomNumber());
        slot.setRegistrarName(updatedSlot.getRegistrarName());
        slot.setStatus(updatedSlot.getStatus());
        return slotRepository.save(slot);
    }

    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }

    public List<Slot> getAvailableSlots() {
        return slotRepository.findByStatus(SlotStatus.AVAILABLE);
    }

    public Slot bookSlot(Long id) {
        Slot slot = getSlotById(id);
        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new IllegalStateException("Slot already booked");
        }
        slot.setStatus(SlotStatus.BOOKED);
        return slotRepository.save(slot);
    }

    public Slot cancelSlot(Long id) {
        Slot slot = getSlotById(id);
        if (slot.getStatus() == SlotStatus.AVAILABLE) {
            throw new IllegalStateException("Slot is already available");
        }
        slot.setStatus(SlotStatus.AVAILABLE);
        return slotRepository.save(slot);
    }
}