//package com.example.emailsendler.configure;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Properties;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class MailConfigure extends WebSecurityConfigurerAdapter {
//
//
//
//
//
//    @Bean
//    public JavaMailSender getJavaMailSendler(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost("smtp.gmail.com");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("orinboyevmuhammadyusuf800@gmail.com");
//        javaMailSender.setPassword("wtygbybautzgahov");
//        Properties javaMailProperties =
//                javaMailSender.getJavaMailProperties();
//        javaMailProperties.put("mail.transport.protocol","smtp");
//        javaMailProperties.put("mail.smtp.auth","true");
//        javaMailProperties.put("mail.smtp.starttls.enable","true");
//        javaMailProperties.put("mail.debug","true");
//        return javaMailSender;
//    }
//
//
//
//}
