package control.tower.product.service.core.events;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class PaymentMethodCreatedEvent {

    private String paymentId;
    private String userId;
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;
}
