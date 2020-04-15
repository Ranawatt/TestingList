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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

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
//        mockWebServer.enqueue(new MockResponse().setBody("Hello, World"));
//        mockWebServer.enqueue(new MockResponse().setBody("Sup, Bra"));
//        mockWebServer.enqueue(new MockResponse().setBody("Yo Dog"));
        mockWebServer.start();
        HttpUrl baseUrl = mockWebServer.url("https://dummy.restapiexample.com/api/");

        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200).setBody("{\"status\":\"success\",\"data\":[{\"id\":\"1\",\"employee_name\":\"Tiger Nixon\",\"employee_salary\":\"320800\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"2\",\"employee_name\":\"Garrett Winters\",\"employee_salary\":\"170750\",\"employee_age\":\"63\",\"profile_image\":\"\"},{\"id\":\"3\",\"employee_name\":\"Ashton Cox\",\"employee_salary\":\"86000\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"4\",\"employee_name\":\"Cedric Kelly\",\"employee_salary\":\"433060\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"5\",\"employee_name\":\"Airi Satou\",\"employee_salary\":\"162700\",\"employee_age\":\"33\",\"profile_image\":\"\"},{\"id\":\"6\",\"employee_name\":\"Brielle Williamson\",\"employee_salary\":\"372000\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"7\",\"employee_name\":\"Herrod Chandler\",\"employee_salary\":\"137500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"8\",\"employee_name\":\"Rhona Davidson\",\"employee_salary\":\"327900\",\"employee_age\":\"55\",\"profile_image\":\"\"},{\"id\":\"9\",\"employee_name\":\"Colleen Hurst\",\"employee_salary\":\"205500\",\"employee_age\":\"39\",\"profile_image\":\"\"},{\"id\":\"10\",\"employee_name\":\"Sonya Frost\",\"employee_salary\":\"103600\",\"employee_age\":\"23\",\"profile_image\":\"\"},{\"id\":\"11\",\"employee_name\":\"Jena Gaines\",\"employee_salary\":\"90560\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"12\",\"employee_name\":\"Quinn Flynn\",\"employee_salary\":\"342000\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"13\",\"employee_name\":\"Charde Marshall\",\"employee_salary\":\"470600\",\"employee_age\":\"36\",\"profile_image\":\"\"},{\"id\":\"14\",\"employee_name\":\"Haley Kennedy\",\"employee_salary\":\"313500\",\"employee_age\":\"43\",\"profile_image\":\"\"},{\"id\":\"15\",\"employee_name\":\"Tatyana Fitzpatrick\",\"employee_salary\":\"385750\",\"employee_age\":\"19\",\"profile_image\":\"\"},{\"id\":\"16\",\"employee_name\":\"Michael Silva\",\"employee_salary\":\"198500\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"17\",\"employee_name\":\"Paul Byrd\",\"employee_salary\":\"725000\",\"employee_age\":\"64\",\"profile_image\":\"\"},{\"id\":\"18\",\"employee_name\":\"Gloria Little\",\"employee_salary\":\"237500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"19\",\"employee_name\":\"Bradley Greer\",\"employee_salary\":\"132000\",\"employee_age\":\"41\",\"profile_image\":\"\"},{\"id\":\"20\",\"employee_name\":\"Dai Rios\",\"employee_salary\":\"217500\",\"employee_age\":\"35\",\"profile_image\":\"\"},{\"id\":\"21\",\"employee_name\":\"Jenette Caldwell\",\"employee_salary\":\"345000\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"22\",\"employee_name\":\"Yuri Berry\",\"employee_salary\":\"675000\",\"employee_age\":\"40\",\"profile_image\":\"\"},{\"id\":\"23\",\"employee_name\":\"Caesar Vance\",\"employee_salary\":\"106450\",\"employee_age\":\"21\",\"profile_image\":\"\"},{\"id\":\"24\",\"employee_name\":\"Doris Wilder\",\"employee_salary\":\"85600\",\"employee_age\":\"23\",\"profile_image\":\"\"}]}");
        assertEquals("HTTP/1.1 200 OK",String.valueOf(mockResponse.setResponseCode(200)));

       Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
       apiService = retrofit.create(APIService.class);

        Call<EmployeeResponse> call = apiService.getEmployeeResponse();
        response = call.execute();
        employeeResponse = response.body();

        datumList = employeeResponse.getData();
        assertTrue(datumList.size()>1);
        assertEquals(datumList,getDummy_EmployeeList());
        assertEquals(getDummy_EmployeeList().size(),datumList.size());
        assertEquals("Tiger Nixon",datumList.get(0).getEmployeeName());
        assertEquals("320800",datumList.get(0).getEmployeeSalary());

        Iterator<Datum> datumIterator = Mockito.mock(Iterator.class);
        for (int i=0;i<datumList.size();i++) {
            Mockito.when(datumIterator.hasNext()).thenReturn(true,false);
            Mockito.when(datumIterator.next()).thenReturn(datumList.get(i));

            assertEquals(getDummy_EmployeeList().get(i),datumList.get(i).getEmployeeName());
            // Mockito.when(datumList.iterator()).thenReturn(datumIterator) ;
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