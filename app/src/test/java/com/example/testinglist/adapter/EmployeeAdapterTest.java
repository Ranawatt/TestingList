package com.example.testinglist.adapter;

import com.example.testinglist.model.Datum;
import com.example.testinglist.repository.EmployeeRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeAdapterTest {
    @Mock
    private List<Datum> list;
    @Spy
    EmployeeRepository employeeRepository;
    @Mock
    private Datum datum;

    private EmployeeAdapter employeeAdapter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        employeeAdapter = new EmployeeAdapter();
        employeeRepository = new EmployeeRepository();
    }

    @Test
    public void onCreateViewHolderTest() {

    }

    @Test
    public void onBindViewHolderTest() {

        employeeRepository.getMutableLiveDatum();
        when(datum.getEmployeeAge()).thenReturn(String.valueOf(anyInt()));

    }

    @Test
    public void getItemCount() {
    }

    @Test
    public void setEmployeeDetail() {
    }

    @After
    public void tearDown() throws Exception {
    }
}