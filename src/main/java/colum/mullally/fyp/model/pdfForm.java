package colum.mullally.fyp.model;


import java.util.ArrayList;
import java.util.List;

public class pdfForm {
    private String name;
    private List<ContentField> attributes;

    public pdfForm() {
        this.attributes = new ArrayList<>();
    }

    public pdfForm(String name) {
        this.name = name;
        this.attributes = new ArrayList<ContentField>();
    }
    public String getName() {
        return name;
    }

    public List<ContentField> getAttributes() {
        return attributes;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void addAttributes(String name, String content) {
        this.attributes.add(new ContentField(name, content));
    }
    public void addAttributes(String name) {
        this.attributes.add(new ContentField(name, null));
    }
}
