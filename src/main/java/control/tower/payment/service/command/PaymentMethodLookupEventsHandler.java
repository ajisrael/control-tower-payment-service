package control.tower.payment.service.command;

import control.tower.payment.service.core.data.PaymentMethodLookupEntity;
import control.tower.payment.service.core.data.PaymentMethodLookupRepository;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ProcessingGroup("payment-group")
public class PaymentMethodLookupEventsHandler {

    private PaymentMethodLookupRepository paymentMethodLookupRepository;

    @EventHandler
    public void on(PaymentMethodCreatedEvent event) {
        paymentMethodLookupRepository.save(
                new PaymentMethodLookupEntity(
                        event.getPaymentId(), event.getCardNumber()
                )
        );
    }
}
