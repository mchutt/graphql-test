package com.solvd.gorest;

import com.solvd.gorest.posts.CreatePostMethod;
import com.solvd.gorest.posts.GetAllPostsMethod;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.solvd.gorest.utils.APIConstants.TOKEN;

public class PostsTest implements IAbstractTest {
    public static final String RESOURCES_PATH = "api/graphql/posts/";
    private static final String USER_ID = "7676462";

    @DataProvider(name = "createPostData")
    public Object[][] createPostData() {
        return new Object[][]{
                {true},
                {false},
        };
    }

    @Test(dataProvider = "createPostData")
    public void createPostTest(boolean isAuthorized) {
        CreatePostMethod api = new CreatePostMethod();
        api.getProperties().setProperty("userId", USER_ID);
        if (isAuthorized) {
            api.setHeader("Authorization", "Bearer " + TOKEN);
        }else {
            api.setResponseTemplate(RESOURCES_PATH + "createPost/rs-non-authorized.json");
        }
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test
    public void getAllPostsTest(){
        GetAllPostsMethod api = new GetAllPostsMethod();
        api.callAPIExpectSuccess();
        api.validateResponseAgainstSchema(RESOURCES_PATH + "getAll/rs.schema");
    }

}
