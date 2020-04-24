package com.example.testinglist;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.testinglist.model.Datum;
import com.example.testinglist.model.EmployeeResponse;
import com.example.testinglist.repository.EmployeeRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    private MainViewModel mainViewModel;
    @Mock
    private Application application;
    @Mock
    private  Activity activity;
    @Mock
    private Datum datum;
    @Mock
    APIService apiService;
    @Mock
    List<Datum> datumList;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    private MutableLiveData<List<Datum>> mutableLiveDatum = new MutableLiveData<>();
    private ArrayList arrayList = new ArrayList<String>();
    private ArrayList dummyArrayList = new ArrayList<String>();
    private  MockWebServer mockWebServer;
    @Mock
    Response<EmployeeResponse> response;
    EmployeeResponse employeeResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockWebServer = new MockWebServer();
        employeeRepository = new EmployeeRepository();
        mainViewModel = new MainViewModel((Application) application.getApplicationContext());
    }

    @Test
    public void testNull(){

        assertNotNull(mainViewModel.getEmployeeData());
        assertFalse(mainViewModel.getEmployeeData().hasActiveObservers());
    }

    @Test
    public void getAllEmployeeTest() throws IOException {

        mockWebServer.start();
        MockResponse mockResponse = new MockResponse();
        assertEquals("HTTP/1.1 200 OK",String.valueOf(mockResponse.setResponseCode(200)));
        mockResponse.setBody(getJson());
        mockWebServer.enqueue(mockResponse);
        String responseString = mockResponse.getBody().readUtf8();
        JsonObject object = new JsonParser().parse(responseString).getAsJsonObject();
        JsonElement jsonElement = object.get("data");
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray != null)
            for (int i=0;i<jsonArray.size();i++){
                JsonElement element = jsonArray.get(i).getAsJsonObject().get("employee_name");
                dummyArrayList.add(element.getAsString());
            }
        // Fetching Actual response from the server
       HttpUrl baseUrl = mockWebServer.url("https://dummy.restapiexample.com/api/");
       Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
       apiService = retrofit.create(APIService.class);

        Call<EmployeeResponse> call = apiService.getEmployeeResponse();
        response = call.execute();
        employeeResponse = response.body();
        datumList = employeeResponse.getData();
        assertTrue(datumList.size()>1);
        assertEquals(getDummy_EmployeeList().size(),datumList.size());
        assertEquals("Tiger Nixon",datumList.get(0).getEmployeeName());
        assertEquals("320800",datumList.get(0).getEmployeeSalary());

        Iterator<Datum> datumIterator = Mockito.mock(Iterator.class);
        for (int i=0;i<datumList.size();i++) {
            Mockito.when(datumIterator.hasNext()).thenReturn(true,false);
            assertEquals(getDummy_EmployeeList().get(i),datumList.get(i).getEmployeeName());
        }
        // Comparing the value with dummy response
        assertEquals(24,jsonArray.size());
        assertEquals("Tiger Nixon",dummyArrayList.get(0));
        for (int i=0;i<jsonArray.size();i++){
            assertEquals(getDummy_EmployeeList().get(i),dummyArrayList.get(i));
        }
    }

    private String getJson() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("employee.json");
        int size = inputStream.available();
        byte [] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String jsonString = new String(buffer, "UTF-8");
        return jsonString;
    }

    @Test
    public void getEmployeeData() {
        datumList.add(datum);
        Mockito.verify(datumList).add(datum);
        assertEquals(0,datumList.size());

        Mockito.when(datumList.size()).thenReturn(24);
        assertEquals(24,datumList.size());

        Mockito.when(datum.getEmployeeName()).thenReturn("Tiger Nixon");
        assertEquals("Tiger Nixon",datum.getEmployeeName());
    }

    public ArrayList getDummy_EmployeeList(){
            arrayList.add("Tiger Nixon");
        arrayList.add("Garrett Winters");
        arrayList.add("Ashton Cox");
        arrayList.add("Cedric Kelly");
        arrayList.add("Airi Satou");
        arrayList.add("Brielle Williamson");
        arrayList.add("Herrod Chandler");
        arrayList.add("Rhona Davidson");
        arrayList.add("Colleen Hurst");
        arrayList.add("Sonya Frost");
        arrayList.add("Jena Gaines");
        arrayList.add("Quinn Flynn");
        arrayList.add("Charde Marshall");
        arrayList.add("Haley Kennedy");
        arrayList.add("Tatyana Fitzpatrick");
        arrayList.add("Michael Silva");
        arrayList.add("Paul Byrd");
        arrayList.add("Gloria Little");
        arrayList.add("Bradley Greer");
        arrayList.add("Dai Rios");
        arrayList.add("Jenette Caldwell");
        arrayList.add("Yuri Berry");
        arrayList.add("Caesar Vance");
        arrayList.add("Doris Wilder");
        return arrayList;
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
        apiService = null;
        mainViewModel = null;
    }
}