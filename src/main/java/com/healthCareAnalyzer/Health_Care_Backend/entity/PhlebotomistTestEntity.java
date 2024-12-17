package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "phlebotomist_test_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhlebotomistTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phlebotomistTestId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointmentEntity;
    @Column(columnDefinition = "bigint[]")
    private Long[] labTestIds;
    private String patientTestData;

    @Transient
    private List<LabTestsEntity> labTestsEntityList;
}
