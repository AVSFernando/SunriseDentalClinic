package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.Appointment;
import com.sunrisedental.clinic.service.AppointmentService;
import com.sunrisedental.clinic.service.DoctorService;
import com.sunrisedental.clinic.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentController(
            AppointmentService appointmentService,
            PatientService patientService,
            DoctorService doctorService) {

        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    // View all appointments
    @GetMapping
    public String appointments(
            @RequestParam(required = false) String search,
            Model model) {

        var appointments = appointmentService.getAllAppointments();

        if (search != null && !search.trim().isEmpty()) {

            String searchValue = search.trim().toLowerCase();

            appointments = appointments.stream()
                    .filter(appointment ->
                            appointment.getAppointmentNumber() != null
                                    && appointment.getAppointmentNumber()
                                    .toLowerCase()
                                    .contains(searchValue)
                    )
                    .toList();
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("search", search);

        return "appointments";
    }

    // Show add appointment form
    @GetMapping("/add")
    public String showAddAppointmentForm(Model model) {

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "appointment-form";
    }

    // Save appointment
    @PostMapping("/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment) {

        // New appointment
        if (appointment.getId() == null) {

            String appointmentNumber =
                    "APT-" + System.currentTimeMillis();

            appointment.setAppointmentNumber(appointmentNumber);

        } else {

            // Edit existing appointment
            Appointment existingAppointment =
                    appointmentService
                            .getAppointmentById(appointment.getId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Appointment not found: "
                                                    + appointment.getId()
                                    ));

            // Keep the existing appointment number
            appointment.setAppointmentNumber(
                    existingAppointment.getAppointmentNumber()
            );
        }

        appointmentService.saveAppointment(appointment);

        return "redirect:/appointments";
    }

    // Show edit appointment form
    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(
            @PathVariable Long id,
            Model model) {

        Appointment appointment = appointmentService
                .getAppointmentById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found: " + id
                        ));

        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "appointment-form";
    }

    // Delete appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return "redirect:/appointments";
    }
}