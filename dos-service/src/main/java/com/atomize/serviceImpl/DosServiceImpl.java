package com.atomize.serviceImpl;

import com.atomize.dtos.Role;
import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.DosRepository;
import com.atomize.services.DOSService;
import com.atomize.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DosServiceImpl implements DOSService {
    private final DosRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailServive;

    @Override
    public Dos createDos(SignUpRequest signUpRequest) {
        Dos dos = Dos.builder()
                .name(signUpRequest.name())
                .phoneNumber(signUpRequest.phoneNumber())
                .email(signUpRequest.email())
                .schoolName(signUpRequest.schoolName())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(Role.ADMIN)
                .build();

        boolean dosAlreadyExists = repository.findByEmail(dos.getEmail()).isPresent();
        if (dosAlreadyExists) {
            throw new ApiRequestException(" email already exists", HttpStatus.BAD_REQUEST);
        }
        String subject = "Welcome to atomize best school management system! 🚀";
        String text = "Dear " + signUpRequest.name() + "\n\n" +
                "🌟 Congratulations on joining  atomize community! We are delighted to have you on board, and we can't wait to see   your school  starting using our system\n\n" +
                "🚀 Your journey starts now. Explore the limitless potential of atomize by visiting its official online website ( will  be provided later).\n\n" +
                "🔧 Got questions or need assistance? Our dedicated team is here to help. Don't hesitate to reach out!\n\n" +
                "🌐 Stay connected with us:\n" +
                "LinkedIn: [Follow us on LinkedIn](https://www.linkedin.com/in/jean-de-dieu-nshimyumukiza-97b315259/)\n" +
                "GitHub: [Check out our repositories on GitHub](https://github.com/jodosjodos)\n" +
                "👉 Let's make your work easily and digitize your school!\n\n" +
                "Best regards,\n" +
                "Jodos Company Group , here credentials you have created for your account " + signUpRequest;

        emailServive.sendEmailToDos(signUpRequest.email(), subject, text);
        return repository.save(dos);
    }
}
