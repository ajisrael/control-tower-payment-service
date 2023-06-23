package control.tower.payment.service.query;

import control.tower.payment.service.core.data.PaymentMethodEntity;
import control.tower.payment.service.core.data.PaymentMethodRepository;
import control.tower.core.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.payment.service.query.queries.FindPaymentMethodQuery;
import control.tower.core.query.querymodels.PaymentMethodQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class PaymentMethodsQueryHandler {

    private final PaymentMethodRepository paymentMethodRepository;

    @QueryHandler
    public List<PaymentMethodQueryModel> findAllPaymentMethods(FindAllPaymentMethodsQuery query) {
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findAll();

        return convertPaymentMethodEntitiesToPaymentMethodQueryModels(paymentMethodEntities);
    }

    @QueryHandler
    public PaymentMethodQueryModel findPaymentMethod(FindPaymentMethodQuery query) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(query.getPaymentId()).orElseThrow(
                () -> new IllegalStateException(String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, query.getPaymentId())));

        return convertPaymentMethodEntityToPaymentMethodQueryModel(paymentMethodEntity);
    }

    @QueryHandler
    public List<PaymentMethodQueryModel> findAllPaymentMethodsForUser(FindAllPaymentMethodsForUserQuery query) {
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findByUserId(query.getUserId());

        if (paymentMethodEntities.isEmpty()) {
            throw new IllegalArgumentException("No payment methods found for user: " + query.getUserId());
        }

        return convertPaymentMethodEntitiesToPaymentMethodQueryModels(paymentMethodEntities);
    }

    private List<PaymentMethodQueryModel> convertPaymentMethodEntitiesToPaymentMethodQueryModels(
            List<PaymentMethodEntity> paymentMethodEntities) {
        List<PaymentMethodQueryModel> paymentMethodQueryModels = new ArrayList<>();

        for (PaymentMethodEntity paymentMethodEntity : paymentMethodEntities) {
            paymentMethodQueryModels.add(convertPaymentMethodEntityToPaymentMethodQueryModel(paymentMethodEntity));
        }

        return paymentMethodQueryModels;
    }

    private PaymentMethodQueryModel convertPaymentMethodEntityToPaymentMethodQueryModel(PaymentMethodEntity paymentMethodEntity) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");

        return new PaymentMethodQueryModel(
                paymentMethodEntity.getPaymentId(),
                paymentMethodEntity.getUserId(),
                paymentMethodEntity.getMaskedCardNumber(),
                dateFormat.format(paymentMethodEntity.getExpirationDate())
        );
    }
}
