package colum.mullally.fyp;

import colum.mullally.fyp.pdfForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String name;
    private List<pdfForm> pdf;

    public User(){
        this.pdf = new ArrayList<>();
    }

    public User(String name, List<pdfForm> pdf) {
        this.id = id;
        this.name = name;
        this.pdf = pdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<pdfForm> getPdf() {
        return pdf;
    }

    public void addPdf(pdfForm pdf) {
        this.pdf.add(pdf);
    }

    public String getId() {
        return id;
    }
}
