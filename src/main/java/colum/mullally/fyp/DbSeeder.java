package colum.mullally.fyp;

import colum.mullally.fyp.Repositories.UserAuthenticationRepository;
import colum.mullally.fyp.Repositories.UserRepository;
import colum.mullally.fyp.model.User;
import colum.mullally.fyp.model.UserAuthentication;
import colum.mullally.fyp.model.pdfForm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserRepository userRepository;
    private UserAuthenticationRepository userAuthenticationRepository;

    public DbSeeder(UserRepository userRepository,UserAuthenticationRepository userAuthenticationRepository) {
        this.userRepository = userRepository;
        this.userAuthenticationRepository=userAuthenticationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        UserAuthentication A=new UserAuthentication("admin",encoder.encode("admin") ,"ADMIN");
        User u1 = new User(
                "admin",
                Arrays.asList(
                        new pdfForm("Studentform")
                )
        );
        u1.getPdf().get(0).addAttributes("test");
        this.userRepository.deleteAll();
        this.userAuthenticationRepository.deleteAll();
        this.userRepository.save(u1);
        this.userAuthenticationRepository.save(A);
    }
}
