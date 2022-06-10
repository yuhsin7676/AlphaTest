package alphatest.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import alphatest.feignClients.GiphyClient;
import alphatest.feignClients.OpenExchangeRatesClient;
import alphatest.models.OpenExchangeRatesModel;
import alphatest.models.ResponseGifModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.stereotype.Service;

@RestController
@RequestMapping("/demo")
public class Controller{
    
    @Autowired
    private GiphyClient giphyClient;
    
    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;
    
    @Value("${giphy.api_key}")
    private String giphyApiKey;
    
    @Value("${openExchangeRates.app_id}")
    private String openExchangeRatesAppId;
    
    @Value("${giphy.rich}")
    private String richTag;
    
    @Value("${giphy.broke}")
    private String brokeTag;
    
    @GetMapping("/getGif/{code}")
    public ResponseGifModel getGif(@PathVariable String code){
        
        // ??????? ?????? responseGifModel, ?????????? ????? ?? ??????? Giphy  
        ResponseGifModel response = new ResponseGifModel();
        
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = nowDate.minusDays(1);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // ??????? ?????????? ? ?????? ?? ??????????? ? ????????? ????
        OpenExchangeRatesModel nowCourceObject = openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId);
        OpenExchangeRatesModel yesterdayCourceObject = openExchangeRatesClient.getCource(yesterdayDate.format(formatter), this.openExchangeRatesAppId);
        
        // ?????? ??????? ????? ??????????? ? ????????? ?????? ??????? ? ?????? {code}
        Double nowCource = nowCourceObject.rates.get(code);
        Double yesterdayCource = yesterdayCourceObject.rates.get(code);
        Double diff = nowCource - yesterdayCource;
        
        // ???????? ???, ?? ???????? ????? ?????????? ?????, ? ????, ??????? ???????????? ??????? ?? ?????
        String tag;
        if(diff > 0){
            response.key = 1;
            tag = this.richTag;
        }
        else{
            response.key = -1;
            tag = this.brokeTag;
        }
        
        // ?????????? ?????? ?? ???????? ????? Feign-??????
        response.gifResponse = this.giphyClient.getGif(this.giphyApiKey, tag);
        return response;
        
    }
    
    @GetMapping("/getCodes")
    public Map<String, Double> getCodes(){
        
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // ??????? ?????????? ? ?????? ?? ??????????? ????
        OpenExchangeRatesModel nowCourceObject = openExchangeRatesClient.getCource(nowDate.format(formatter), this.openExchangeRatesAppId);
        
        // ?????? ?????? ????? ? ?? ???? ? ???????
        return nowCourceObject.rates;
        
    }
    
}
