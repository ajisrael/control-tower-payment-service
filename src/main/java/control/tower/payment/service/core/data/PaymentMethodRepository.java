package control.tower.payment.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, String> {

    PaymentMethodEntity findByPaymentId(String paymentId);

    List<PaymentMethodEntity> findByUserId(String userId);
}
