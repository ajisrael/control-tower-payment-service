package control.tower.payment.service.command;

import control.tower.payment.service.core.data.PaymentMethodLookupEntity;
import control.tower.payment.service.core.data.PaymentMethodLookupRepository;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import control.tower.payment.service.core.events.PaymentMethodRemovedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@AllArgsConstructor
@ProcessingGroup("payment-group")
public class PaymentMethodLookupEventsHandler {

    private PaymentMethodLookupRepository paymentMethodLookupRepository;

    @EventHandler
    public void on(PaymentMethodCreatedEvent event) {
        paymentMethodLookupRepository.save(
                new PaymentMethodLookupEntity(event.getPaymentId(), event.getCardNumber())
        );
    }

    @EventHandler
    public void on(PaymentMethodRemovedEvent event) {
        PaymentMethodLookupEntity paymentMethodLookupEntity =
                paymentMethodLookupRepository.findByPaymentId(event.getPaymentId());

        throwErrorIfEntityDoesNotExist(
                paymentMethodLookupEntity,
                String.format("Payment method %s does not exist", event.getPaymentId())
        );

        paymentMethodLookupRepository.delete(paymentMethodLookupEntity);
    }
}
