package com.example.hstalk.Retrofit;

import com.example.hstalk.Retrofit.RequestBody.RequestPut;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGet;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetBoard;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetBoardList;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetComments;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetCommentsUserName;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetInfoByPI;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetInfoByRI;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetPush;
import com.example.hstalk.Retrofit.ResponseBody.ResponseGetUserInfo;
import com.example.hstalk.Retrofit.ResponseBody.ResponsecheckEmail;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetroBaseApiService {
    final String Base_URL = "http://52.231.69.121";
    @GET("/posts/{userId}")
    Call<ResponseGet> getFirst(@Path("userId") String id);
    @GET("/posts")
    Call<List<ResponseGet>> getSecond(@Query("userId") String id);
    @GET("/getUserInfo.php")
    Call<ResponseGetUserInfo> getUserInfo(@Query("user_Id") String id);
    @GET("/getBoardList.php")
    Call<List<ResponseGetBoardList>> getBoardList();
    @GET("/getCommentsUserName.php")
    Call<List<ResponseGetCommentsUserName>> getCommentsUserName(@Query("userName") String id);

    @GET("/getInfoByPI.php")
    Call<List<ResponseGetInfoByPI>> getInfoByPI(@Query("PI") String id);
    @GET("/getInfoByRI.php")
    Call<List<ResponseGetInfoByRI>> getInfoByRI(@Query("RI") String id);
    @GET("/getComments.php")
    Call<List<ResponseGetComments>> getComments(@Query("postId") int id);
    @GET("/getBoard.php")
    Call<ResponseGetBoard> getBoard(@Query("postId") int id);
    @GET("/getPush.php")
    Call<ResponseGetPush> getPush(@Query("pushId") String id);
    @GET("/checkEmail.php")
    Call<ResponsecheckEmail> checkEmail(@Query("email") String id);

    @FormUrlEncoded
    @POST("/CreateBoard.php")
    Call<Void> CreateBoard(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/insertReply.php")
    Call<Void> commentBoard(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/endCall.php")
    Call<Void> updateEndCall(@FieldMap HashMap<String, Object> parameters);

    @FormUrlEncoded
    @POST("/posts")
    Call<ResponseGet> postFirst(@FieldMap HashMap<String, Object> parameters);

    @PUT("/posts/1")
    Call<ResponseGet> putFirst(@Body RequestPut parameters);

    @FormUrlEncoded
    @PATCH("/posts/1")
    Call<ResponseGet> patchFirst(@Field("title") String title);
    @DELETE("/posts/1")
    Call<ResponseBody> deleteFirst();

}
