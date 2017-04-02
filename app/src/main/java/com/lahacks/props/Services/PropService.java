package com.lahacks.props.Services;


import com.lahacks.props.Entity.PropsUserRequest;
import com.lahacks.props.Entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by connie on 4/1/17.
 */

public interface PropService {

    @POST("/users")
    Call<User> createUser(@Body PropsUserRequest propsUserRequest);


}
