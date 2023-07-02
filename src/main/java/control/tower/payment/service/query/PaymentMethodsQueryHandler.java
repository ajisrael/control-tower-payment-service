package control.tower.payment.service.query;

import control.tower.core.query.queries.DoesPaymentMethodExistForUserQuery;
import control.tower.core.utils.PaginationUtility;
import control.tower.payment.service.core.data.PaymentMethodEntity;
import control.tower.payment.service.core.data.PaymentMethodRepository;
import control.tower.core.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.payment.service.query.queries.FindAllPaymentMethodsQuery;
import control.tower.payment.service.query.queries.FindPaymentMethodQuery;
import control.tower.core.query.querymodels.PaymentMethodQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE;
import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE_SIZE;
import static control.tower.payment.service.core.constants.ExceptionMessages.NO_PAYMENT_METHODS_FOUND_FOR_USER_WITH_ID;
import static control.tower.payment.service.core.constants.ExceptionMessages.PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class PaymentMethodsQueryHandler {

    private final PaymentMethodRepository paymentMethodRepository;

    @QueryHandler
    public Page<PaymentMethodQueryModel> findAllPaymentMethods(FindAllPaymentMethodsQuery query) {
        return paymentMethodRepository.findAll(query.getPageable())
                .map(this::convertPaymentMethodEntityToPaymentMethodQueryModel);
    }

    @QueryHandler
    public PaymentMethodQueryModel findPaymentMethod(FindPaymentMethodQuery query) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(query.getPaymentId()).orElseThrow(
                () -> new IllegalStateException(String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, query.getPaymentId())));

        return convertPaymentMethodEntityToPaymentMethodQueryModel(paymentMethodEntity);
    }

    @QueryHandler
    public List<PaymentMethodQueryModel> findAllPaymentMethodsForUserAsList(FindAllPaymentMethodsForUserQuery query) {
        List<PaymentMethodEntity> paymentMethodEntities = paymentMethodRepository.findByUserId(query.getUserId());

        return convertPaymentMethodEntitiesToPaymentMethodQueryModels(paymentMethodEntities);
    }

    @QueryHandler
    public Page<PaymentMethodQueryModel> findAllPaymentMethodsForUserAsPage(FindAllPaymentMethodsForUserQuery query) {
        Pageable pageable = query.getPageable();

        if (pageable == null) {
            pageable = PaginationUtility.buildPageable(
                    Integer.parseInt(DEFAULT_PAGE), Integer.parseInt(DEFAULT_PAGE_SIZE));
        }

       return paymentMethodRepository.findByUserId(query.getUserId(), pageable)
               .map(this::convertPaymentMethodEntityToPaymentMethodQueryModel);
    }

    @QueryHandler
    public boolean doesPaymentMethodExistForUser(DoesPaymentMethodExistForUserQuery query) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(query.getPaymentId()).orElseThrow(
                () -> new IllegalArgumentException(String.format(PAYMENT_METHOD_WITH_ID_DOES_NOT_EXIST, query.getPaymentId())));

        return paymentMethodEntity.getUserId().equals(query.getUserid());
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
