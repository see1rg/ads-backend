package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.models.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    @Mapping(target = "image", expression = "java(getImage(user))")
    UserDto userToUserDto(User user);

    default String getImage(User user) {
        if (user.getAvatar() == null) {
            return null;
        }
        return "/users/" + user.getId() + "/getImage";
    }

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountExpired", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "email" , source = "username")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountExpired", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User registerReqToUser(RegisterReq registerReq);

}
