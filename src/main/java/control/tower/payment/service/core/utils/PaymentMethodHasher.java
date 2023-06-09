package control.tower.payment.service.core.utils;

import control.tower.payment.service.core.models.HashablePaymentMethod;
import org.springframework.beans.BeanUtils;

import static control.tower.core.utils.Helper.calculateSHA256Hash;

public class PaymentMethodHasher {
    public static String createPaymentMethodHash(Object address) {
        HashablePaymentMethod hashableAddress = new HashablePaymentMethod();
        BeanUtils.copyProperties(address, hashableAddress);
        return calculateSHA256Hash(hashableAddress.getCombinedValues());
    }
}
