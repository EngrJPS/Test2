package com.example.test2.SendNotif;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAg_o49Ow:APA91bEav1O-BE7YyKAMnUEwCSnIxSVthsvHobSS7QvBGfHQetRVLcRjKjk5rA54b7ds_CBZ7uy5Afyzggt8Nbwb3z7PC1XvOOpUsnQSTXYmj06t1zzS1KWWGTMYo81FGGLJa0e5I4PK"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
