package com.example.testinglist.adapter;

import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.testinglist.adapter.EmployeeAdapter.EmployeeHolder;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeAdapterTest {
    @Mock
    private Context context;
    @Mock
    private List<Datum> list;
    @Spy
    EmployeeRepository employeeRepository;
    @Mock
    private Datum datum;
    @Mock
    private Adapter adapter;
    @Mock
    private RecyclerView.ViewHolder viewHolder;

    @Mock
    private EmployeeAdapter employeeAdapter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        employeeRepository = new EmployeeRepository();

        context = context.getApplicationContext();
    }

    @Test
    public void onCreateViewHolderTest() {

        LinearLayout linearLayout = new LinearLayout(context);
        adapter = new EmployeeAdapter();
        viewHolder = adapter.onCreateViewHolder(linearLayout, 24);

        assertFalse(viewHolder instanceof EmployeeHolder);
    }

    @Test
    public void onBindViewHolderTest() {

        employeeRepository.getMutableLiveDatum();
        when(datum.getEmployeeAge()).thenReturn("Tiger Nixon");
        assertEquals("Tiger Nixon",datum.getEmployeeAge());
    }

    @Test
    public void getItemCount() {
        int initialExpected = 24;
        int initialActual = employeeAdapter.getItemCount();

        assertEquals(initialExpected, initialActual);
    }


    @After
    public void tearDown() throws Exception {
    }
}