package colum.mullally.fyp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserAuthentication")
public class UserAuthentication {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;

    public UserAuthentication() {

    }

    public UserAuthentication(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UserAuthentication(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UserAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "User";
    }
    public void set_id(String id) {
        this.id = id;
    }

    public String get_id() {
        return this.id;
    }

    public void setPassword(String password) {
        this.password =password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public boolean hasRole(String role){
        if(this.role.matches(role)){
            return true;
        }
        else
            return false;
    }
}
