package control.tower.product.service.query;

import control.tower.product.service.core.data.PaymentMethodEntity;
import control.tower.product.service.core.data.PaymentMethodRepository;
import control.tower.product.service.core.events.PaymentMethodCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
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
}
