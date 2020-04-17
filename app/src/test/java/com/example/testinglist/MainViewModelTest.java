package com.example.testinglist;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testinglist.model.Datum;
import com.example.testinglist.model.EmployeeResponse;
import com.example.testinglist.repository.EmployeeRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.internal.duplex.DuplexResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    private MainViewModel mainViewModel;
    @Mock
    private Application application;
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
//        when(employeeRepository.getMutableLiveDatum()).thenReturn(null);
//        assertTrue(response.isSuccessful() );
//        when(employeeResponse.getData()).thenReturn(ArgumentMatchers.<Datum>anyList());
        mockWebServer.enqueue(new MockResponse().setBody("Hello, World"));
        mockWebServer.enqueue(new MockResponse().setBody("Sup, Bra"));
        mockWebServer.enqueue(new MockResponse().setBody("Yo Dog"));
        mockWebServer.start();
        HttpUrl baseUrl = mockWebServer.url("https://dummy.restapiexample.com/api/");

        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200).setBody((DuplexResponseBody) application.getResources().getAssets().open("employee.json"));
        assertEquals("HTTP/1.1 200 OK",String.valueOf(mockResponse.setResponseCode(200)));

       Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
       apiService = retrofit.create(APIService.class);

        Call<EmployeeResponse> call = apiService.getEmployeeResponse();
        response = call.execute();
        employeeResponse = response.body();

        datumList = employeeResponse.getData();
        Object jsonObject = new JsonParser().parse(String.valueOf(mockResponse.getBody()));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) jsonObject;

        // getting firstName and lastName
        try {
            String firstName = (String) jo.getString("status");
            JSONArray jsonArray = jo.getJSONArray("data");
            String lastName = (String) jo.getString("");
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        String msg = String.valueOf(jsonObject.getAsJsonObject("status"));

//        datumList = mockRespons.
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