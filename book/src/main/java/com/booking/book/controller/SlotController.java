package com.booking.book.controller;

import com.booking.book.model.Slot;
import com.booking.book.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @GetMapping
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @GetMapping("/{id}")
    public Slot getSlotById(@PathVariable Long id) {
        return slotService.getSlotById(id);
    }

    @PostMapping
    public Slot createSlot(@RequestBody Slot slot) {
        return slotService.createSlot(slot);
    }

    @PutMapping("/{id}")
    public Slot updateSlot(@PathVariable Long id, @RequestBody Slot slot) {
        return slotService.updateSlot(id, slot);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
    }

    @GetMapping("/available")
    public List<Slot> getAvailableSlots() {
        return slotService.getAvailableSlots();
    }

    @PostMapping("/{id}/book")
    public Slot bookSlot(@PathVariable Long id) {
        return slotService.bookSlot(id);
    }
    
    @PostMapping("/{id}/cancel")
    public Slot cancelSlot(@PathVariable Long id) {
        return slotService.cancelSlot(id);
    }
}
    