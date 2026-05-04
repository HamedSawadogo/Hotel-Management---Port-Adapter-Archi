package org.example.application.ports.in.message.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    @Override
    @EventListener
    public void paymentCompleted() {


    }

    @Override
    @EventListener
    public void paymentCancelled() {

    }
}
