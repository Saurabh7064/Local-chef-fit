package fit.localchef.controller;


import fit.localchef.models.requests.AuthenticationRequest;
import fit.localchef.models.responses.AuthenticationResponse;
import fit.localchef.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import fit.localchef.models.requests.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
                @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/reset-password/initiate")
    public ResponseEntity<String> initiatePasswordReset(@RequestParam String email) {
        String response = authenticationService.initiatePasswordReset(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String response = authenticationService.resetPassword(token, newPassword);
        return ResponseEntity.ok(response);
    }


}
