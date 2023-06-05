package control.tower.product.service.query.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PaymentMethodRestModel {

    private String paymentId;
    private String userId;
    private String cardNumber;
    private Date expirationDate;
}
