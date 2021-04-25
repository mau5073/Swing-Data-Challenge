package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class dataFunctionsTests extends DemoApplicationTests{
    private dataFunctions functions  = new dataFunctions();

    @Test
    public void testData() throws Exception {
      double[][] data = functions.getData();
      assertEquals(data[0][0], 0.0);
      assertEquals(data[1275][6], -9.450898);
    }
    @Test
    public void testsearchContinuityAboveValue() throws Exception {
        int index = functions.searchContinuityAboveValue(1,1,1200,3,3);
        assertEquals(index, 887);

        index = functions.searchContinuityAboveValue(1,1,1200,50,1000);
        assertEquals(index, -1);
    }
    @Test
    public void testbackSearchContinuityWithinRange() throws Exception {
        int index = functions.backSearchContinuityWithinRange(6,800,50,-1,.3,3);
        assertEquals(index, 713);
        index = functions.backSearchContinuityWithinRange(6,1,1,50,.3,3);
        assertEquals(index, -1);
    }
    @Test
    public void testsearchContinuityAboveValueTwoSignals() throws Exception {
        int index = functions.searchContinuityAboveValueTwoSignals(2,5,50,1200,1,3,5);
        assertEquals(index, 765);

        index = functions.searchContinuityAboveValueTwoSignals(1,5,0,0,1,3,5);
        assertEquals(index, -1);
    }
    @Test
    public void testsearchMultiContinuityWithinRange() throws Exception {
        Map<Integer, Integer>  list = functions.searchMultiContinuityWithinRange(5,15,1200,0,.5,15);
        HashMap<Integer, Integer> list2 = new HashMap<Integer, Integer>();
        list2.put(193,218);list2.put(564,580);
        assertEquals(list, list2);
    }
}
