package com.healthCareAnalyzer.Health_Care_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phlebotomist_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhlebotomistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phlebotomistId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity userEntity;
    private String phoneNumber;
}
