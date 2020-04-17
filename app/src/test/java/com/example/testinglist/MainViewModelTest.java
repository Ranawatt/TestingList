package com.example.testinglist;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;

import androidx.lifecycle.MutableLiveData;

import com.example.testinglist.model.Datum;
import com.example.testinglist.model.EmployeeResponse;
import com.example.testinglist.repository.EmployeeRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.internal.duplex.DuplexResponseBody;
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
    private  MockWebServer mockWebServer;
    Response<EmployeeResponse> response;
    EmployeeResponse employeeResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockWebServer = new MockWebServer();
        mainViewModel = new MainViewModel((Application) application.getApplicationContext());
    }

    @Test
    public void testNull(){

        assertNotNull(mainViewModel.getEmployeeData());
        assertFalse(mainViewModel.getEmployeeData().hasActiveObservers());
    }

    @Test
    public void getAllEmployeeTest() throws IOException {

        mockWebServer.enqueue(new MockResponse().setBody("Hello, World"));
        mockWebServer.enqueue(new MockResponse().setBody("Sup, Bra"));
        mockWebServer.enqueue(new MockResponse().setBody("Yo Dog"));
        mockWebServer.start();
        HttpUrl baseUrl = mockWebServer.url("https://dummy.restapiexample.com/api/");

        MockResponse mockResponse = new MockResponse();
        assertEquals("HTTP/1.1 200 OK",String.valueOf(mockResponse.setResponseCode(200)));
         InputStream inputStream = activity.getAssets().open("employee.json");
        int size = inputStream.available();
        byte [] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String jsonString = new String(buffer, "UTF-8");

        mockResponse.setBody(jsonString);
//        mockResponse.setBody("{\n" +
//                "\"status\":\"success\",\n" +
//                "\"data\":[\n" +
//                "{\n" +
//                "\"id\":\"1\",\n" +
//                "\"employee_name\":\"Tiger Nixon\",\n" +
//                "\"employee_salary\":\"320800\",\n" +
//                "\"employee_age\":\"61\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"2\",\n" +
//                "\"employee_name\":\"Garrett Winters\",\n" +
//                "\"employee_salary\":\"170750\",\n" +
//                "\"employee_age\":\"63\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"3\",\n" +
//                "\"employee_name\":\"Ashton Cox\",\n" +
//                "\"employee_salary\":\"86000\",\n" +
//                "\"employee_age\":\"66\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"4\",\n" +
//                "\"employee_name\":\"Cedric Kelly\",\n" +
//                "\"employee_salary\":\"433060\",\n" +
//                "\"employee_age\":\"22\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"5\",\n" +
//                "\"employee_name\":\"Airi Satou\",\n" +
//                "\"employee_salary\":\"162700\",\n" +
//                "\"employee_age\":\"33\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"6\",\n" +
//                "\"employee_name\":\"Brielle Williamson\",\n" +
//                "\"employee_salary\":\"372000\",\n" +
//                "\"employee_age\":\"61\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"7\",\n" +
//                "\"employee_name\":\"Herrod Chandler\",\n" +
//                "\"employee_salary\":\"137500\",\n" +
//                "\"employee_age\":\"59\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"8\",\n" +
//                "\"employee_name\":\"Rhona Davidson\",\n" +
//                "\"employee_salary\":\"327900\",\n" +
//                "\"employee_age\":\"55\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"9\",\n" +
//                "\"employee_name\":\"Colleen Hurst\",\n" +
//                "\"employee_salary\":\"205500\",\n" +
//                "\"employee_age\":\"39\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"10\",\n" +
//                "\"employee_name\":\"Sonya Frost\",\n" +
//                "\"employee_salary\":\"103600\",\n" +
//                "\"employee_age\":\"23\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"11\",\n" +
//                "\"employee_name\":\"Jena Gaines\",\n" +
//                "\"employee_salary\":\"90560\",\n" +
//                "\"employee_age\":\"30\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"12\",\n" +
//                "\"employee_name\":\"Quinn Flynn\",\n" +
//                "\"employee_salary\":\"342000\",\n" +
//                "\"employee_age\":\"22\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"13\",\n" +
//                "\"employee_name\":\"Charde Marshall\",\n" +
//                "\"employee_salary\":\"470600\",\n" +
//                "\"employee_age\":\"36\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"14\",\n" +
//                "\"employee_name\":\"Haley Kennedy\",\n" +
//                "\"employee_salary\":\"313500\",\n" +
//                "\"employee_age\":\"43\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"15\",\n" +
//                "\"employee_name\":\"Tatyana Fitzpatrick\",\n" +
//                "\"employee_salary\":\"385750\",\n" +
//                "\"employee_age\":\"19\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"16\",\n" +
//                "\"employee_name\":\"Michael Silva\",\n" +
//                "\"employee_salary\":\"198500\",\n" +
//                "\"employee_age\":\"66\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"17\",\n" +
//                "\"employee_name\":\"Paul Byrd\",\n" +
//                "\"employee_salary\":\"725000\",\n" +
//                "\"employee_age\":\"64\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"18\",\n" +
//                "\"employee_name\":\"Gloria Little\",\n" +
//                "\"employee_salary\":\"237500\",\n" +
//                "\"employee_age\":\"59\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"19\",\n" +
//                "\"employee_name\":\"Bradley Greer\",\n" +
//                "\"employee_salary\":\"132000\",\n" +
//                "\"employee_age\":\"41\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"20\",\n" +
//                "\"employee_name\":\"Dai Rios\",\n" +
//                "\"employee_salary\":\"217500\",\n" +
//                "\"employee_age\":\"35\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"21\",\n" +
//                "\"employee_name\":\"Jenette Caldwell\",\n" +
//                "\"employee_salary\":\"345000\",\n" +
//                "\"employee_age\":\"30\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"22\",\n" +
//                "\"employee_name\":\"Yuri Berry\",\n" +
//                "\"employee_salary\":\"675000\",\n" +
//                "\"employee_age\":\"40\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"23\",\n" +
//                "\"employee_name\":\"Caesar Vance\",\n" +
//                "\"employee_salary\":\"106450\",\n" +
//                "\"employee_age\":\"21\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "},\n" +
//                "{\n" +
//                "\"id\":\"24\",\n" +
//                "\"employee_name\":\"Doris Wilder\",\n" +
//                "\"employee_salary\":\"85600\",\n" +
//                "\"employee_age\":\"23\",\n" +
//                "\"profile_image\":\"\"\n" +
//                "}\n" +
//                "]\n" +
//                "}");
        mockWebServer.enqueue(mockResponse);
        String responseString = mockResponse.getBody().readUtf8();
        JsonObject object = new JsonParser().parse(responseString).getAsJsonObject();
        JsonElement jsonElement = object.get("data");
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray != null){
            int len = jsonArray.size();
            for (int i=0;i < len;i++){
                arrayList.add(jsonArray.get(i));
            }
        }
        
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
            Mockito.when(datumIterator.next()).thenReturn(datumList.get(i));

            assertEquals(getDummy_EmployeeList().get(i),datumList.get(i).getEmployeeName());
             Mockito.when(datumList.iterator()).thenReturn(datumIterator) ;
        }
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