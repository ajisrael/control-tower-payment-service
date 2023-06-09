package control.tower.payment.service;

import control.tower.payment.service.command.interceptors.CreatePaymentMethodCommandInterceptor;
import control.tower.payment.service.core.errorhandling.PaymentServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Autowired
	public void registerCreatePaymentMethodCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreatePaymentMethodCommandInterceptor.class)
		);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("payment-group",
				configuration -> new PaymentServiceEventsErrorHandler());
	}
}
