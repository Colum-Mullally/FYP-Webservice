package colum.mullally.fyp.model;


import java.util.ArrayList;
import java.util.List;

public class pdfForm {
    private String name;
    private String url;
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

    public String getUrl() {
        return url;
    }
    public int getAttributesIndex(String name){
        for(int x =0; x < attributes.size();x++){
            if(attributes.get(x).getName().matches(name)){
                return x;
            }
        }
        return -1;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
