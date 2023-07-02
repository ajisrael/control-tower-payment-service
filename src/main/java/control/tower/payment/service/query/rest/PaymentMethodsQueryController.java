package control.tower.payment.service.query.rest;

import control.tower.core.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.core.rest.PageResponseType;
import control.tower.core.rest.PaginationResponse;
import control.tower.core.utils.PaginationUtility;
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
import java.util.concurrent.CompletableFuture;

import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE;
import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE_SIZE;

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
    public CompletableFuture<PaginationResponse<PaymentMethodQueryModel>> getAllPaymentMethods(
            @RequestParam(defaultValue = DEFAULT_PAGE) int currentPage,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        FindAllPaymentMethodsQuery findAllPaymentMethodsQuery = FindAllPaymentMethodsQuery.builder()
                .pageable(PaginationUtility.buildPageable(currentPage, pageSize))
                .build();

        return queryGateway.query(findAllPaymentMethodsQuery, new PageResponseType<>(PaymentMethodQueryModel.class))
                .thenApply(PaginationUtility::toPageResponse);
    }

    @GetMapping(params = "paymentId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get payment method by id")
    public CompletableFuture<PaymentMethodQueryModel> getPaymentMethod(String paymentId) {
        return queryGateway.query(new FindPaymentMethodQuery(paymentId),
                ResponseTypes.instanceOf(PaymentMethodQueryModel.class));
    }

    @GetMapping(params = "userId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all payment methods for user by id")
    public CompletableFuture<PaginationResponse<PaymentMethodQueryModel>> getAllPaymentMethodsForUser(
            String userId,
            @RequestParam(defaultValue = DEFAULT_PAGE) int currentPage,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        FindAllPaymentMethodsForUserQuery findAllPaymentMethodsForUserQuery = FindAllPaymentMethodsForUserQuery.builder()
                .userId(userId)
                .pageable(PaginationUtility.buildPageable(currentPage, pageSize))
                .build();

        return queryGateway.query(findAllPaymentMethodsForUserQuery, new PageResponseType<>(PaymentMethodQueryModel.class))
                .thenApply(PaginationUtility::toPageResponse);
    }
}
