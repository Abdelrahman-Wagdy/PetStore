package usertests;

import io.restassured.response.Response;
import models.UserModel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.UserServices;
import org.javatuples.Quartet;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {
    private UserModel buildUser(Quartet<String,String, String, String> quad,
                                Quartet<String,String, Integer, Integer> quad2){
        UserModel newUser = new UserModel();
        newUser.setFirstName(quad.getValue0());
        newUser.setLastName(quad.getValue1());
        newUser.setEmail(quad.getValue2());
        newUser.setPassword(quad.getValue3());
        newUser.setUsername(quad2.getValue0());
        newUser.setPhone(quad2.getValue1());
        newUser.setId(quad2.getValue2());
        newUser.setUserStatus(quad2.getValue3());
        return newUser;
    }

    @DataProvider(name = "users")
    public Object[][] userData() {
        return new Object[][] {
                {new Quartet<String,String, String, String>("abdelrahman", "wagdy", "a@b.com", "password"),
                        new Quartet<String, String, Integer, Integer>("awagdy","001122334455",10, 2)},
                {new Quartet<String,String, String, String>("test_f", "test_l", "f@l.com", "passwordfl"),
                        new Quartet<String,String, Integer, Integer>("testfl","010102202021", 11, 1)},
                {new Quartet<String,String, String, String>("qc", "pros", "qc@pros.com", "qc_pros"),
                        new Quartet<String,String, Integer, Integer>("qcpros","010102202021", 12, 3)}
        };
    }

    @Test(dataProvider = "users")
    public void validateThatAUserIsCreatedSuccessfully(Quartet<String,String, String, String> quad,
                                                       Quartet<String,String, Integer, Integer> quad2){
        UserModel newUser = buildUser(quad, quad2);
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
    public void validateThatUserCannotBeUpdatedUnlessLoggedIn(Quartet<String,String, String, String> quad,
                                                              Quartet<String,String, Integer, Integer> quad2){
        UserModel existing_user = buildUser(quad, quad2);
        existing_user.setUserStatus(10);
        Response response = UserServices.updateUser(existing_user);
        Assert.assertTrue(response.getStatusCode() != 200);

    }

    @Test(priority = 1)
    public void validateThatTheUserCannotLoginWithIncorrectCredentials(){
        Response response = UserServices.loginUser("abdelrahman", "ppp111");
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().toString(), "Invalid username/password supplied");
    }

    @Test(dataProvider = "users", priority = 2)
    public void validateThatTheUserCanLoginWithTheCorrectCredentials(Quartet<String,String, String, String> quad,
                                                                     Quartet<String,String, Integer, Integer> quad2){
        UserModel user = buildUser(quad, quad2);
        Response response = UserServices.loginUser(user.getUsername(), user.getPassword());
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(dataProvider = "users", priority = 2)
    public void validateThatTheUserCanUpdateTheUserDataWhenLoggedIn(Quartet<String,String, String, String> quad,
                                                                    Quartet<String,String, Integer, Integer> quad2){
        UserModel existing_user = buildUser(quad, quad2);
        Response loginResponse = UserServices.loginUser(existing_user.getUsername(), existing_user.getPassword());
        Assert.assertEquals(loginResponse.getStatusCode(), 200);

        existing_user.setUserStatus(10);


        Response userResponse = UserServices.updateUser(existing_user);

        Assert.assertEquals(userResponse.getStatusCode(), 200);
        UserModel actual_user = userResponse.as(UserModel.class);

        assertThat(actual_user).usingRecursiveComparison().isEqualTo(existing_user);

    }

    @Test(dataProvider = "users", priority = 3)
    public void validateThatTheUserCanAccessTheUserData(Quartet<String,String, String, String> quad,
                                                        Quartet<String,String, Integer, Integer> quad2){
        UserModel existing_user = buildUser(quad, quad2);
        existing_user.setUserStatus(10);

        Response response = UserServices.getUser(existing_user.getUsername());

        Assert.assertEquals(response.getStatusCode(), 200);
        UserModel actual_user = response.as(UserModel.class);
        assertThat(actual_user).usingRecursiveComparison().isEqualTo(existing_user);

    }

    @Test(dataProvider = "users", priority = 3)
    public void validateThatTheUserCanDeleteTheUserData(Quartet<String,String, String, String> quad,
    Quartet<String,String, Integer, Integer> quad2){
            UserModel existing_user = buildUser(quad, quad2);
            Response deleteUserresponse = UserServices.deleteUser(existing_user.getUsername());

            Assert.assertEquals(deleteUserresponse.getStatusCode(), 200);

            Response getUserResponse = UserServices.getUser(existing_user.getUsername());
            Assert.assertTrue(getUserResponse.getStatusCode() != 200);
    }
}
