package control.tower.payment.service.query;

import control.tower.payment.service.core.data.PaymentMethodEntity;
import control.tower.payment.service.core.data.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PaymentMethodsQueryHandler {

    private final PaymentMethodRepository paymentMethodRepository;

    @QueryHandler
    public List<PaymentMethodEntity> findAllPaymentMethods(FindAllPaymentMethodsQuery query) {
        return paymentMethodRepository.findAll();
    }
}
