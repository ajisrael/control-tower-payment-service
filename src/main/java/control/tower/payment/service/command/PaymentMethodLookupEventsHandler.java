package control.tower.payment.service.command;

import control.tower.payment.service.core.data.PaymentMethodLookupEntity;
import control.tower.payment.service.core.data.PaymentMethodLookupRepository;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import control.tower.payment.service.core.events.PaymentMethodRemovedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST;
import static control.tower.payment.service.core.utils.PaymentMethodHasher.createPaymentMethodHash;

@Component
@AllArgsConstructor
@ProcessingGroup("payment-group")
public class PaymentMethodLookupEventsHandler {

    private PaymentMethodLookupRepository paymentMethodLookupRepository;

    @EventHandler
    public void on(PaymentMethodCreatedEvent event) {
        paymentMethodLookupRepository.save(
                new PaymentMethodLookupEntity(event.getPaymentId(), createPaymentMethodHash(event))
        );
    }

    @EventHandler
    public void on(PaymentMethodRemovedEvent event) {
        PaymentMethodLookupEntity paymentMethodLookupEntity =
                paymentMethodLookupRepository.findByPaymentId(event.getPaymentId());

        throwExceptionIfEntityDoesNotExist(paymentMethodLookupEntity,
                String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, event.getPaymentId()));

        paymentMethodLookupRepository.delete(paymentMethodLookupEntity);
    }
}
