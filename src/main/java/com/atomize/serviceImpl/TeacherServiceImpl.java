package com.atomize.serviceImpl;

import com.atomize.dtos.Role;
import com.atomize.dtos.TeacherSignUpDto;
import com.atomize.entity.Teacher;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.TeacherRepository;
import com.atomize.services.EmailService;
import com.atomize.services.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository repository;
    private final EmailService emailService;

    @Override
    public Teacher createTeacher(TeacherSignUpDto signUpDto) {
        Teacher teacher = Teacher.builder()
                .fullName(signUpDto.fullName())
                .phoneNumber(signUpDto.phoneNumber())
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
//        reaching out
        System.out.println(authentication);

        String subject = "Welcome to atomize best school management system! 🚀";
        String text = "Dear teacher " + signUpDto.fullName() + "\n\n" +
                "🌟 Congratulations on joining  atomize community! We are delighted to have you on board, and we can't wait to see   your school  starting using our system\n\n" +
                "🚀 Your journey starts now. Explore the limitless potential of atomize by visiting its official online website ( will  be provided later).\n\n" +
                "🔧 Got questions or need assistance? Our dedicated team is here to help. Don't hesitate to reach out!\n\n" +
                "🌐 Stay connected with us:\n" +
                "LinkedIn: [Follow us on LinkedIn](https://www.linkedin.com/in/jean-de-dieu-nshimyumukiza-97b315259/)\n" +
                "GitHub: [Check out our repositories on GitHub](https://github.com/jodosjodos)\n" +
                "👉 Let's make your work easily and digitize your school!\n\n" +
                "Best regards,\n" +
                "Jodos Company Group , here credentials you have created for your account " + signUpDto + " you have created by dos : "+"mee";


//        emailService.sendEmailToDos(signUpDto.email(), subject, text);
//        return repository.save(teacher);
//        not saving
//        return draft
        return teacher;
    }


}
