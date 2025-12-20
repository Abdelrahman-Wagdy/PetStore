package models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("password")
	private String password;

	@SerializedName("userStatus")
	private int userStatus;

	@SerializedName("phone")
	private String phone;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getPassword(){
		return password;
	}

	public String getPhone(){
		return phone;
	}


	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"CreateUserModel{" + 
			"firstName = '" + firstName + '\'' + 
			",lastName = '" + lastName + '\'' + 
			",password = '" + password + '\'' + 
			",userStatus = '" + userStatus + '\'' + 
			",phone = '" + phone + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
    }

    public static class Builder {

        private final UserModel user = new UserModel();

        public Builder firstName(String firstname) {user.firstName = firstname; return this;}
        public Builder lastName(String lastName) {user.lastName = lastName; return this;}
        public Builder password(String password) {user.password = password; return this;}
        public Builder userStatus(int userStatus) {user.userStatus = userStatus; return this;}
        public Builder phone(String phone) {user.phone = phone; return this;}
        public Builder id(int id) {user.id = id; return this;}
        public Builder email(String email) {user.email = email; return this;}
        public Builder userName(String userName) {user.username = userName; return this;}

        public UserModel build() {
            return user;
        }
    }
}