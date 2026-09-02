package com.sunrisedental.clinic.controller;

import com.sunrisedental.clinic.entity.Payment;
import com.sunrisedental.clinic.service.AppointmentService;
import com.sunrisedental.clinic.service.PatientService;
import com.sunrisedental.clinic.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PaymentController(
            PaymentService paymentService,
            PatientService patientService,
            AppointmentService appointmentService) {

        this.paymentService = paymentService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    // View all payments
    @GetMapping
    public String payments(Model model) {

        model.addAttribute(
                "payments",
                paymentService.getAllPayments()
        );

        return "payments";
    }

    // Show add payment form
    @GetMapping("/add")
    public String showAddPaymentForm(Model model) {

        model.addAttribute("payment", new Payment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments",
                appointmentService.getAllAppointments());

        return "payment-form";
    }

    // Save payment
    @PostMapping("/save")
    public String savePayment(
            @ModelAttribute Payment payment) {

        paymentService.savePayment(payment);

        return "redirect:/payments";
    }

    // Show edit payment form
    @GetMapping("/edit/{id}")
    public String showEditPaymentForm(
            @PathVariable Long id,
            Model model) {

        Payment payment = paymentService
                .getPaymentById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found: " + id
                        ));

        model.addAttribute("payment", payment);
        model.addAttribute("patients",
                patientService.getAllPatients());
        model.addAttribute("appointments",
                appointmentService.getAllAppointments());

        return "payment-form";
    }

    // Delete payment
    @GetMapping("/delete/{id}")
    public String deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return "redirect:/payments";
    }
    // View printable bill
    @GetMapping("/bill/{id}")
    public String viewBill(
            @PathVariable Long id,
            Model model) {

        Payment payment = paymentService
                .getPaymentById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found: " + id
                        ));

        model.addAttribute("payment", payment);

        return "bill";
    }
}