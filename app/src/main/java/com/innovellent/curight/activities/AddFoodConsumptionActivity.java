package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.Category_SpinnerAdapter;
import com.innovellent.curight.adapter.FoodItemSpinnerAdapter;
import com.innovellent.curight.adapter.FoodUnitSpinnerAdapter;
import com.innovellent.curight.adapter.SwimmingAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Calorie;
import com.innovellent.curight.model.Category_Feed;
import com.innovellent.curight.model.Category_List;
import com.innovellent.curight.model.FoodItem;
import com.innovellent.curight.model.FoodItem_Feed;
import com.innovellent.curight.model.FoodUnit;
import com.innovellent.curight.model.Food_Units;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseCalorie;
import com.innovellent.curight.model.ServerResponseFoodCategory;
import com.innovellent.curight.model.ServerResponseFoodItem;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.Swimming;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.SharedPrefService;
import com.innovellent.curight.utility.Util;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.BREAKFAST;
import static com.innovellent.curight.utility.Constants.DATE;
import static com.innovellent.curight.utility.Constants.DINNER;
import static com.innovellent.curight.utility.Constants.FOOD_ID;
import static com.innovellent.curight.utility.Constants.LUNCH;
import static com.innovellent.curight.utility.Constants.MEAL_TYPE_NAME;
import static com.innovellent.curight.utility.Constants.SERVING_QTY;
import static com.innovellent.curight.utility.Constants.SERVING_UNIT;
import static com.innovellent.curight.utility.Constants.SNACKS;
import static com.innovellent.curight.utility.Constants.TIME;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.USER_ID;

public class AddFoodConsumptionActivity extends AppCompatActivity {

    private static final String TAG = "CuRight";
    Category_SpinnerAdapter category_spinneradapter;
    ArrayList<Category_List> categorylist = new ArrayList<Category_List>();
    EditText et_date;
    private Toolbar toolbar;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private Spinner itemSpinner, unitSpinner,sp_category;
    private Button btnSubmit, btnAdd;
    private int mYear, mMonth, mDay;
    private EditText etQuantity;
    private ProgressDialog progressDialog;
    private SharedPrefService sharedPrefService;
    private FoodItemSpinnerAdapter foodItemSpinnerAdapter;
    private SwimmingAdapter swimmingAdapter;

    private long userId;
    private String accessToken, title, time, quantity;
    private ArrayList<Swimming> foodConsumptions = new ArrayList<>();

    public AddFoodConsumptionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
     //   setContentView(R.layout.activity_add_food);

        initReferences();
        setupToolbar();
        initClickListeners();
  //      getcategorySpinnerdata();
        sharedPrefService = SharedPrefService.getInstance();
        userId = sharedPrefService.getLong(USER_ID);
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(et_date);
            }
        });
       // getFoodItems();
        //getfooditemspinner();
    }

    private void getfooditemspinner(Long catid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.d(TAG,"getfooditem id"+catid);
        Call<ServerResponseFoodItem> call = apiInterface.getfooditem(catid);
        call.enqueue(new Callback<ServerResponseFoodItem>() {
            @Override
            public void onResponse(Call<ServerResponseFoodItem> call, Response<ServerResponseFoodItem> response) {
                Log.d(TAG, "getfooditem body:" + response.body());
                if (response.body() != null) {
                    Log.d(TAG, "getfooditem code: " + response.body().getCode());

                    ArrayList<FoodItem_Feed> foodItems = response.body().getResults();
                    if (foodItems.size() > 0) {
                        foodItemSpinnerAdapter = new FoodItemSpinnerAdapter(AddFoodConsumptionActivity.this, foodItems);
                        itemSpinner.setAdapter(foodItemSpinnerAdapter);
                        itemSpinner.setSelection(0);
                        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                resetFoodUnitSpinnerAdapter();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        resetFoodUnitSpinnerAdapter();
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResponseFoodItem> call, Throwable t) {

            }
        });
    }

//    private void getcategorySpinnerdata() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(new Config().SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        Call<ServerResponseFoodCategory> call = apiInterface.getcategory();
//        call.enqueue(new Callback<ServerResponseFoodCategory>() {
//            @Override
//            public void onResponse(Call<ServerResponseFoodCategory> call, Response<ServerResponseFoodCategory> response) {
//                if (response.body() != null) {
//                    Log.d(TAG, "gettestcentre " + response.body().getCode());
//                    if(response.body().getCode()==200){
//                        ArrayList<Category_Feed> result = response.body().getResults();
//                        for (int i = 0; i < result.size(); i++){
//                            categorylist.add(new Category_List(result.get(i).getFoodcategoryid(),result.get(i).getCategoryname()));
//                        }
//                        setspinneradpter();
//                    }else {
//
//                    }
//                }else {
//
//                }
//            }
//            public void onFailure(Call<ServerResponseFoodCategory> call, Throwable t) {
//                t.getMessage();
//                String message = t.getMessage();
//                Log.e("TAG","error :: "+message);
//                if (!isFinishing()) {
//                    if (Constants.SERVER_DOWN.equals(message)) {
//                        Util.showAlertDialog(AddFoodConsumptionActivity.this, "Server is Down! Please try  again later!", "ERROR");
//                        return;
//                    } else {
//                        Util.showAlertDialog(AddFoodConsumptionActivity.this, message, "ERROR");
//                        return;
//                    }
//                }
//
//
//            }
//        });
//    }

    private void setspinneradpter() {
        category_spinneradapter = new Category_SpinnerAdapter(getApplicationContext(),categorylist);
        sp_category.setAdapter(category_spinneradapter);
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getfooditemspinner(categorylist.get(i).getFoodcategoryid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getfooditemspinner(categorylist.get(0).getFoodcategoryid());
            }
        });
    }

    private void initReferences() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.title);
        et_date = (EditText) findViewById(R.id.et_date);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
     //   sp_category = (Spinner) findViewById(R.id.sp_fooditem);
     //   itemSpinner = (Spinner) findViewById(R.id.spItem);
        unitSpinner = (Spinner) findViewById(R.id.spUnit);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        if ((actionBar = getSupportActionBar()) != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title = getIntent().getStringExtra(TITLE);
        if (title != null) {
            switch (title) {
                case BREAKFAST:
                    time = "10:30AM";
                    break;
                case LUNCH:
                    time = "1:30PM";
                    break;
                case SNACKS:
                    time = "5:30PM";
                    break;
                case DINNER:
                    time = "8:30PM";
                    break;
                default:
                    time = "";
                    break;
            }
        } else title = "Curight";
        tvTitle.setText(title);
    }

    private void initClickListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(quantity = etQuantity.getText().toString().trim()).isEmpty())
                    //addFoodConsumption(((FoodItem) itemSpinner.getSelectedItem()).getName(), ((FoodUnit) unitSpinner.getSelectedItem()));
                addFoodConsumption(((FoodItem_Feed) itemSpinner.getSelectedItem()).getFoodName(), ((Food_Units) unitSpinner.getSelectedItem()));
                else
                    Toast.makeText(AddFoodConsumptionActivity.this, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void getFoodItems() {
//        Log.d(TAG,"getFoodItemcalled");
//        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Loading", "please wait", false);
//        progressDialog.show();
//
//        ApiInterface client = ApiClient.getClient();
//        Call<ServerResponse<List<FoodItem>>> call = client.getAllFoodItem(accessToken);
//
//        call.enqueue(new Callback<ServerResponse<List<FoodItem>>>() {
//            @Override
//            public void onResponse(Call<ServerResponse<List<FoodItem>>> call, Response<ServerResponse<List<FoodItem>>> response) {
//                Log.d(TAG,"getFoodItem response"+getBaseContext());
//                if (getBaseContext() != null) {
//                    progressDialog.dismiss();
//                    if (response.isSuccessful()) {
//                        List<FoodItem> foodItems = response.body().getResults();
//                        if (foodItems.size() > 0) {
//                            foodItemSpinnerAdapter = new FoodItemSpinnerAdapter(AddFoodConsumptionActivity.this, foodItems);
//                            itemSpinner.setAdapter(foodItemSpinnerAdapter);
//                            itemSpinner.setSelection(0);
//                            itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    resetFoodUnitSpinnerAdapter();
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//                                }
//                            });
//                            resetFoodUnitSpinnerAdapter();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse<List<FoodItem>>> call, Throwable t) {
//                if (getBaseContext() != null) progressDialog.dismiss();
//            }
//        });
//
//    }

    private void resetFoodUnitSpinnerAdapter() {
        if (itemSpinner.getSelectedItem() != null) {

           // ArrayList<FoodUnit> foodUnits = ((FoodItem) itemSpinner.getSelectedItem()).getUnits();
            ArrayList<Food_Units> foodUnits = ((FoodItem_Feed) itemSpinner.getSelectedItem()).getResults();
            if (foodUnits.size() > 0) {
                FoodUnitSpinnerAdapter foodUnitSpinnerAdapter = new FoodUnitSpinnerAdapter(AddFoodConsumptionActivity.this, foodUnits);
                unitSpinner.setAdapter(foodUnitSpinnerAdapter);
                unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!(quantity = etQuantity.getText().toString().trim()).isEmpty())
                            getCalories(id, quantity);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    public void selectDate(final EditText setedttxt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddFoodConsumptionActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);

                        int dayOfWeek = GregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                        String day = "", monthYear = "";
                        int month = monthOfYear + 1;
                        if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = dayOfMonth + "";
                        }
                        if (month >= 1 && month <= 9) {
                            monthYear = "0" + month;
                        } else {
                            monthYear = month + "";
                        }

                        String date = day + "-" + monthYear + "-" + year;
                        setedttxt.setText(dayOfMonth + "/" + (monthYear) + "/" + year);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

    public void getCalories(long food_id, String quantity) {

        ApiInterface client = ApiClient.getClient();

        Calorie cal = new Calorie(food_id + "", quantity);

        Call<ServerResponseCalorie> call = client.getCalories(accessToken, cal);

        call.enqueue(new Callback<ServerResponseCalorie>() {
            @Override
            public void onResponse(Call<ServerResponseCalorie> call, Response<ServerResponseCalorie> response) {

                if (response.isSuccessful()) {
                    Log.e("CALORIE", "Response ::  " + response.code() + "   message ::  " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseCalorie> call, Throwable t) {

            }
        });


    }

    public void addFoodConsumption(final String foodItem, final Food_Units foodUnit) {

        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Adding", "please wait", false);
        progressDialog.show();

        ApiInterface client = ApiClient.getClient();

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put(USER_ID, userId);
            paramObject.put(DATE, new SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH).format(Calendar.getInstance().getTime()));
            paramObject.put(FOOD_ID, foodUnit.getFoodid());
            paramObject.put(MEAL_TYPE_NAME, title);
            paramObject.put(SERVING_QTY, quantity);
            paramObject.put(SERVING_UNIT, foodUnit.getUnit());
            paramObject.put(TIME, time);

            Call<ServerResponse<String>> call = client.createFood(accessToken, paramObject.toString());
            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
                    if (getBaseContext() != null) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            foodConsumptions.add(new Swimming(foodItem, String.valueOf(userId), foodUnit.getUnit()));
                            swimmingAdapter = new SwimmingAdapter(AddFoodConsumptionActivity.this, R.layout.list_row_food, foodConsumptions);
                            recyclerView.setLayoutManager(new LinearLayoutManager(AddFoodConsumptionActivity.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(swimmingAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                    if (getBaseContext() != null) progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }
}
