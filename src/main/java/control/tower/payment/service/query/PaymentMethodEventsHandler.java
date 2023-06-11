package control.tower.payment.service.query;

import control.tower.payment.service.core.data.PaymentMethodEntity;
import control.tower.payment.service.core.data.PaymentMethodRepository;
import control.tower.payment.service.core.events.PaymentMethodCreatedEvent;
import control.tower.payment.service.core.events.PaymentMethodRemovedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST;

@Component
@ProcessingGroup("payment-group")
public class PaymentMethodEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodEventsHandler.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodEventsHandler(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        LOGGER.error(exception.getLocalizedMessage());
    }

    @EventHandler
    public void on(PaymentMethodCreatedEvent event) {
        PaymentMethodEntity paymentMethodEntity = new PaymentMethodEntity();
        BeanUtils.copyProperties(event, paymentMethodEntity);
        paymentMethodRepository.save(paymentMethodEntity);
    }

    @EventHandler
    public void on(PaymentMethodRemovedEvent event) {
        PaymentMethodEntity paymentMethodEntity =
                paymentMethodRepository.findByPaymentId(event.getPaymentId());

        throwExceptionIfEntityDoesNotExist(paymentMethodEntity,
                String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, event.getPaymentId()));

        paymentMethodRepository.delete(paymentMethodEntity);
    }
}
