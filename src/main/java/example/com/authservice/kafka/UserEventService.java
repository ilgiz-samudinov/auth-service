package example.com.authservice.kafka;

import example.com.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventService {
    private final UserEventRepository userEventRepository;
    private final UserEventMapper userEventMapper;



    public void save(User user) {
        UserEvent  userEvent = userEventMapper.toEvent(user);
        userEventRepository.save(userEvent);
    }
}
