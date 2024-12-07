package com.healthCareAnalyzer.Health_Care_Backend.service.stage;

import com.healthCareAnalyzer.Health_Care_Backend.entity.StageEntity;
import com.healthCareAnalyzer.Health_Care_Backend.repository.StageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public ResponseEntity<?> addNewStages(List<String> stageList) {

        stageRepository.deleteAll();
        for (String stage : stageList) {
            StageEntity stageEntity = new StageEntity();
            stageEntity.setStageName(stage);
            stageRepository.save(stageEntity);
        }
        return ResponseEntity.ok().body("Created Stages Successfully");
    }
}
