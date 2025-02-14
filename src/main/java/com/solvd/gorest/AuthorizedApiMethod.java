package com.solvd.gorest;

import com.solvd.gorest.services.AuthService;
import com.zebrunner.carina.api.AbstractApiMethodV2;

/**
 * Authorized by default
 */
public abstract class AuthorizedApiMethod extends AbstractApiMethodV2 {

    public AuthorizedApiMethod() {
        setHeader("Authorization", "Bearer " + AuthService.getAuthToken());
    }

    public void removeAuthorizationHeader(){
        getRequest().auth().none();
    }
}
