package control.tower.payment.service.command;

import control.tower.payment.service.command.commands.CreatePaymentMethodCommand;
import control.tower.payment.service.command.commands.RemovePaymentMethodCommand;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import control.tower.payment.service.core.events.PaymentMethodRemovedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
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
        PaymentMethodCreatedEvent event = PaymentMethodCreatedEvent.builder()
                .paymentId(command.getPaymentId())
                .userId(command.getUserId())
                .cardNumber(command.getCardNumber())
                .expirationDate(command.getExpirationDate())
                .securityCode(command.getSecurityCode())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemovePaymentMethodCommand command) {
        PaymentMethodRemovedEvent event = PaymentMethodRemovedEvent.builder()
                .paymentId(command.getPaymentId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PaymentMethodCreatedEvent event) {
        this.paymentId = event.getPaymentId();
        this.userId = event.getUserId();
        this.expirationDate = event.getExpirationDate();
    }

    @EventSourcingHandler
    public void on(PaymentMethodRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
