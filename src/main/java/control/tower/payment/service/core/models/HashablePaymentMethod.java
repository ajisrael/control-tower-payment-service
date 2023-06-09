package control.tower.payment.service.core.models;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@NoArgsConstructor
public class HashablePaymentMethod {

    private String userId;
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;

    public String getCombinedValues() {
        return userId + cardNumber + expirationDate.toString() + securityCode;
    }
}
