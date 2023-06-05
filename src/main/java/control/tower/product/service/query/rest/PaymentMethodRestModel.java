package control.tower.product.service.query.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentMethodRestModel {

    private String paymentId;
    private String userId;
    private String cardNumber;
    private String expirationDate;
}
