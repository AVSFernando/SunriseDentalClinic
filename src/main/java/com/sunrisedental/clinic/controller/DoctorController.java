package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.Doctor;
import com.sunrisedental.clinic.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // View all doctors
    @GetMapping
    public String doctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    // Show add doctor form
    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    // Save doctor
    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    // Show edit doctor form
    @GetMapping("/edit/{id}")
    public String showEditDoctorForm(
            @PathVariable Long id,
            Model model) {

        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found: " + id));

        model.addAttribute("doctor", doctor);

        return "doctor-form";
    }

    // Delete doctor
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}