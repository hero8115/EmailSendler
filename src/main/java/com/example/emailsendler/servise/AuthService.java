package com.example.emailsendler.servise;

import com.example.emailsendler.EnumRole;
import com.example.emailsendler.entity.Addition;
import com.example.emailsendler.entity.User;
import com.example.emailsendler.generation.JWTValidation;
import com.example.emailsendler.payload.ApiResponse;
import com.example.emailsendler.payload.LoginDto;
import com.example.emailsendler.payload.UserDto;
import com.example.emailsendler.repository.AdditionRepository;
import com.example.emailsendler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {


    @Autowired
    AdditionRepository additionRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JWTValidation jwtValidation;
    public ApiResponse saveUser(UserDto userDto){
        User user = new User();
        if (userRepository.existsByEmail(userDto.getEmail())){
            return new ApiResponse("Ushbu email Avvaldan mavjuda.",false);
        }
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setEmailCode(String.valueOf(UUID.randomUUID()));
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Ro'yxatdan O'tdingiz. Accountni tasdiqlang",true);
    }

    public boolean sendEmail(String sendingEmail,String emailCode){
        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("orinboyevmuhammadyusuf800@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("Tasdiqlash");
            simpleMailMessage.setText("<a href = 'http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail+"'>Tasdiqlash<a/>");
            System.out.println(simpleMailMessage.getText().toString());
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public ApiResponse verifyEmail(String sendingEmail, String emailCode){
//        String emailCode = link.substring(10,46);
//        String sendingEmail = link.substring(53);
        Optional<Addition> byEmail = additionRepository.findByEmail(sendingEmail);
        Optional<User> byEmailAndEmailCode = userRepository.findByEmailAndEmailCode(sendingEmail, emailCode);
        Optional<User> byEmail1 = userRepository.findByEmail(sendingEmail);
        if (byEmail.isPresent()){
            User user = byEmail1.get();
            user.setUsername(byEmail.get().getUsername());
            user.setPassword(byEmail.get().getPassword());
            user.setEmailCode(null);
            userRepository.save(user);
            additionRepository.deleteById(byEmail.get().getId());
            return new ApiResponse("Malumotlar o'zgartirildi", true);
        }
        if (byEmailAndEmailCode.isPresent()) {
            User user = byEmailAndEmailCode.get();
            user.setEmailCode(null);
            user.setActive(true);
            userRepository.save(user);
            return new ApiResponse("Tasdiqlandi", true);
        }
        return new ApiResponse("Ushbu Akkount avvaldan tasdiqlangan",false);

    }

    public ApiResponse update(UUID id, UserDto userDto) {
        Optional<User> byEmail = userRepository.findById(id);
        if (userRepository.existsByUsername(userDto.getUsername())) return new ApiResponse("Username band",false);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            String code = String.valueOf(UUID.randomUUID());
            user.setEmailCode(code);
            sendEmail(user.getEmail(), code);
            Addition addition = new Addition();
            addition.setId(id);
            addition.setUsername(userDto.getUsername());
            addition.setPassword(userDto.getPassword());
            addition.setEmail(user.getEmail());
            additionRepository.save(addition);
        }
        return new ApiResponse("Kodni tasdiqlang",true);
    }


    public List<User> userList() {
        return userRepository.findAll();
    }

    public ApiResponse delete(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            userRepository.deleteByEmail(email);
            return new ApiResponse("Foydalanuvchi o'chirildi", true);
        }
        return new ApiResponse("Foydalanuvchi topilmadi",false);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));

            String token = jwtValidation.generateToken(loginDto.getUsername());
            return new ApiResponse("Siz tizimga kirdingiz",true,token);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Login Yoki Parol xato",false);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return user;
        }
        return null;

    }

    public ResponseEntity<String> addManager(UUID id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            User user = byId.get();
            user.setRolename("MANAGER");
            userRepository.save(user);
            return new ResponseEntity<>(user.getUsername()+ " MANAGER qilib tayinlandi", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User topilmadi", HttpStatus.NOT_FOUND);
    }
}