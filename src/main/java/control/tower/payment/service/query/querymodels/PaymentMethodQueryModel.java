package control.tower.payment.service.query.querymodels;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentMethodQueryModel {

    private String paymentId;
    private String userId;
    private String cardNumber;
    private String expirationDate;
}
