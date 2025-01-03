package br.com.fiap.microservice_payment.service.impl;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.service.PaymentService;
import br.com.fiap.microservice_payment.util.Constants;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentClient client;

    public PaymentServiceImpl(@Value("${com.mercadopago.token}") String token) {
        MercadoPagoConfig.setAccessToken(token);
        this.client = new PaymentClient();
    }

    public void createPayment(PaymentDto paymentDto) throws MPException, MPApiException {
        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(paymentDto.getAmount())
                .installments(Constants.PAYMENT_INSTALLMENTS)
                .paymentMethodId(Constants.PAYMENT_METHOD_PIX)
                .description(Constants.PAYMENT_DESCRIPTION)
                .payer(PaymentPayerRequest.builder()
                        .email(paymentDto.getClientEmail())
                        .build())
                .build();

        client.create(createRequest);
    }

    public void webhookHandle(WebhookDto webhookDto) throws MPException, MPApiException {
        System.out.println(webhookDto);
        PaymentClient client = new PaymentClient();

        System.out.println("------------> " + webhookDto.getData().getId());
        if(webhookDto.getAction().equals("payment.updated")) {
            Payment pay = client.get(webhookDto.getData().getId());
            System.out.println(pay.getStatus());
            System.out.println(pay.getStatusDetail());
        }
    }

}
