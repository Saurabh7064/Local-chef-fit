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
}
