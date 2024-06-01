package org.apkmem.aplikasimembership.data;
/**
 * @editor David.Seay-71220909
 */
public class Users {
    private int id;
    private String username;
    private String email;
    private String telephone;
    private String password;

    public Users(int id, String username, String email, String telephone, String password) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setTelephone(telephone);
        this.setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
