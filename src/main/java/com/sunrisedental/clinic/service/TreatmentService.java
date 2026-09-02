package com.sunrisedental.clinic.service;

import com.sunrisedental.clinic.entity.Treatment;
import com.sunrisedental.clinic.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public Optional<Treatment> getTreatmentById(Long id) {
        return treatmentRepository.findById(id);
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }
}