package control.tower.payment.service.command;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

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
        command.validate();

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


}
