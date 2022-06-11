package alphatest.services;

import alphatest.feignClients.GiphyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiphyService{
    
    @Autowired
    private GiphyClient giphyClient;
    
    @Value("${giphy.api_key}")
    private String giphyApiKey;
    
    public String getGif(String tag){

        String response = this.giphyClient.getGif(this.giphyApiKey, tag);
        return response;
        
    }
    
}
