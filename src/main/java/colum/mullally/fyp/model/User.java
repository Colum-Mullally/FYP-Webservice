package colum.mullally.fyp.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "User")
public class User {
    @Id
    private String id;
    private String username;
    private List<pdfForm> pdf;

    public User(){
        this.pdf = new ArrayList<>();
    }

    public User(String name, List<pdfForm> pdf) {
        this.id =id;
        this.username = name;
        this.pdf = pdf;
    }
    public User(String name) {
        this.id =id;
        this.username = name;
        this.pdf = new ArrayList<>();
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public List<pdfForm> getPdf() {
        return pdf;
    }

    public void addPdf(pdfForm pdf) {
        this.pdf.add(pdf);
    }
    public int getPdfIndex(String name){
        for(int x =0; x < pdf.size();x++){
            System.out.println(pdf.get(x).getName());
            System.out.println(name);
            if(pdf.get(x).getName().matches(name)){
                return x;
            }
        }
        return -1;
    }

    public String getId() {
        return id;
    }

    public void deleteDoc(String fileUrl) {
        for(int x =0; x < pdf.size();x++){
            if(pdf.get(x).getUrl().matches(fileUrl)){
                pdf.remove(x);
            }
        }
    }
}
