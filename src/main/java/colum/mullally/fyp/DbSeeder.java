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
//        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
//        UserAuthentication A=new UserAuthentication("admin",encoder.encode("a") ,"USER");
//        User u1 = new User(
//                "A",
//                Arrays.asList(
//                        new pdfForm("Studentform")
//                )
//        );
//        u1.getPdf().get(0).addAttributes("test");
//        User u2 = new User(
//                "B",
//                Arrays.asList(
//                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
//                )
//        );
//        User u3 = new User(
//                "C",
//                Arrays.asList(
//                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
//                )
//        );
//        User u4 = new User(
//                "D",
//                Arrays.asList(
//                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
//                )
//        );
//        User u5 = new User(
//                "E",
//                Arrays.asList(
//                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
//                )
//        );
//        User u6 = new User(
//                "F",
//                Arrays.asList(
//                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
//                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
//                )
//        );
//        this.userRepository.deleteAll();
//        this.userAuthenticationRepository.deleteAll();
//        this.userRepository.save(u1);
////        this.userRepository.save(u2);
////        this.userRepository.save(u3);
////        this.userRepository.save(u4);
////        this.userRepository.save(u5);
////        this.userRepository.save(u6);
//        this.userAuthenticationRepository.save(A);
    }
}
