package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_table",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"doctor_id", "date_of_appointment", "slot_id"}),
                @UniqueConstraint(columnNames = {"patient_id", "date_of_appointment", "slot_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @Column(nullable = false)
    private String stage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id")
    private AppointmentSlotEntity slot;
    @Column(nullable = false)
    private LocalDate dateOfAppointment;
    @Column(nullable = false)
    private LocalDateTime dateOfBooking;

    public AppointmentEntity(Long appointmentId, PatientEntity patient, AppointmentSlotEntity slot, LocalDate dateOfAppointment, LocalDateTime dateOfBooking) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.slot = slot;
        this.dateOfAppointment = dateOfAppointment;
        this.dateOfBooking = dateOfBooking;
    }
}
