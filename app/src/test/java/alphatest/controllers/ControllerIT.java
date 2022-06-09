/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package alphatest.controllers;

import alphatest.models.ResponseGifModel;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ilya
 */
public class ControllerIT {
    
    public ControllerIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getGif method, of class Controller.
     */
    @Test
    public void testGetGif() {
        System.out.println("getGif");
        String code = "";
        Controller instance = new Controller();
        ResponseGifModel expResult = null;
        ResponseGifModel result = instance.getGif(code);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCodes method, of class Controller.
     */
    @Test
    public void testGetCodes() {
        System.out.println("getCodes");
        Controller instance = new Controller();
        Map<String, Double> expResult = null;
        Map<String, Double> result = instance.getCodes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
