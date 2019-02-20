package colum.mullally.fyp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "UserAuthentication")
public class UserAuthentication {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();

    public UserAuthentication() {

    }

    public UserAuthentication(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = encoder.encode(password);
        this.role = role;
    }
    public UserAuthentication(String username, String password, String role) {
        this.username = username;
        this.password = encoder.encode(password);
        this.role = role;
    }
    public UserAuthentication(String username, String password) {
        this.username = username;
        this.password = encoder.encode(password);
        this.role = "User";
    }
    public void set_id(String id) {
        this.id = id;
    }

    public String get_id() {
        return this.id;
    }

    public void setPassword(String password) {
        this.password = encoder.encode(password);
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
    public void setRole(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }
    public boolean hasRole(String role){
        if(this.role == role){
            return true;
        }
        else
            return false;
    }
}
