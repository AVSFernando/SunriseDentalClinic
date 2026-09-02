package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.Patient;
import com.sunrisedental.clinic.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // View all patients
    @GetMapping
    public String patients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // Show add patient form
    @GetMapping("/add")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form";
    }

    // Save patient
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    // Delete patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
    // Show edit patient form
    @GetMapping("/edit/{id}")
    public String showEditPatientForm(
            @PathVariable Long id,
            Model model) {

        Patient patient = patientService.getPatientById(id);

        model.addAttribute("patient", patient);

        return "patient-form";
    }
}