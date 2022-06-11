package alphatest.controllers;

import alphatest.models.OpenExchangeRatesModel;
import alphatest.services.GiphyService;
import alphatest.services.OpenExchangeRatesService;
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
    
    @Value("${giphy.rich}")
    private String richTag;
    
    @Value("${giphy.broke}")
    private String brokeTag;
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GiphyService giphyService;
    
    @MockBean
    private OpenExchangeRatesService openExchangeRatesService;
    
    /*
    * Все тесты проверяют контроллеры при валидных id сервисов и наличии данных за последний день.
    * Приложение упадет, если эти условия не выполнены
    */
    
    @Test
    public void test_getGif_when_return_rich_gif() throws Exception{
        
        Mockito.when(openExchangeRatesService.getChangeCource("test"))
                .thenReturn(5.00);
        Mockito.when(giphyService.getGif(this.richTag))
                .thenReturn("richjson");
        mockMvc.perform(get("/demo/getGif/test")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.key").value(1));     
        
    }
    
    @Test
    public void test_getGif_when_return_broke_gif() throws Exception{
        
        Mockito.when(openExchangeRatesService.getChangeCource("test"))
                .thenReturn(-5.00);
        Mockito.when(giphyService.getGif(this.brokeTag))
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
        
        Mockito.when(openExchangeRatesService.getRates())
                .thenReturn(rates);
        mockMvc.perform(get("/demo/getRates")
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.test").value(30.0000));    
        
    }
    
}
