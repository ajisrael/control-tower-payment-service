package control.tower.product.service.command;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Getter
@Builder
public class CreatePaymentMethodCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String userId;
    private String cardNumber;
    private Date expirationDate;
    private String securityCode;
}
