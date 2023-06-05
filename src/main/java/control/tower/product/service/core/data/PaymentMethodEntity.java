package control.tower.product.service.core.data;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="paymentmethod")
public class PaymentMethodEntity implements Serializable {

    private static final long serialVersionUID = 789654123987456323L;

    @Id
    @Column(unique = true)
    private String paymentId;
    private String userId;
    @Column(unique = true)
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;

    public String getMaskedCardNumber() {
        return maskStringExceptLastFour(cardNumber);
    }

    private String maskStringExceptLastFour(String input) {
        if (input == null || input.length() <= 4) {
            return input;
        }

        int length = input.length();
        StringBuilder maskedString = new StringBuilder();

        for (int i = 0; i < length - 4; i++) {
            maskedString.append("*");
        }

        maskedString.append(input.substring(length - 4));

        return maskedString.toString();
    }

}
