package control.tower.payment.service;

import control.tower.core.config.XStreamConfig;
import control.tower.payment.service.command.interceptors.CreatePaymentMethodCommandInterceptor;
import control.tower.payment.service.command.interceptors.RemovePaymentMethodCommandInterceptor;
import control.tower.payment.service.core.errorhandling.PaymentServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Autowired
	public void registerPaymentCommandInterceptors(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreatePaymentMethodCommandInterceptor.class)
		);
		commandBus.registerDispatchInterceptor(
				context.getBean(RemovePaymentMethodCommandInterceptor.class)
		);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("payment-group",
				configuration -> new PaymentServiceEventsErrorHandler());
	}
}
