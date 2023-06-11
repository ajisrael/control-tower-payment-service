package control.tower.payment.service.query;

import control.tower.payment.service.core.data.PaymentMethodEntity;
import control.tower.payment.service.core.data.PaymentMethodRepository;
import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.payment.service.query.queries.FindPaymentMethodQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class PaymentMethodsQueryHandler {

    private final PaymentMethodRepository paymentMethodRepository;

    @QueryHandler
    public List<PaymentMethodEntity> findAllPaymentMethods(FindAllPaymentMethodsQuery query) {
        return paymentMethodRepository.findAll();
    }

    @QueryHandler
    public PaymentMethodEntity findPaymentMethod(FindPaymentMethodQuery query) {
        return paymentMethodRepository.findById(query.getPaymentId()).orElseThrow(
                () -> new IllegalStateException(String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, query.getPaymentId())));
    }
}
