package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "prescription_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointmentEntity;
    @Column(columnDefinition = "bigint[]")
    private Long[] medicineIds;

    @Transient
    private List<MedicineInventoryEntity> medicineInventoryEntities;


}
