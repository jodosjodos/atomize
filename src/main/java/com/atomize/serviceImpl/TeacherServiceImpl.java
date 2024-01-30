package com.atomize.serviceImpl;

import com.atomize.dto.LoginResponse;
import com.atomize.dto.Role;
import com.atomize.dto.TeacherLoginDto;
import com.atomize.dto.TeacherSignUpDto;
import com.atomize.entity.Dos;
import com.atomize.entity.Teacher;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.DosRepository;
import com.atomize.repository.TeacherRepository;
import com.atomize.services.EmailService;
import com.atomize.services.TeacherService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository repository;
    private final EmailService emailService;
    private final DosRepository dosRepository;

    // sign up teacher
    @Override
    @Transactional
    public Teacher createTeacher(TeacherSignUpDto signUpDto) {
        Teacher teacher = Teacher.builder()
                .fullName(signUpDto.fullName())
                .phoneNumber(signUpDto.phoneNumber())
                .degree(signUpDto.degree())
                .email(signUpDto.email())
                .password(passwordEncoder.encode(signUpDto.password()))
                .role(Role.TEACHER)
                .dateOfBirth(signUpDto.dateOfBirth())

                .build();

        boolean teacherAlreadyExists = repository.findByEmail(teacher.getEmail()).isPresent();
        if (teacherAlreadyExists) {
            throw new ApiRequestException(" email already exists", HttpStatus.BAD_REQUEST);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        // reaching out
        Dos loggedDos = (Dos) authentication.getPrincipal();
        // teacher.setCreatorDos(loggedDos);

        teacher.setCreatorDos(loggedDos);
        List<Teacher> teachers = loggedDos.getTeachers();
        teachers.add(teacher);
        loggedDos.setTeachers(teachers);

        String subject = "Welcome to atomize best school management system! 🚀";
        String text = "Dear teacher " + signUpDto.fullName() + "\n\n" +
                "🌟 Congratulations on joining  atomize community! We are delighted to have you on board, and we can't wait to see   your school  starting using our system\n\n"
                +
                "🚀 Your journey starts now. Explore the limitless potential of atomize by visiting its official online website ( will  be provided later).\n\n"
                +
                "🔧 Got questions or need assistance? Our dedicated team is here to help. Don't hesitate to reach out!\n\n"
                +
                "🌐 Stay connected with us:\n" +
                "LinkedIn: [Follow us on LinkedIn](https://www.linkedin.com/in/jean-de-dieu-nshimyumukiza-97b315259/)\n"
                +
                "GitHub: [Check out our repositories on GitHub](https://github.com/jodosjodos)\n" +
                "👉 Let's make your work easily and digitize your school!\n\n" +
                "Best regards,\n" +
                "Jodos Company Group , here credentials you have created for your account " + signUpDto
                + " you have created by dos : " + loggedDos.getName();

        log.info("send email");
        emailService.sendEmailToDos(signUpDto.email(), subject, text);

        log.info("save dos");
        dosRepository.save(loggedDos);
        log.info("save teacher");
        return repository.save(teacher);
        // return teacher;
    }

    // login teacher
    @Override
    public LoginResponse<Teacher> login(@Valid TeacherLoginDto loginDto) {
        try {

            Teacher user = repository.findByEmail(loginDto.email())
                    .orElseThrow(() -> new ApiRequestException("Invalid email", HttpStatus.BAD_REQUEST));

            if (passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                return new LoginResponse<Teacher>(user, null);
            }

            throw new ApiRequestException("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (ApiRequestException e) {
            throw new ApiRequestException("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ApiRequestException("something went wrong please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
