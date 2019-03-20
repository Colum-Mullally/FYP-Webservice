package colum.mullally.fyp.Controllers;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import colum.mullally.fyp.model.pdfForm;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/details")
    public ResponseEntity details(Principal principal){
        User user =this.userRepository.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/details/{pdf}")
    public ResponseEntity pdfDetails(Principal principal, @PathVariable("pdf") String pdf){
        User user =this.userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdf);
        if(index>=0){
        return new ResponseEntity<>(user.getPdf().get(index),HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No PDF of that name is associated with this account",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/details/{pdf}/addcontent/{contentFieldName}")
    public ResponseEntity pdfDetails(Principal principal, @PathVariable("pdf") String pdf, @PathVariable("contentFieldName") String contentFieldName, @RequestParam("content") String content){
        User user =this.userRepository.findByUsername(principal.getName());
        int index = user.getPdfIndex(pdf);
        if(index>=0) {
            pdfForm form = user.getPdf().get(index);
            index = form.getAttributesIndex(contentFieldName);
            if(index>=0) {
                form.getAttributes().get(index).setContent(content);
                userRepository.save(user);
                return new ResponseEntity<>("Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Field of that name is associated with this PDF",HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No PDF of that name is associated with this account",HttpStatus.NOT_FOUND);
        }
    }
}
