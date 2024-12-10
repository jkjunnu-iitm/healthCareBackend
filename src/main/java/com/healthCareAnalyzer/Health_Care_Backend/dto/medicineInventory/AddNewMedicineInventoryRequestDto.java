package com.healthCareAnalyzer.Health_Care_Backend.dto.medicineInventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewMedicineInventoryRequestDto {

    @NotBlank
    public String medicineName;
    @NotEmpty
    private Integer medicineQuantity;
    @NotBlank
    private String medicineSerialNumber;
}
