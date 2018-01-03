package com.innovellent.curight.api;

import com.innovellent.curight.model.Auth;
import com.innovellent.curight.model.BMIReport;
import com.innovellent.curight.model.BloodPressureReport;
import com.innovellent.curight.model.BloodSugarDeletePojo;
import com.innovellent.curight.model.BloodcountPojo;
import com.innovellent.curight.model.BloodsugarPojo;
import com.innovellent.curight.model.Calorie;
import com.innovellent.curight.model.CholesterolReport;
import com.innovellent.curight.model.CreateExercise;
import com.innovellent.curight.model.DeleteBMIRecord;
import com.innovellent.curight.model.DeleteBPRecordParameter;
import com.innovellent.curight.model.DeleteCholesterolRecordParameter;
import com.innovellent.curight.model.DeleteFctDataPojo;
import com.innovellent.curight.model.DeleteParameterPojo;
import com.innovellent.curight.model.DeleteParameterpatientreport;
import com.innovellent.curight.model.DiagnosticCenterDoctorByDC;
import com.innovellent.curight.model.DiagnosticCentre;
import com.innovellent.curight.model.FamilyProfile;
import com.innovellent.curight.model.FctPojo;
import com.innovellent.curight.model.FoodCreate;
import com.innovellent.curight.model.FoodItem;
import com.innovellent.curight.model.GetTestDetailCenter;
import com.innovellent.curight.model.Goal;
import com.innovellent.curight.model.Login;
import com.innovellent.curight.model.MED_REMAINDER_RESPONSE;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.MyServer_Response;
import com.innovellent.curight.model.OverviewCenterByDC;
import com.innovellent.curight.model.POST_CREATE_CLASS;
import com.innovellent.curight.model.POST_MED_CLASS;
import com.innovellent.curight.model.POST_TIME_UPDATE_CLASS;
import com.innovellent.curight.model.POST_UPDATE_CLASS;
import com.innovellent.curight.model.ParameterBMI;
import com.innovellent.curight.model.ParameterBP;
import com.innovellent.curight.model.ParameterCholesterol;
import com.innovellent.curight.model.ParameterPojo;
import com.innovellent.curight.model.PatientReportsPojo;
import com.innovellent.curight.model.PhotosCenterByDC;
import com.innovellent.curight.model.PostAddProfile;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.PostBodyRegister;
import com.innovellent.curight.model.Post_Family_Update;
import com.innovellent.curight.model.Post_MedReminderAdd;
import com.innovellent.curight.model.Post_Profile_Update;
import com.innovellent.curight.model.Profile_Response;
import com.innovellent.curight.model.Registration_Response;
import com.innovellent.curight.model.SearchingCenter;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseAuth;
import com.innovellent.curight.model.ServerResponseBloodCount;
import com.innovellent.curight.model.ServerResponseBloodSugar;
import com.innovellent.curight.model.ServerResponseBookedTest;
import com.innovellent.curight.model.ServerResponseCalorie;
import com.innovellent.curight.model.ServerResponseCreateExercise;
import com.innovellent.curight.model.ServerResponseDiagCenter;
import com.innovellent.curight.model.ServerResponseDoctorAppointment;
import com.innovellent.curight.model.ServerResponseDoctorByDC;
import com.innovellent.curight.model.ServerResponseExercise;
import com.innovellent.curight.model.ServerResponseFct;
import com.innovellent.curight.model.ServerResponseFood;
import com.innovellent.curight.model.ServerResponseFoodCategory;
import com.innovellent.curight.model.ServerResponseGetFood;
import com.innovellent.curight.model.ServerResponseGetTestDetail;
import com.innovellent.curight.model.ServerResponseLogin;
import com.innovellent.curight.model.ServerResponseOffer;
import com.innovellent.curight.model.ServerResponseOverviewByDC;
import com.innovellent.curight.model.ServerResponsePhotosByDC;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.ServerResponseWHRGet;
import com.innovellent.curight.model.ServerResponsemedicine;
import com.innovellent.curight.model.ServerSearchPage;
import com.innovellent.curight.model.TestBookingCreate;
import com.innovellent.curight.model.TestBookingDetail;
import com.innovellent.curight.model.TestBookingId;
import com.innovellent.curight.model.TestingCenter;
import com.innovellent.curight.model.UserId;
import com.innovellent.curight.model.UserIdStr;
import com.innovellent.curight.model.VACCINE_UPDATE_RESPONSE;
import com.innovellent.curight.model.WHRGetCenter;
import com.innovellent.curight.model.WhrList;

import java.util.List;

import okhttp3.ResponseBody;
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

    @GET("food/category/get")
    Call<ServerResponseFoodCategory> getcategory();

    @POST("test/gettestbyid")
    Call<ServerResponseTest> getTestById(@Header("x-access-token") String x_access_token, @Body TestingCenter testingCenter);

    @GET("diagnosticcentre/getalldoctor")
    Call<ServerResponseDoctorAppointment> getAllDoctor();

    @POST("search/get")
    Call<ServerSearchPage> getSearch(@Body SearchingCenter searchingCenter );

    @POST("diagnosticcentre/gettestmapping")
    Call<ServerResponseDiagCenter> getDcTest(@Body DiagnosticCentre centre);

    @POST("diagnosticcentre/gettestdetail")
    Call<ServerResponseGetTestDetail> getTestByDc(@Body GetTestDetailCenter centre);

    @POST("diagnosticcentre/getdoctorbydc")
    Call<ServerResponseDoctorByDC> getDoctorByDC(@Body DiagnosticCenterDoctorByDC center);

    @POST("diagnosticcentre/overview")
    Call<ServerResponseOverviewByDC> getOverviewByDc(@Body OverviewCenterByDC center);

    @POST("diagnosticcentre/getallphotos")
    Call<ServerResponsePhotosByDC> getPhotosByDC(@Body PhotosCenterByDC center);

    @Headers("x-access-token: hjjgkuykg")
    @POST("whr/get")
    Call<ResponseBody> getwhrlistdata(@Body ParameterPojo value);

    @Headers("Content-Type: application/json")
    @POST("fct/get")
    Call<ResponseBody> getfctdata(@Header("x-access-token") String accessToken, @Body FctPojo value);

    @Headers("Content-Type: application/json")
    @POST("fct/create")
    Call<ServerResponseFct<String>> addfctrecord(@Header("x-access-token") String accessToken, @Body String requestbody);

    @Headers("Content-Type: application/json")
    @POST("fct/delete")
    Call<ResponseBody> deletefctrecord(@Header("x-access-token") String accessToken, @Body DeleteFctDataPojo deleteFctDataPojo);

    @Headers("Content-Type: application/json")
    @POST("bloodcount/get")
    Call<ResponseBody> getbloodcountdata(@Header("x-access-token") String accessToken, @Body BloodcountPojo bloodcountPojo);

    @Headers("Content-Type: application/json")
    @POST("bloodcount/create")
    Call<ServerResponseBloodCount<String>> addbloodcountrecord(@Header("x-access-token") String accessToken, @Body String requestbody);

    @Headers("Content-Type: application/json")
    @POST("bloodsugar/get")
    Call<ResponseBody> getbloodsugardata(@Header("x-access-token") String accessToken, @Body BloodsugarPojo bloodsugarPojo);

    @Headers("Content-Type: application/json")
    @POST("bloodsugar/create")
    Call<ServerResponseBloodSugar<String>> addbloodsugarrecord(@Header("x-access-token") String accessToken, @Body String requestbody);

    @Headers("Content-Type: application/json")
    @POST("bloodsugar/delete")
    Call<ResponseBody> deletebloodsugardata(@Header("x-access-token") String accessToken, @Body BloodSugarDeletePojo bloodSugarPojo);

    @Headers("Content-Type: application/json")
    @POST("whr/delete")
    Call<ResponseBody> deleteWhrdata(@Header("x-access-token") String accessToken, @Body DeleteParameterPojo value);

    @Headers("Content-Type: application/json")
    @POST("whr/create")
    Call<ServerResponse<String>> addWHRRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("patientreports/get")
    Call<ResponseBody> getpatientreports(@Header("x-access-token") String accessToken, @Body PatientReportsPojo patientReportsPojo);

    @Headers("Content-Type: application/json")
    @POST("patientreports/delete")
    Call<ResponseBody> deletepatientreportrecord(@Header("x-access-token") String accessToken, @Body DeleteParameterpatientreport deleteParameterpatientreport);

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
    Call<ServerResponseCreateExercise> createExercise(@Header("x-access-token") String x_access_token, @Body String requestBody);

    //Blood Pressure APIs
    @Headers("x-access-token: hjjgkuykg")
    @POST("bloodpressure/get")
    Call<ResponseBody> getBloodPressureRecords(@Body ParameterBP userId);

    @Headers("Content-Type: application/json")
    @POST("bloodpressure/create")
    Call<ServerResponse<String>> addBloodPressureRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("bloodpressure/delete")
    Call<ResponseBody> deleteBloodPressureRecord(@Header("x-access-token") String accessToken, @Body DeleteBPRecordParameter requestBody);

    //Cholesterol APIs
    @Headers("Content-Type: application/json")
    @POST("cholestrol/get")
    Call<ResponseBody> getCholesterolRecords(@Header("x-access-token") String accessToken, @Body ParameterCholesterol requestBody);

    @Headers("Content-Type: application/json")
    @POST("cholestrol/create")
    Call<ServerResponse<String>> addCholesterolRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("cholestrol/delete")
    Call<ResponseBody> deleteCholesterolRecord(@Header("x-access-token") String accessToken, @Body DeleteCholesterolRecordParameter requestBody);

    //BMI APIs
    @Headers("Content-Type: application/json")
    @POST("bmi/get")
    Call<ResponseBody> getBMIRecords(@Header("x-access-token") String accessToken, @Body ParameterBMI requestBody);

    @Headers("Content-Type: application/json")
    @POST("bmi/create")
    Call<ServerResponse<String>> addBMIRecord(@Header("x-access-token") String accessToken, @Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("bmi/delete")
    Call<ResponseBody> deleteBMIRecord(@Header("x-access-token") String accessToken, @Body DeleteBMIRecord requestBody);

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

    //get profile
    @Headers("x-access-token: hjjgkuykg")
    @POST("patientprofile/getprofile")
    Call<Profile_Response> getProfileIndividual(@Body PostBodyProfile userId);;

    //get vaccine update
    @Headers("x-access-token: hjjgkuykg")
    @POST("vaccine/update")
    Call<VACCINE_UPDATE_RESPONSE> get_vaccine_update(@Body POST_UPDATE_CLASS userId);;

    //get vaccine create
    @Headers("x-access-token: hjjgkuykg")
    @POST("vaccine/create")
    Call<VACCINE_UPDATE_RESPONSE> get_vaccine_create(@Body POST_CREATE_CLASS userid);;

    //get med remainder
    @Headers("x-access-token: hjjgkuykg")
    @POST("medreminder/get")
    Call<MED_REMAINDER_RESPONSE> get_med_remainder(@Body POST_MED_CLASS userid);;

    //create user
    @Headers("x-access-token: hjjgkuykg")
    @POST("medreminder/create")
    Call<Registration_Response> createmedremainder(@Body Post_MedReminderAdd userId);;

    //get vaccine create
    @Headers("x-access-token: hjjgkuykg")
    @POST("medreminder/update")
    Call<VACCINE_UPDATE_RESPONSE> get_med_update(@Body POST_TIME_UPDATE_CLASS userid);;

    //create user
    @Headers("x-access-token: hjjgkuykg")
    @POST("user/create")
    Call<Registration_Response> createUser(@Body PostBodyRegister userId);;

    //create profile
    @Headers("x-access-token: hjjgkuykg")
    @POST("family/create")
    Call<Registration_Response> createprofile(@Body PostAddProfile userId);;

    //get all medicine
    @GET("medreminder/medicine/get")
    Call<ServerResponsemedicine> getAllMedicine();

    //create user
    @Headers("x-access-token: hjjgkuykg")
    @POST("patientprofile/update")
    Call<Registration_Response> updatepatientprofile(@Body Post_Profile_Update userId);;

    @Headers("x-access-token: hjjgkuykg")
    @POST("patientprofile/update")
    Call<Registration_Response> updatefamilyprofile(@Body Post_Family_Update userId);;


}
