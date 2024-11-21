package com.healthCareAnalyzer.Health_Care_Backend.repository;

import com.healthCareAnalyzer.Health_Care_Backend.entity.AppointmentEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query(" SELECT a.slot.slotId FROM AppointmentEntity a WHERE a.doctor.doctorId=?1 and a.dateOfAppointment=?2 and (a.stage='approval' or a.stage='diagnose') ")
    List<Long> findOpenSlots(@NotNull Long doctorId, @NotNull LocalDate dateOfAppointment);

    @Query("SELECT a.stage FROM AppointmentEntity a WHERE a.patient.patientId=?1")
    List<String> findStageByPatientId(Long patientId);


    @Transactional
    @Modifying
    @Query("DELETE FROM AppointmentEntity a WHERE a.stage='approval' and a.dateOfAppointment<?1")
    void deleteUnapprovedAppointments(LocalDate localDate);

    @Query("SELECT new AppointmentEntity (a.appointmentId,a.dateOfAppointment,a.slot,a.dateOfBooking,a.patient.patientId,a.patient.userEntity.firstName,a.patient.userEntity.lastName) FROM AppointmentEntity a WHERE a.doctor.userEntity.username=?1 and a.stage='approval' ")
    List<AppointmentEntity> findAppointmentsWaitingForApproval(String username);
}
