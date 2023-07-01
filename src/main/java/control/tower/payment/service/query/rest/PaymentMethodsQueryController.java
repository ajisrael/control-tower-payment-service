package control.tower.payment.service.query.rest;

import control.tower.core.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.core.query.querymodels.PaymentMethodQueryModel;
import control.tower.payment.service.query.queries.FindPaymentMethodQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymentmethods")
@Tag(name = "Payment Query API")
public class PaymentMethodsQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all payment methods")
    public List<PaymentMethodQueryModel> getAllPaymentMethods() {
        return queryGateway.query(new FindAllPaymentMethodsQuery(),
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();
    }

    @GetMapping(params = "paymentId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get payment method by id")
    public PaymentMethodQueryModel getPaymentMethod(String paymentId) {
        return queryGateway.query(new FindPaymentMethodQuery(paymentId),
                ResponseTypes.instanceOf(PaymentMethodQueryModel.class)).join();
    }

    @GetMapping(params = "userId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all payment methods for user by id")
    public List<PaymentMethodQueryModel> getAllPaymentMethodsForUser(String userId) {
        return queryGateway.query(new FindAllPaymentMethodsForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();
    }
}
