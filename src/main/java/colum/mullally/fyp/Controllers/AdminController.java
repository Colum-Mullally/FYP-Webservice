package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    private UserRepository userRepository;
    private UserAuthenticationRepository userAuthenticationRepository;

    public AdminController(UserRepository userRepository,UserAuthenticationRepository userAuthenticationRepository) {
        this.userRepository = userRepository;
        this.userAuthenticationRepository =userAuthenticationRepository;
    }

    @GetMapping("/allusers")
    public ResponseEntity getAll(Principal principal){
        UserAuthentication user = userAuthenticationRepository.findByUsername(principal.getName());
        if (user.hasRole("ADMIN")){
        List<User> users =this.userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/promote")
    public ResponseEntity promote(@RequestParam("username") String username, Principal principal){
        UserAuthentication user = userAuthenticationRepository.findByUsername(principal.getName());
        if (user.hasRole("ADMIN")){
        user = userAuthenticationRepository.findByUsername(username);
        user.setRole("ADMIN");
        userAuthenticationRepository.save(user);
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>( HttpStatus.FORBIDDEN);
    }

}

