package eapli.base.usermanagement.application.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.clientusermanagement.domain.events.SignupAcceptedEvent;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.eventpubsub.EventHandler;

public class SignupAcceptedWatchDog implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupAcceptedWatchDog.class);

    @Override
    public void onEvent(final DomainEvent domainevent) {
        assert domainevent instanceof SignupAcceptedEvent;

        final SignupAcceptedEvent event = (SignupAcceptedEvent) domainevent;

        final AddUserOnSignupAcceptedController controller = new AddUserOnSignupAcceptedController();
        try {
            controller.addUser(event);
        } catch (final IntegrityViolationException e) {
            // TODO provably should send some warning email...
            LOGGER.error("Unable to register new user on signup event", e);
        }
    }
}
