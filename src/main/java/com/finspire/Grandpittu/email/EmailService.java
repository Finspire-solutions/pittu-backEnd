package com.finspire.Grandpittu.email;

import com.finspire.Grandpittu.entity.ReserveTable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

//to send acctivation code
    @Async
    public void sendEmail(String to,
                          String username,
                          EmailTemplateName emailTemplate,
                          String confirmationUrl,
                          String activationCode,
                          String subject
                          ) throws MessagingException {
        String templateName;
        if (emailTemplate == null){
            templateName = "confirm-email";
        }else{
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String ,Object> properties =new HashMap<>();
        properties.put("username",username);
        properties.put("confirmationUrl",confirmationUrl);
        properties.put("activation_code",activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("finspire001@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String template = templateEngine.process(templateName,context);
        helper.setText(template,true);
        mailSender.send(mimeMessage);
    }

    //send email after successfully booked
    @Async
    public void sendBookingConfirmationEmail(ReserveTable reserveTable,
                                             EmailTemplateName emailTemplate,
                                             String subject
                          ) throws MessagingException {
        String templateName;
        if (emailTemplate == null){
            templateName = "confirm-email";
        }else{
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        FileSystemResource logo = new FileSystemResource(new File("src/main/resources/images/logo.png"));
        helper.addInline("logoImage", logo);

        Map<String ,Object> properties =new HashMap<>();
        properties.put("customerName",reserveTable.getUsername());
        properties.put("bookingId",reserveTable.getBookedId());
        properties.put("checkInDate",reserveTable.getDateAndTime());
        properties.put("guestCount",reserveTable.getGuestNo());

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("info@grandpittu.lk");
        helper.setTo(reserveTable.getEmail());
        helper.setSubject(subject);
        String template = templateEngine.process(templateName,context);
        helper.setText(template,true);
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendBookingRelatedEmail(ReserveTable reserveTable,EmailContentDto emailContentDto,
                                        EmailTemplateName emailTemplate
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null){
            templateName = "confirm-email";
        }else{
            templateName = emailTemplate.getName();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        FileSystemResource logo = new FileSystemResource(new File("src/main/resources/images/logo.png"));
        helper.addInline("logoImage", logo);

        Map<String ,Object> properties =new HashMap<>();
        properties.put("customerName",reserveTable.getUsername());
        properties.put("reservedDate",reserveTable.getDateAndTime());
        properties.put("noOfPeople",reserveTable.getGuestNo());
        properties.put("reason",emailContentDto.getReason());
        properties.put("status",emailContentDto.getStatus());
        properties.put("bookingId",reserveTable.getBookedId());

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("info@grandpittu.lk");
        helper.setTo(reserveTable.getEmail());
        helper.setSubject(emailContentDto.getSubject());
        String template = templateEngine.process(templateName,context);
        helper.setText(template,true);
        mailSender.send(mimeMessage);
    }


}
