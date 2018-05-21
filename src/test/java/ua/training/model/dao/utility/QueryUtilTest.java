package ua.training.model.dao.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class QueryUtilTest {
    private String query;
    private String returnQuery;

    @Before
    public void setUp() {
        query = "SELECT * FROM dbswft.dish WHERE idUsers IN (?)";
        returnQuery = "SELECT * FROM dbswft.dish WHERE idUsers IN (?,?,?,?,?)";
    }

    @After
    public void tearDown() {
        query = null;
        returnQuery = null;
    }

    @Test
    public void addParamAccordingToArrSize() {
        query = QueryUtil.addParamAccordingToArrSize(query, 5);
        assertEquals(returnQuery, query);
    }
}