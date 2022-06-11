package alphatest.services;

import alphatest.feignClients.OpenExchangeRatesClient;
import alphatest.models.OpenExchangeRatesModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan("alphatest")
public class OpenExchangeRatesServiceIT {
    
    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;
    
    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClient;
    
    @Test
    public void testGetRates(){
        
        Map<String, Double> rates = new HashMap<String, Double>();
        rates.put("test", 30.0000);
        
        OpenExchangeRatesModel openExchangeRatesModel = new OpenExchangeRatesModel();
        openExchangeRatesModel.base = "RUB";
        openExchangeRatesModel.rates = rates;
        
        Mockito.when(openExchangeRatesClient.getRates(anyString()))
                .thenReturn(openExchangeRatesModel);
        Map<String, Double> result = openExchangeRatesService.getRates();
        assertEquals(rates, result);
        
    }

    @Test
    public void testGetCource() {
        
        LocalDate nowDate = LocalDate.now();
        
        Map<String, Double> rates = new HashMap<String, Double>();
        rates.put("test", 30.0000);
        
        OpenExchangeRatesModel openExchangeRatesModel = new OpenExchangeRatesModel();
        openExchangeRatesModel.base = "RUB";
        openExchangeRatesModel.rates = rates;
        
        Mockito.when(openExchangeRatesClient.getCource(anyString(), anyString()))
                .thenReturn(openExchangeRatesModel);
        Double result = openExchangeRatesService.getCource(nowDate, "test");
        assertEquals(30.0000, result);
    }

    @Test
    public void testGetChangeCource() {
        
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = nowDate.minusDays(1);
        
        // Создадим шпиона, в котором будет перезадано значение метода getCource()
        OpenExchangeRatesService oers = Mockito.spy(OpenExchangeRatesService.class);
        Mockito.doReturn(4.00).when(oers).getCource(nowDate, "test");
        Mockito.doReturn(3.00).when(oers).getCource(yesterdayDate, "test");
        
        Double result = oers.getChangeCource("test");
        assertEquals(1.00, result);
        
    }
    
}
