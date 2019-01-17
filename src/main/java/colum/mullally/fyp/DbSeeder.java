package colum.mullally.fyp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DbSeeder implements CommandLineRunner {
    private UserRepository userRepository;

    public DbSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(
                "A",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        User u2 = new User(
                "B",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        User u3 = new User(
                "C",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        User u4 = new User(
                "D",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        User u5 = new User(
                "E",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        User u6 = new User(
                "F",
                Arrays.asList(
                        new pdfForm("Studentform",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("forma",Arrays.asList("Colum","Mullally","15142434")),
                        new pdfForm("Sformc",Arrays.asList("Colum","Mullally","15142434"))
                )
        );
        this.userRepository.deleteAll();
        this.userRepository.save(u1);
        this.userRepository.save(u2);
        this.userRepository.save(u3);
        this.userRepository.save(u4);
        this.userRepository.save(u5);
        this.userRepository.save(u6);

    }
}
