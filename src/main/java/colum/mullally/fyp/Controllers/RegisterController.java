package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RegisterController {
    private UserRepository userRepository;
    private UserAuthenticationRepository userAuthenticationRepository;
    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();

    public RegisterController(UserRepository userRepository,UserAuthenticationRepository userAuthenticationRepository) {
        this.userRepository = userRepository;
        this.userAuthenticationRepository =userAuthenticationRepository;
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password){
        UserAuthentication securityUser = new UserAuthentication(username,encoder.encode(password));
        User user = new User(username);
        userRepository.save(user);
        userAuthenticationRepository.save(securityUser);
        return "succesful";
    }
}
