package control.tower.payment.service.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import static control.tower.core.constants.DomainConstants.API_GATEWAY_URL;

public class WebClientService {

    private static WebClient.Builder webClientBuilder = WebClient.builder();
    private static final WebClient webClient = webClientBuilder.baseUrl(API_GATEWAY_URL).build();

    private WebClientService() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean doesUserExist(String userId){
        ClientResponse response = webClient.get()
                .uri("user-service/users?userId={userId}", userId)
                .exchange()
                .block();

        return response.statusCode() == HttpStatus.OK;
    }

}
