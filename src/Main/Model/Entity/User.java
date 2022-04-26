package Main.Model.Entity;

import Main.Model.Repository.Authentication;

public class User {
    private int id;
    private String name;
    private String password;
    private String newPassword;
    private String confirmPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean confirmNewPassword(){
        return this.getNewPassword().equals(this.getConfirmPassword());
    }
    public boolean confirmOldPassword(){
        Authentication authentication = new Authentication();
        return authentication.getUser().getPassword().equals(this.getPassword());
    }
}
