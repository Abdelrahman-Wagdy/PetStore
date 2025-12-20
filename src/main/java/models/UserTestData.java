package models;


public class UserTestData {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String username;
    public String phone;
    public int id;
    public int status;

    public UserTestData(String firstName,
                        String lastName,
                        String email,
                        String password,
                        String username,
                        String phone,
                        int id,
                        int status) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.id = id;
        this.status = status;
    }
}
