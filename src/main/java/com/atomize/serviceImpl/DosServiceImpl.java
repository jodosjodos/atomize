package com.atomize.serviceImpl;

import com.atomize.dto.LoginResponse;
import com.atomize.dto.Role;
import com.atomize.dto.SignInRequest;
import com.atomize.dto.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.entity.Teacher;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.DosRepository;
import com.atomize.repository.TeacherRepository;
import com.atomize.services.DOSService;
import com.atomize.services.EmailService;
import com.atomize.services.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// dos service implementation
@Service
@RequiredArgsConstructor
public class DosServiceImpl implements DOSService {
    private final DosRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Override
    @Transactional
    public Dos createDos(SignUpRequest signUpRequest) {
        Dos dos = Dos.builder()
                .name(signUpRequest.name())
                .phoneNumber(signUpRequest.phoneNumber())
                .email(signUpRequest.email())
                .schoolName(signUpRequest.schoolName())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(Role.DOS)
                .build();

        List<Teacher> teachers = new ArrayList<Teacher>();

        dos.setTeachers(teachers);

        boolean dosAlreadyExists = repository.findByEmail(dos.getEmail()).isPresent();
        boolean dosSchoolAlreadyExists = repository.findBySchoolName(dos.getSchoolName()).isPresent();
        if (dosAlreadyExists) {
            throw new ApiRequestException(" email already exists", HttpStatus.BAD_REQUEST);
        }
        if (dosSchoolAlreadyExists) {
            throw new ApiRequestException(" school dos have been already registered", HttpStatus.BAD_REQUEST);

        }
        String subject = "Welcome to atomize best school management system! 🚀";
        String text = "Dear " + signUpRequest.name() + "\n\n" +
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
                "Jodos Company Group , here credentials you have created for your account " + signUpRequest;

        emailService.sendEmailToDos(signUpRequest.email(), subject, text);
        return repository.save(dos);
    }

    @Override
    public List<Dos> getAllDos() {
        List<Dos> dos = repository.findAll();
        if (dos.size() < 1)
            throw new ApiRequestException(" dos is empty", HttpStatus.NO_CONTENT);
        return dos;
    }

    @Override
    public Dos deleteDos(String dosEmail) {
        Dos dos = repository.findByEmail(dosEmail).orElseThrow(() -> {
            throw new ApiRequestException("email not found", HttpStatus.NOT_FOUND);
        });
        repository.deleteByEmail(dosEmail);
        // send email when they delete account
        return dos;
    }

    @Override
    public LoginResponse<Dos> loginDos(SignInRequest signInRequest) {
        try {

            Dos user = repository.findByEmail(signInRequest.email())
                    .orElseThrow(() -> new ApiRequestException("Invalid email", HttpStatus.BAD_REQUEST));

            if (passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
                String token = jwtService.generateToken(user);
                return new LoginResponse<Dos>(user, token);
            }

            throw new ApiRequestException("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (ApiRequestException e) {
            throw new ApiRequestException("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ApiRequestException("something went wrong please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<Teacher> getAllTeachers() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        // reaching out
        Dos loggedDos = (Dos) authentication.getPrincipal();
        // ArrayList<Teacher> teachers =
        // teacherRepository.findByDos_Id(loggedDos.getId());
        ArrayList<Teacher> teachers = new ArrayList<>();
        if (teachers.size() == 0) {
            throw new ApiRequestException("this dos haven't created any teacher", HttpStatus.NOT_FOUND);
        }
        return teachers;

    }
}
