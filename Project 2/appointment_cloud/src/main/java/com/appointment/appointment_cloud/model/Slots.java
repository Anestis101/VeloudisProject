package com.appointment.appointment_cloud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "slots") 
@Data
public class Slots {

    @Id
    private String id; 
    private LocalDate date;
    private LocalTime time;
    private String roomNumber;
    private String registrarName;

    private SlotStatus status; // No @Enumerated needed, as enums are stored as Strings in MongoDB
}
