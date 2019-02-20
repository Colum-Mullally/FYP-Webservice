package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/User/")
public class UserController {
    private UserRepository userRepository;
    private UserAuthenticationRepository userAuthenticationRepository;
    @Autowired
    public UserController(UserRepository userRepository,UserAuthenticationRepository userAuthenticationRepository) {
        this.userRepository = userRepository;
        this.userAuthenticationRepository =userAuthenticationRepository;
    }
//    @GetMapping("/all")
//    public List<User> getAll(Principal principal){
//        System.out.println(principal.getName());
//        List<User> users =this.userRepository.findAll();
//        return users;
//    }
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,@RequestParam("password") String password){
                UserAuthentication securityUser = new UserAuthentication(username,password);
                User user = new User(username);
                userRepository.save(user);
                userAuthenticationRepository.save(securityUser);
                return "succesful";
    }
    @PostMapping("/jk")
    public String login(){
        return "hi";
    }
}
