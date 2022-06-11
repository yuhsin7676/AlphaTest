package alphatest.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import alphatest.services.GiphyService;
import alphatest.services.OpenExchangeRatesService;
import alphatest.models.OpenExchangeRatesModel;
import alphatest.models.ResponseGifModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/demo")
public class Controller{
    
    @Autowired
    private GiphyService giphyService;
    
    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;
    
    @Value("${giphy.rich}")
    private String richTag;
    
    @Value("${giphy.broke}")
    private String brokeTag;
    
    @GetMapping("/getGif/{code}")
    public ResponseGifModel getGif(@PathVariable String code){
        
        ResponseGifModel response = new ResponseGifModel();
        
        double diff = openExchangeRatesService.getChangeCource(code);
        
        String tag;
        if(diff > 0){
            response.key = 1;
            tag = this.richTag;
        }
        else{
            response.key = -1;
            tag = this.brokeTag;
        }
        
        // response.gifResponse - json-объект в виде строки
        response.gifResponse = this.giphyService.getGif(tag);
        return response;
        
    }
    
    @GetMapping("/getRates")
    public Map<String, Double> getRates() throws IOException{

        return openExchangeRatesService.getRates();
        
    }
    
}
