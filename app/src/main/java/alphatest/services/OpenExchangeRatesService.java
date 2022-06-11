package alphatest.services;

import alphatest.feignClients.OpenExchangeRatesClient;
import alphatest.models.OpenExchangeRatesModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OpenExchangeRatesService {
    
    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;
    
    @Value("${openExchangeRates.app_id}")
    private String openExchangeRatesAppId;
    
    public Map<String,Double> getRates(){
        
        OpenExchangeRatesModel courceObject = openExchangeRatesClient.getRates(this.openExchangeRatesAppId);
        return courceObject.rates;
    
    }
    
    public double getCource(LocalDate date, String code){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        OpenExchangeRatesModel courceObject = openExchangeRatesClient.getCource(date.format(formatter), this.openExchangeRatesAppId);
        return courceObject.rates.get(code);
    
    }
    
    public double getChangeCource(String code){
    
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterdayDate = nowDate.minusDays(1);
        
        Double nowCource = getCource(nowDate, code);
        Double yesterdayCource = getCource(yesterdayDate, code);
        
        return nowCource - yesterdayCource;
    
    }
    
}
