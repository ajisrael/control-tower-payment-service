package control.tower.payment.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

import static control.tower.core.utils.Helper.*;
import static control.tower.core.constants.ExceptionMessages.PAYMENT_ID_CANNOT_BE_EMPTY;
import static control.tower.payment.service.core.constants.ExceptionMessages.*;

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
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getCardNumber(), CARD_NUMBER_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsNull(this.getExpirationDate(), EXPIRATION_DATE_CANNOT_BE_NULL);
        throwExceptionIfParameterIsEmpty(this.getSecurityCode(), SECURITY_CODE_CANNOT_BE_EMPTY);
    }
}
