package alphatest.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import alphatest.models.OpenExchangeRatesModel;

@FeignClient(name="openExchangeRatesClient", url="${openExchangeRates.url}")
public interface OpenExchangeRatesClient {
    
    @GetMapping("/historical/{date}.json")
    OpenExchangeRatesModel getCource(
            @PathVariable String date,
            @RequestParam("app_id") String app_id
    );
    
}
