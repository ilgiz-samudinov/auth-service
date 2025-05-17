package example.com.authservice.kafka;

import example.com.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage (String topic, User user) {
        kafkaTemplate.send(topic, user);
    }
}
