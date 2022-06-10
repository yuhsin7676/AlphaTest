package alphatest.controllers;

import alphatest.feignClients.GiphyClient;
import alphatest.feignClients.OpenExchangeRatesClient;
import alphatest.models.OpenExchangeRatesModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

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
    
    /*
    * Все тесты проверяют контроллеры при валидных id сервисов и наличии данных за последний день.
    * Приложение упадет, если эти условия не выполнены
    */
    
    @Test
    public void test_getGif_when_return_rich_gif() throws Exception{
        
        Map<String, Double> yesterdayRates = new HashMap<String, Double>();
        Map<String, Double> nowRates = new HashMap<String, Double>();
        yesterdayRates.put("test", 30.0000);
        nowRates.put("test", 35.0000);
        
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = nowDate.minusDays(1);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        OpenExchangeRatesModel openExchangeRatesModelYesterday = new OpenExchangeRatesModel();
        openExchangeRatesModelYesterday.base = "RUB";
        openExchangeRatesModelYesterday.rates = yesterdayRates;
        
        OpenExchangeRatesModel openExchangeRatesModelNow = new OpenExchangeRatesModel();
        openExchangeRatesModelNow.base = "RUB";
        openExchangeRatesModelNow.rates = nowRates;
        
        Controller controller = Mockito.mock(Controller.class);
        Mockito.when(openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModelNow);
        Mockito.when(openExchangeRatesClient.getCource(yesterdayDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModelYesterday);
        Mockito.when(giphyClient.getGif(this.giphyApiKey, this.brokeTag))
                .thenReturn("richjson");
        mockMvc.perform(get("/demo/getGif/test")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.key").value(1));     
        
    }
    
    @Test
    public void test_getGif_when_return_broke_gif() throws Exception{
        
        Map<String, Double> yesterdayRates = new HashMap<String, Double>();
        Map<String, Double> nowRates = new HashMap<String, Double>();
        yesterdayRates.put("test", 35.0000);
        nowRates.put("test", 30.0000);
        
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = nowDate.minusDays(1);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        OpenExchangeRatesModel openExchangeRatesModelYesterday = new OpenExchangeRatesModel();
        openExchangeRatesModelYesterday.base = "RUB";
        openExchangeRatesModelYesterday.rates = yesterdayRates;
        
        OpenExchangeRatesModel openExchangeRatesModelNow = new OpenExchangeRatesModel();
        openExchangeRatesModelNow.base = "RUB";
        openExchangeRatesModelNow.rates = nowRates;
        
        Controller controller = Mockito.mock(Controller.class);
        Mockito.when(openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModelNow);
        Mockito.when(openExchangeRatesClient.getCource(yesterdayDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModelYesterday);
        Mockito.when(giphyClient.getGif(this.giphyApiKey, this.brokeTag))
                .thenReturn("brokejson");
        mockMvc.perform(get("/demo/getGif/test")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.key").value(-1));     
        
    }

    @Test
    public void test_getCodes_when_openExchangeRatesAppId_is_valid() throws Exception{
        
        Map<String, Double> rates = new HashMap<String, Double>();
        rates.put("test", 30.0000);
        
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        OpenExchangeRatesModel openExchangeRatesModel = new OpenExchangeRatesModel();
        openExchangeRatesModel.base = "RUB";
        openExchangeRatesModel.rates = rates;
        
        Controller controller = Mockito.mock(Controller.class);
        Mockito.when(openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId))
                .thenReturn(openExchangeRatesModel);
        mockMvc.perform(get("/demo/getCodes")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.test").value(30.0000));    
        
    }
    
}
