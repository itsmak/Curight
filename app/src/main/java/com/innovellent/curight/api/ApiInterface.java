package com.innovellent.curight.api;

import com.innovellent.curight.model.Auth;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressureReport;
import com.innovellent.curight.model.Calorie;
import com.innovellent.curight.model.CholesterolReport;
import com.innovellent.curight.model.CreateExercise;
import com.innovellent.curight.model.DiagnosticCentre;
import com.innovellent.curight.model.FamilyProfile;
import com.innovellent.curight.model.FoodCreate;
import com.innovellent.curight.model.FoodItem;
import com.innovellent.curight.model.Goal;
import com.innovellent.curight.model.Login;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.MyServer_Response;
import com.innovellent.curight.model.POST_UPDATE_CLASS;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseAuth;
import com.innovellent.curight.model.ServerResponseBookedTest;
import com.innovellent.curight.model.ServerResponseCalorie;
import com.innovellent.curight.model.ServerResponseCreateExercise;
import com.innovellent.curight.model.ServerResponseDiagCenter;
import com.innovellent.curight.model.ServerResponseExercise;
import com.innovellent.curight.model.ServerResponseFood;
import com.innovellent.curight.model.ServerResponseGetFood;
import com.innovellent.curight.model.ServerResponseLogin;
import com.innovellent.curight.model.ServerResponseOffer;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.TestBookingCreate;
import com.innovellent.curight.model.TestBookingDetail;
import com.innovellent.curight.model.TestBookingId;
import com.innovellent.curight.model.UserId;
import com.innovellent.curight.model.UserIdStr;
import com.innovellent.curight.model.VACCINE_UPDATE_RESPONSE;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("getoffer")
    Call<ServerResponseOffer> getOffer(@Query("userid") String userid);

    @GET("test/getalltest")
    Call<ServerResponseTest> getTest();

    @POST("diagnosticcentre/gettestmapping")
    Call<ServerResponseDiagCenter> getDcTest(@Body DiagnosticCentre centre);

    @POST("diagnosticcentre/gettestdetail")
    Call<ServerResponseDiagCenter> getTestByDc(@Body DiagnosticCentre centre);

    @POST("user/authenticate")
    Call<ServerResponseAuth> performAuth(@Body Auth auth);

    @POST("user/validate")
    Call<ServerResponseLogin> performLogin(@Body Login loginCreds);

    @POST("testbooking/create")
    Call<TestBookingId> proceedToPay(@Header("x-access-token") String x_access_token, @Body TestBookingCreate testBookCreate);

    @POST("testbooking/get")
    Call<ServerResponseBookedTest> testBookDetail(@Header("x-access-token") String x_access_token, @Body TestBookingDetail testBookingDetail);

    @POST("exercise/get")
    Call<ServerResponseExercise> getExercise(@Header("x-access-token") String x_access_token, @Body UserIdStr userIdStr);

    @POST("exercise/create")
    Call<ServerResponseCreateExercise> createExercise(@Header("x-access-token") String x_access_token, @Body CreateExercise createExercise);

    //Blood Pressure APIs
    @Headers("Content-Type: application/json")
    @POST("bloodpressure/get")
    Call<ServerResponse<BloodPressureReport>> getBloodPressureRecords(@Header("x-access-token") String x_access_token, @Body String userId);

    @Headers("Content-Type: application/json")
    @POST("bloodpressure/create")
    Call<ServerResponse<String>> addBloodPressureRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("bloodpressure/delete")
    Call<ServerResponse<String>> deleteBloodPressureRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    //Cholesterol APIs
    @Headers("Content-Type: application/json")
    @POST("cholestrol/get")
    Call<ServerResponse<CholesterolReport>> getCholesterolRecords(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("cholestrol/create")
    Call<ServerResponse<String>> addCholesterolRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("cholestrol/delete")
    Call<ServerResponse<String>> deleteCholesterolRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    //BMI APIs
    @Headers("Content-Type: application/json")
    @POST("bmi/get")
    Call<ServerResponse<BMIReport>> getBMIRecords(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("bmi/create")
    Call<ServerResponse<String>> addBMIRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("bmi/delete")
    Call<ServerResponse<String>> deleteBMIRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    //Family APIs
    @Headers("Content-Type: application/json")
    @POST("patientprofile/getprofile")
    Call<ServerResponse<List<FamilyProfile>>> getFamilyProfiles(@Header("x-access-token") String accessToken, @Body String requestBody);

    //Goal APIs
    @Headers("Content-Type: application/json")
    @POST("goal/get")
    Call<ServerResponse<Goal>> getGoal(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("goal/update")
    Call<ServerResponse<String>> setGoal(@Header("x-access-token") String x_access_token, @Body String requestBody);

    //Food APIs
    @Headers("Content-Type: application/json")
    @GET("food/getfoodwithmultipleunits")
    Call<ServerResponse<List<FoodItem>>> getAllFoodItems(@Header("x-access-token") String x_access_token);

    @Headers("Content-Type: application/json")
    @POST("foodconsumption/get")
    Call<ServerResponseFood> getFood(@Header("x-access-token") String x_access_token, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("foodconsumption/create")
    Call<ServerResponse<String>> createFood(@Header("x-access-token") String x_access_token, @Body String createFood);

    @Headers("Content-Type: application/json")
    @POST("food/getaloriesbyfood")
    Call<ServerResponseCalorie> getCalories(@Header("x-access-token") String x_access_token, @Body Calorie calories);

    //get Vaccine
    @Headers("x-access-token: hjjgkuykg")
    @POST("vaccine/get")
    Call<MyServer_Response> getData(@Body PostBodyClass userId);;

    //get profile
    @Headers("x-access-token: hjjgkuykg")
    @POST("patientprofile/getprofile")
    Call<MyProfile_Response> getProfile(@Body PostBodyProfile userId);;

    //get vaccine update
    @Headers("x-access-token: hjjgkuykg")
    @POST("vaccine/update")
    Call<VACCINE_UPDATE_RESPONSE> get_vaccine_update(@Body POST_UPDATE_CLASS userId);;

}
