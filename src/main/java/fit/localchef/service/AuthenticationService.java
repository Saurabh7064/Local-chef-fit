package fit.localchef.service;


import fit.localchef.models.Chef;
import fit.localchef.models.Customer;
import fit.localchef.models.Role;
import fit.localchef.models.User;
import fit.localchef.models.requests.AuthenticationRequest;
import fit.localchef.models.requests.RegisterRequest;
import fit.localchef.models.responses.AuthenticationResponse;
import fit.localchef.repository.ChefRepository;
import fit.localchef.repository.CustomerRepository;
import fit.localchef.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final ChefRepository chefRepository;
    private final JavaMailSender mailSender; // Add this for email sending


    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {

            return AuthenticationResponse.builder()
                    .message("User with email " + request.getEmail() + " already exists.")
                    .build();
        }


        // Create the base User object
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()) // Accept role from request
                .build();

        // Save user into the User table
        repository.save(user);

        // Handle role-specific logic
        if (request.getRole() == Role.CUSTOMER) {
            // Create and save a Customer entry
            var customer = Customer.builder()
                    .user(user)
                    .loyaltyPoints(0) // Default value
                    .defaultDeliveryInstructions("")
                    .build();
            customerRepository.save(customer);
        } else if (request.getRole() == Role.CHEF) {
            // Create and save a Chef entry
            var chef = Chef.builder()
                    .user(user)
                    .profileImageUrl("")
                    .bio("")
                    .rating(0.0) // Default rating
                    .cuisineSpecialties("")
                    .contactEmail(request.getEmail())
                    .phoneNumber("")
                    .build();
            chefRepository.save(chef);
        }

        // Generate JWT token for the registered user
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch(Exception e) {
        System.out.println(e.fillInStackTrace());
    }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String initiatePasswordReset(String email) {
        // Check if the user exists
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " does not exist."));

        // Generate a reset token (JWT)
        String resetToken = jwtService.generateToken(user);

        // Send the token to the user's email
        sendResetTokenEmail(email, resetToken);

        return "Password reset email sent to: " + email;
    }

    public String resetPassword(String resetToken, String newPassword) {
        // Validate the token
        String email = jwtService.extractUsername(resetToken);

        // Fetch the user based on the email
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token or user does not exist."));

        // Reset the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        return "Password successfully reset!";
    }

    private void sendResetTokenEmail(String email, String resetToken) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            //this url is post need to update to get
            helper.setText(
                    "Click the link below to reset your password:\n" +
                            "http://localhost:8080/api/v1/auth/reset-password/confirm?token" + resetToken,
                    true
            );

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


}
