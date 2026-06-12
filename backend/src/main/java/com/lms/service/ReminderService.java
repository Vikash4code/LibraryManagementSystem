package com.lms.service;

import com.lms.entity.Loan;
import com.lms.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final LoanRepository loanRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String fromAddress;

    // runs every day at 9 AM and emails users whose books are due within 2 days
    @Scheduled(cron = "0 0 9 * * *")
    public void sendDueDateReminders() {
        if (fromAddress == null || fromAddress.isBlank()) {
            log.info("Mail is not configured, skipping due-date reminders");
            return;
        }

        LocalDate today = LocalDate.now();
        List<Loan> dueSoon = loanRepository.findByStatusAndDueDateBetween(
                Loan.Status.ISSUED, today, today.plusDays(2));

        for (Loan loan : dueSoon) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromAddress);
                message.setTo(loan.getUser().getEmail());
                message.setSubject("Library reminder: \"" + loan.getBook().getTitle() + "\" is due soon");
                message.setText("Hi " + loan.getUser().getName() + ",\n\n"
                        + "This is a reminder that the book \"" + loan.getBook().getTitle() + "\" "
                        + "is due on " + loan.getDueDate() + ".\n"
                        + "Please return it on time to avoid a fine of Rs 10 per day.\n\n"
                        + "Thank you,\nLibrary Team");
                mailSender.send(message);
            } catch (Exception e) {
                log.warn("Failed to send reminder to {}: {}", loan.getUser().getEmail(), e.getMessage());
            }
        }

        log.info("Due-date reminders processed for {} loans", dueSoon.size());
    }
}
