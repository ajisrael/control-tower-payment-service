package control.tower.payment.service.command.rest.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMethodCreatedResponse {

    private String paymentId;
}
