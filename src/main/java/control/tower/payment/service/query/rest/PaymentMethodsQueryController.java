package control.tower.payment.service.query.rest;

import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.payment.service.core.data.PaymentMethodEntity;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodsQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<PaymentMethodRestModel> getPaymentMethods() {
        FindAllPaymentMethodsQuery findAllPaymentMethodsQuery = new FindAllPaymentMethodsQuery();

        List<PaymentMethodEntity> paymentMethodEntities = queryGateway.query(findAllPaymentMethodsQuery,
                ResponseTypes.multipleInstancesOf(PaymentMethodEntity.class)).join();

        return convertPaymentMethodEntitiesToPaymentMethodRestModels(paymentMethodEntities);
    }

    private List<PaymentMethodRestModel> convertPaymentMethodEntitiesToPaymentMethodRestModels(
            List<PaymentMethodEntity> paymentMethodEntities) {
        List<PaymentMethodRestModel> paymentMethodRestModels = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");

        for (PaymentMethodEntity paymentMethodEntity : paymentMethodEntities) {
            paymentMethodRestModels.add(new PaymentMethodRestModel(
                    paymentMethodEntity.getPaymentId(),
                    paymentMethodEntity.getUserId(),
                    paymentMethodEntity.getMaskedCardNumber(),
                    dateFormat.format(paymentMethodEntity.getExpirationDate())
            ));
        }

        return paymentMethodRestModels;
    }
}
