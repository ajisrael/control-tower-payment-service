package control.tower.product.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodLookupRepository extends JpaRepository<PaymentMethodLookupEntity, String> {

    PaymentMethodLookupEntity findByPaymentId(String paymentId);

    PaymentMethodLookupEntity findByPaymentIdOrCardNumber(String paymentId, String cardNumber);
}
