package example.com.authservice.kafka;

import example.com.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEventMapper {

    @Mapping(target = "id", ignore = true)
    UserEvent toEvent(User  user);
}
