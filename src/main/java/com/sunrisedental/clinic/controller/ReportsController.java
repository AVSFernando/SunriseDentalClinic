package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.repository.PatientRepository;
import com.sunrisedental.clinic.repository.DoctorRepository;
import com.sunrisedental.clinic.repository.AppointmentRepository;
import com.sunrisedental.clinic.repository.TreatmentRepository;
import com.sunrisedental.clinic.repository.PaymentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class ReportsController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final PaymentRepository paymentRepository;

    public ReportsController(
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

    @GetMapping("/reports")
    public String reports(Model model) {

        // ===============================
        // DATABASE COUNTS
        // ===============================

        long totalPatients = patientRepository.count();

        long totalDoctors = doctorRepository.count();

        long totalAppointments = appointmentRepository.count();

        long totalTreatments = treatmentRepository.count();


        // ===============================
        // TOTAL PAID REVENUE
        // ===============================

        BigDecimal totalRevenue = paymentRepository
                .findAll()
                .stream()
                .filter(payment ->
                        "Paid".equalsIgnoreCase(payment.getStatus()))
                .map(payment -> payment.getAmount())
                .filter(amount -> amount != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // ===============================
        // SEND DATA TO reports.html
        // ===============================

        model.addAttribute(
                "totalPatients",
                totalPatients
        );

        model.addAttribute(
                "totalDoctors",
                totalDoctors
        );

        model.addAttribute(
                "totalAppointments",
                totalAppointments
        );

        model.addAttribute(
                "totalTreatments",
                totalTreatments
        );

        model.addAttribute(
                "totalRevenue",
                totalRevenue
        );


        return "reports";
    }
}