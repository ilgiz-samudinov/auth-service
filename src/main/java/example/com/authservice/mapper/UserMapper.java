package example.com.authservice.mapper;

import example.com.authservice.dto.UserRequest;
import example.com.authservice.dto.UserResponse;
import example.com.authservice.exception.ValidationException;
import example.com.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity (UserRequest userRequest);
    UserResponse toResponse (User user);


}
