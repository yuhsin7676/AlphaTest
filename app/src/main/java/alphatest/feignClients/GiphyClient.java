package alphatest.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="giphyClient", url="${giphy.url}")
public interface GiphyClient {
    
    @GetMapping("/random")
    String getGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
    
}
