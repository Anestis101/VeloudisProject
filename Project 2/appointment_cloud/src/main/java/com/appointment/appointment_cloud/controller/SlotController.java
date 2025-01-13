package com.appointment.appointment_cloud.controller;

import com.appointment.appointment_cloud.model.Slots;
import com.appointment.appointment_cloud.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    // Create a new slot
    @PostMapping
    public Slots createSlot(@RequestBody Slots slot) {
        return slotService.createSlot(slot);
    }

    // Retrieve all slots
    @GetMapping
    public List<Slots> getAllSlots() {
        return slotService.getAllSlots();
    }

    // Retrieve a slot by ID
    @GetMapping("/{id}")
    public Slots getSlotById(@PathVariable String id) {
        return slotService.getSlotById(id);
    }

    // Update an existing slot by ID
    @PutMapping("/{id}")
    public Slots updateSlot(@PathVariable String id, @RequestBody Slots slot) {
        return slotService.updateSlot(id, slot);
    }

    // Delete a slot by ID
    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable String id) {
        slotService.deleteSlot(id);
    }

    // Retrieve all available slots
    @GetMapping("/available")
    public List<Slots> getAvailableSlots() {
        return slotService.getAvailableSlots();
    }

    // Schedule an appointment for a slot by ID
    @PostMapping("/{id}/appointment")
    public Slots scheduleAppointment(@PathVariable String id) {
        return slotService.scheduleAppointment(id);
    }

    // Cancel an appointment for a slot by ID
    @PostMapping("/{id}/cancel")
    public Slots cancelAppointment(@PathVariable String id) {
        return slotService.cancelAppointment(id);
    }
}
