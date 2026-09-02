package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.Treatment;
import com.sunrisedental.clinic.service.TreatmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    // View all treatments
    @GetMapping
    public String treatments(Model model) {

        model.addAttribute(
                "treatments",
                treatmentService.getAllTreatments()
        );

        return "treatments";
    }

    // Show add treatment form
    @GetMapping("/add")
    public String showAddTreatmentForm(Model model) {

        model.addAttribute("treatment", new Treatment());

        return "treatment-form";
    }

    // Save treatment
    @PostMapping("/save")
    public String saveTreatment(
            @ModelAttribute Treatment treatment) {

        treatmentService.saveTreatment(treatment);

        return "redirect:/treatments";
    }

    // Show edit treatment form
    @GetMapping("/edit/{id}")
    public String showEditTreatmentForm(
            @PathVariable Long id,
            Model model) {

        Treatment treatment = treatmentService
                .getTreatmentById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Treatment not found: " + id
                        ));

        model.addAttribute("treatment", treatment);

        return "treatment-form";
    }

    // Delete treatment
    @GetMapping("/delete/{id}")
    public String deleteTreatment(
            @PathVariable Long id) {

        treatmentService.deleteTreatment(id);

        return "redirect:/treatments";
    }
}