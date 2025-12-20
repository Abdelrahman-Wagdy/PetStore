package usertests;

import io.restassured.response.Response;
import models.UserModel;
import models.UserTestData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.UserServices;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {

    private UserModel buildUser(UserTestData userTestData){
        return new UserModel.Builder()
                .firstName(userTestData.firstName)
                .lastName(userTestData.lastName)
                .userName(userTestData.username)
                .password(userTestData.password)
                .id(userTestData.id)
                .userStatus(userTestData.status)
                .email(userTestData.email)
                .phone(userTestData.phone)
                .build();
    }

    @DataProvider(name = "users", parallel = true)
    public Object[][] userData() {
        return new Object[][] {
                { new UserTestData("abdelrahman","wagdy","a@b.com","password","awagdy","001122334455",10,2) },
                { new UserTestData("test_f","test_l","f@l.com","passwordfl","testfl","010102202021",11,1) },
                { new UserTestData("qc","pros","qc@pros.com","qc_pros","qcpros","010102202021",12,3) }
        };
    }

    @Test(dataProvider = "users")
    public void validateThatAUserIsCreatedSuccessfully(UserTestData userTestData){
        UserModel newUser = buildUser(userTestData);
        Response response = UserServices.createUser(newUser);
        Assert.assertEquals(response.getStatusCode(), 200);
        UserModel actual_user = response.as(UserModel.class);

        assertThat(actual_user).usingRecursiveComparison().isEqualTo(newUser);

    }

    @Test
    public void validateThatTheUserCanLogout(){
        Response response = UserServices.logoutUser();

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(dataProvider = "users")
    public void validateThatUserCannotBeUpdatedUnlessLoggedIn(UserTestData userTestData){
        UserModel existing_user = buildUser(userTestData);

        UserModel updated_user = new UserModel.Builder()
                .firstName(existing_user.getFirstName())
                .phone(existing_user.getPhone())
                .email(existing_user.getEmail())
                .id(existing_user.getId())
                .userName(existing_user.getUsername())
                .password(existing_user.getPassword())
                .lastName(existing_user.getLastName())
                .userStatus(10)
                .build();

        Response response = UserServices.updateUser(updated_user);
        Assert.assertTrue(response.getStatusCode() != 200);

    }

    @Test(priority = 1)
    public void validateThatTheUserCannotLoginWithIncorrectCredentials(){
        Response response = UserServices.loginUser("abdelrahman", "ppp111");
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Invalid username/password supplied");
    }

    @Test(dataProvider = "users", priority = 2)
    public void validateThatTheUserCanLoginWithTheCorrectCredentials(UserTestData userTestData){
        UserModel user = buildUser(userTestData);
        Response response = UserServices.loginUser(user.getUsername(), user.getPassword());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "users", priority = 2)
    public void validateThatTheUserCanUpdateTheUserDataWhenLoggedIn(UserTestData userTestData){

        UserModel existing_user = buildUser(userTestData);
        Response loginResponse = UserServices.loginUser(existing_user.getUsername(), existing_user.getPassword());
        Assert.assertEquals(loginResponse.getStatusCode(), 200);

        UserModel updated_user = new UserModel.Builder()
                .firstName(existing_user.getFirstName())
                .phone(existing_user.getPhone())
                .email(existing_user.getEmail())
                .id(existing_user.getId())
                .userName(existing_user.getUsername())
                .password(existing_user.getPassword())
                .lastName(existing_user.getLastName())
                .userStatus(10)
                .build();

        Response userResponse = UserServices.updateUser(updated_user);

        Assert.assertEquals(userResponse.getStatusCode(), 200);
        UserModel actual_user = userResponse.as(UserModel.class);

        assertThat(actual_user).usingRecursiveComparison().isEqualTo(updated_user);

    }

    @Test(dataProvider = "users", priority = 3)
    public void validateThatTheUserCanAccessTheUserData(UserTestData userTestData){
        UserModel existing_user = buildUser(userTestData);

        UserModel updated_user = new UserModel.Builder()
                .firstName(existing_user.getFirstName())
                .phone(existing_user.getPhone())
                .email(existing_user.getEmail())
                .id(existing_user.getId())
                .userName(existing_user.getUsername())
                .password(existing_user.getPassword())
                .lastName(existing_user.getLastName())
                .userStatus(10)
                .build();

        Response response = UserServices.getUser(updated_user.getUsername());

        Assert.assertEquals(response.getStatusCode(), 200);
        UserModel actual_user = response.as(UserModel.class);
        assertThat(actual_user).usingRecursiveComparison().isEqualTo(updated_user);

    }

    @Test(dataProvider = "users", priority = 3)
    public void validateThatTheUserCanDeleteTheUserData(UserTestData userTestData){
            UserModel existing_user = buildUser(userTestData);
            Response deleteUserresponse = UserServices.deleteUser(existing_user.getUsername());

            Assert.assertEquals(deleteUserresponse.getStatusCode(), 200);

            Response getUserResponse = UserServices.getUser(existing_user.getUsername());
            Assert.assertTrue(getUserResponse.getStatusCode() != 200);
    }
}
