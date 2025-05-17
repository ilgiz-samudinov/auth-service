package example.com.authservice.kafka;

import example.com.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final  UserEventService userEventService;

    @KafkaListener(topics = "created-user")
    public void listen (User user) {
        System.out.println("user created: " + user.toString());
        userEventService.save(user);
    }
}
