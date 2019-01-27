package colum.mullally.fyp.model;

import java.util.ArrayList;
import java.util.List;

public class pdfForm {
    private String name;
    private List<String> attributes;

    public pdfForm() {
        this.attributes = new ArrayList<>();
    }

    public pdfForm(String name, List<String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void addAttributes(String attribute) {
        this.attributes.add(attribute);
    }
}
