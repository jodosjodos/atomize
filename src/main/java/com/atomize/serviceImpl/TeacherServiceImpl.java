package com.atomize.serviceImpl;

import com.atomize.dtos.Role;
import com.atomize.dtos.TeacherSignUpDto;
import com.atomize.entity.Dos;
import com.atomize.entity.Teacher;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.TeacherRepository;
import com.atomize.services.EmailService;
import com.atomize.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository repository;
    private final EmailService emailService;

    @Override
    public Teacher createTeacher(TeacherSignUpDto signUpDto) {
        Teacher teacher = Teacher.builder()
                .fullName(signUpDto.getFullName())
                .phoneNumber(signUpDto.getPhoneNumber())
                .degree(signUpDto.getDegree())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .role(Role.TEACHER)
                .dateOfBirth(signUpDto.getDateOfBirth())

                .build();

        boolean teacherAlreadyExists = repository.findByEmail(teacher.getEmail()).isPresent();
        if (teacherAlreadyExists) {
            throw new ApiRequestException(" email already exists", HttpStatus.BAD_REQUEST);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        // reaching out
        Dos loggedDos = (Dos) authentication.getPrincipal();
        System.out.println(loggedDos.getName());
        ;

        String subject = "Welcome to atomize best school management system! 🚀";
        String text = "Dear teacher " + signUpDto.getFullName() + "\n\n" +
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

        emailService.sendEmailToDos(signUpDto.getEmail(), subject, text);
        return repository.save(teacher);
        // return teacher;
    }

}
