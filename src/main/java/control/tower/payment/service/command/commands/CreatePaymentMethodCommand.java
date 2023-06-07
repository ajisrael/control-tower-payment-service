package control.tower.payment.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class CreatePaymentMethodCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String userId;
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;

    public void validate() {
        if (isNullOrBlank(this.getPaymentId())) {
            throw new IllegalArgumentException("Payment id cannot be empty");
        }

        if (isNullOrBlank(this.getUserId())) {
            throw new IllegalArgumentException("User id cannot be empty");
        }

        if (isNullOrBlank(this.getCardNumber())) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }

        if (this.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }

        if (isNullOrBlank(this.getSecurityCode())) {
            throw new IllegalArgumentException("Security code cannot be empty");
        }
    }
}
