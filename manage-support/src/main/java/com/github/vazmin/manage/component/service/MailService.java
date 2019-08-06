package com.github.vazmin.manage.component.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

//    @Autowired
//    private JavaMailSenderImpl mailSender;
//
//    @Autowired
//    private Properties emailProps;
//
//    @Autowired
//    private FreemarkEngine freemarkEngine;
//
//    @Autowired
//    private SystemNoticeLogService systemNoticeLogService;
//
//    @Async
//    public void sendEmail(SystemNoticeLog systemNoticeLog, String content, boolean isMultipart, boolean isHtml) {
////        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
////            isMultipart, isHtml, to, subject, content);
//
//        String to = systemNoticeLog.getUsername();
//        // Prepare message using a Spring helper
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
//            message.setTo(to);
//            message.setFrom(emailProps.getProperty("admin.email.username"));
//            message.setSubject(systemNoticeLog.getTitle());
//            message.setText(content, isHtml);
//            mailSender.send(mimeMessage);
//            systemNoticeLog.setResult(StatusEnum.VALID.getValue());
//            log.debug("Sent email to User '{}'", to);
//        } catch (Exception e) {
//            systemNoticeLog.setResult(StatusEnum.INVALID.getValue());
//            systemNoticeLog.setErrorMsg(e.getMessage());
//            if (log.isDebugEnabled()) {
//                log.warn("Email could not be sent to user '{}'", to, e);
//            } else {
//                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
//            }
//        }
//        try {
//            systemNoticeLogService.insert(systemNoticeLog);
//        } catch (ServiceProcessException e) {
//            log.error("发送邮件记录日志异常");
//            e.printStackTrace();
//        }
//    }
//
//    @Async
//    public void sendEmailFromTemplate(Map<String, Object> om, SystemNoticeLog systemNoticeLog,
//                                      String templateName, String titleKey) {
//        String content = null;
//        try {
//            content = freemarkEngine.mergeTemplateIntoString(templateName, om);
//        } catch (IOException | TemplateException e) {
//            e.printStackTrace();
//        }
//        String subject = emailProps.getProperty(titleKey);
//        systemNoticeLog.setTitle(subject);
//        sendEmail(systemNoticeLog, content, false, true);
//    }
//
//
//    @Async
//    public void sendPasswordResetMail(ManageUser user) {
//        log.debug("Sending password reset email to '{}'", user.getEmail());
//        String resetURL = String.format("%s?key=%s",
//                emailProps.getProperty("email.reset.url"), user.getResetKey());
//        Map<String, Object> om = new HashMap<>();
//        om.put("user", user);
//        om.put("resetURL", resetURL);
//        om.put("title", emailProps.getProperty("email.reset.title"));
//        SystemNoticeLog systemNoticeLog = new SystemNoticeLog();
//        systemNoticeLog.setNoticeTime(DateUtil.getTimestamp());
//        systemNoticeLog.setUserId(user.getId());
//        systemNoticeLog.setSendContent(resetURL);
//        systemNoticeLog.setUsername(user.getEmailOrUsername());
//        systemNoticeLog.setSendMode(SystemNoticeSendModelEnum.EMAIL.getValue());
//        systemNoticeLog.setNoticeType(SystemNoticeModelEnum.RESET_PASS.getValue());
//        sendEmailFromTemplate(om, systemNoticeLog,
//                "passwordResetEmail.ftl", "email.reset.title");
//    }
}
