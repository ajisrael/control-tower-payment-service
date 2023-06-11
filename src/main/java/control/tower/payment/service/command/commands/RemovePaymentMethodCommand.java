package control.tower.payment.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;
import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_ID_CANNOT_BE_EMPTY;

@Getter
@Builder
public class RemovePaymentMethodCommand {

    @TargetAggregateIdentifier
    private String paymentId;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
    }
}
