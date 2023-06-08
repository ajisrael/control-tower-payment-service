package control.tower.payment.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class RemovePaymentMethodCommand {

    @TargetAggregateIdentifier
    private String paymentId;

    public void validate() {
        if (isNullOrBlank(this.getPaymentId())) {
            throw new IllegalArgumentException("Payment id cannot be empty");
        }
    }
}
