package control.tower.product.service.command;

import control.tower.product.service.core.events.PaymentMethodCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

import static control.tower.product.service.core.utils.Helper.isNullOrBlank;

@Aggregate
@NoArgsConstructor
@Getter
public class PaymentMethodAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String userId;
    private Date expirationDate;

    @CommandHandler
    public PaymentMethodAggregate(CreatePaymentMethodCommand command) {
        validateCreatePaymentMethodCommand(command);

        PaymentMethodCreatedEvent event = PaymentMethodCreatedEvent.builder()
                .paymentId(command.getPaymentId())
                .userId(command.getUserId())
                .cardNumber(command.getCardNumber())
                .expirationDate(command.getExpirationDate())
                .securityCode(command.getSecurityCode())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventHandler
    public void on(PaymentMethodCreatedEvent event) {
        this.paymentId = event.getPaymentId();
        this.userId = event.getUserId();
        this.expirationDate = event.getExpirationDate();
    }

    private void validateCreatePaymentMethodCommand(CreatePaymentMethodCommand command) {
        if (isNullOrBlank(command.getPaymentId())) {
            throw new IllegalArgumentException("Payment id cannot be empty");
        }

        if (isNullOrBlank(command.getUserId())) {
            throw new IllegalArgumentException("User id cannot be empty");
        }

        if (isNullOrBlank(command.getCardNumber())) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }

        if (command.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }

        if (isNullOrBlank(command.getSecurityCode())) {
            throw new IllegalArgumentException("Security code cannot be empty");
        }
    }
}
