package control.tower.payment.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodLookupRepository extends JpaRepository<PaymentMethodLookupEntity, String> {

    PaymentMethodLookupEntity findByPaymentId(String paymentId);

    PaymentMethodLookupEntity findByPaymentMethodHash(String paymentMethodHash);
}
