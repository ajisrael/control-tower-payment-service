package control.tower.payment.service.query.rest;

import control.tower.payment.service.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.payment.service.query.querymodels.PaymentMethodQueryModel;
import control.tower.payment.service.query.queries.FindPaymentMethodQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodsQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<PaymentMethodQueryModel> getAllPaymentMethods() {
        return queryGateway.query(new FindAllPaymentMethodsQuery(),
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();
    }

    @GetMapping(params = "paymentId")
    public PaymentMethodQueryModel getPaymentMethod(String paymentId) {
        return queryGateway.query(new FindPaymentMethodQuery(paymentId),
                ResponseTypes.instanceOf(PaymentMethodQueryModel.class)).join();
    }

    @GetMapping(params = "userId")
    public List<PaymentMethodQueryModel> getAllPaymentMethodsForUser(String userId) {
        return queryGateway.query(new FindAllPaymentMethodsForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();
    }
}
