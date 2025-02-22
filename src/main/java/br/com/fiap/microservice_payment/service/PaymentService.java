package br.com.fiap.microservice_payment.service;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface PaymentService {
    PaymentEntity getPayment(String paymentId);
    PaymentEntity createPayment(PaymentDto paymentDto) throws MPException, MPApiException, JsonProcessingException;
    boolean webhookHandle(WebhookDto webhookDto) throws MPException, MPApiException;
}
