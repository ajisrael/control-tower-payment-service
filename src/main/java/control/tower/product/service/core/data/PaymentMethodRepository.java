package control.tower.product.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, String> {

    PaymentMethodEntity findByPaymentId(String paymentId);
}
