package com.solvd.gorest.users;

import com.jayway.jsonpath.JsonPath;
import com.solvd.gorest.AuthorizedApiMethod;
import com.solvd.gorest.dtos.UserDTO;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.PropertiesPath;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;

import java.util.Properties;

@Endpoint(url = "${config.env.api_url}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/graphql/users/createUser/rq.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
@PropertiesPath(path = "api/graphql/users/user.properties")
public class CreateUserMethod extends AuthorizedApiMethod {

    public static String getFieldName(String response) {
        return JsonPath.read(response, "$.errors[0].extensions.result[0].fieldName");
    }

    public static String getMessage(String response) {
        return JsonPath.read(response, "$.errors[0].extensions.result[0].messages[0]");
    }

    public void addUserProperties(UserDTO userDTO) {
        Properties props = getProperties();
        props.setProperty("email", userDTO.getEmail());
        props.setProperty("gender", userDTO.getGender());
        props.setProperty("name", userDTO.getName());
        props.setProperty("status", userDTO.getStatus());
    }

}

