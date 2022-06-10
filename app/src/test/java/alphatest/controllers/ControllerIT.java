package alphatest.controllers;

import alphatest.feignClients.GiphyClient;
import alphatest.feignClients.OpenExchangeRatesClient;
import alphatest.models.ResponseGifModel;
import alphatest.models.OpenExchangeRatesModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
public class ControllerIT {
    
    @Value("${giphy.api_key}")
    private String giphyApiKey;
    
    @Value("${openExchangeRates.app_id}")
    private String openExchangeRatesAppId;
    
    @Value("${giphy.rich}")
    private String richTag;
    
    @Value("${giphy.broke}")
    private String brokeTag;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GiphyClient giphyClient;
    
    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClient;
    
    /**
     * Test of getGif method, of class Controller.
     */
    @Test
    public void testGetGif() throws Exception{
        
        Map<String, Double> rates = new HashMap<String, Double>();
        rates.put("test", 30.0000);
        
        OpenExchangeRatesModel openExchangeRatesModel = new OpenExchangeRatesModel();
        openExchangeRatesModel.base = "RUB";
        openExchangeRatesModel.rates = rates;
        
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        Controller controller = Mockito.mock(Controller.class);
        Mockito.when(openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModel);
        mockMvc.perform(get("/demo/getCodes")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.test").value(30.0000));
        
        
        
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
        //fail("The test case is a prototype.");
    }
    
}
