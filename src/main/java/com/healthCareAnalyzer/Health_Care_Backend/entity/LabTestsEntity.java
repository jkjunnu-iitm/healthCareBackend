package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lab_tests_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabTestsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labTestId;
    @Column(nullable = false)
    private String labTestName;
    @Column(columnDefinition = "text[]")
    private String[] labTestFields;


}
