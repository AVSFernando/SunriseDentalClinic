package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.repository.PatientRepository;
import com.sunrisedental.clinic.repository.DoctorRepository;
import com.sunrisedental.clinic.repository.AppointmentRepository;
import com.sunrisedental.clinic.repository.TreatmentRepository;
import com.sunrisedental.clinic.repository.PaymentRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final PaymentRepository paymentRepository;

    public DashboardController(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            TreatmentRepository treatmentRepository,
            PaymentRepository paymentRepository) {

        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            Model model,
            Authentication authentication) {

        String username = authentication.getName();

        // Logged-in username
        model.addAttribute("username", username);

        // Database counts
        model.addAttribute(
                "totalPatients",
                patientRepository.count()
        );

        model.addAttribute(
                "totalDoctors",
                doctorRepository.count()
        );

        model.addAttribute(
                "totalAppointments",
                appointmentRepository.count()
        );

        model.addAttribute(
                "totalTreatments",
                treatmentRepository.count()
        );

        // Total Revenue
        BigDecimal totalRevenue = paymentRepository
                .findAll()
                .stream()
                .filter(payment ->
                        "Paid".equalsIgnoreCase(payment.getStatus()))
                .map(payment -> payment.getAmount())
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute(
                "totalRevenue",
                totalRevenue
        );

        return "dashboard";
    }
}