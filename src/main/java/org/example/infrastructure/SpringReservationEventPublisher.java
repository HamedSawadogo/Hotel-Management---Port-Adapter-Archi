package org.example.infrastructure;

import org.example.domain.events.BaseEvent;
import org.example.domain.ports.out.eventpublisher.ReservationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class SpringReservationEventPublisher  implements ReservationEventPublisher, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public SpringReservationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
       this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(BaseEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
