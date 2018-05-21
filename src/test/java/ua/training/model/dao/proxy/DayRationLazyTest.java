package ua.training.model.dao.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entity.RationComposition;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DayRationLazyTest {
    private DayRationLazy dayRation;
    private ArrayList<RationComposition> arrDR;

    @Before
    public void setUp() {
        arrDR = new ArrayList<>();
        dayRation = new DayRationLazy();
        dayRation.setId(1);
    }

    @After
    public void tearDown() {
        arrDR = null;
        dayRation = null;
    }

    @Test
    public void getCompositions() {
        assertTrue(arrDR.isEmpty());
        arrDR = dayRation.getCompositions();
        assertFalse(arrDR.isEmpty());
    }
}