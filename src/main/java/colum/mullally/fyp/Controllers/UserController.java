package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import colum.mullally.fyp.model.pdfForm;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
    @Autowired
    public UserController(UserRepository userRepository,UserAuthenticationRepository userAuthenticationRepository) {
        this.userRepository = userRepository;
        this.userAuthenticationRepository =userAuthenticationRepository;
    }
    @GetMapping("/all")
    public List<User> getAll(Principal principal){
        List<User> users =this.userRepository.findAll();
        return users;
    }
    @GetMapping("/details")
    public User details(Principal principal){
        User user =this.userRepository.findByUsername(principal.getName());
        return user;
    }
    @GetMapping("/details/{pdf}")
    public pdfForm pdfDetails(Principal principal, @PathVariable("pdf") String pdf){
        User user =this.userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdf);
        return user.getPdf().get(index);
    }
    @PostMapping("/details/{pdf}/addcontent/{contentFieldName}")
    public String pdfDetails(Principal principal, @PathVariable("pdf") String pdf, @PathVariable("contentFieldName") String contentFieldName,@RequestParam("content") String content){
        User user =this.userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdf);
        pdfForm form= user.getPdf().get(index);
        index =form.getAttributesIndex(contentFieldName);
        form.getAttributes().get(index).setContent(content);
        userRepository.save(user);
        return "succesful";
    }
}
