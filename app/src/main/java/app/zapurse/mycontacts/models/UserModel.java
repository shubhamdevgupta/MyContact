package app.zapurse.mycontacts.models;

public class UserModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public UserModel(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public UserModel(String name, String email, String mobile, int id) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.id = id;
    }

    String name, email, mobile;
    int id;
}
