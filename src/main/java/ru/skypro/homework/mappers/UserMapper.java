package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.RegisterReq;
import ru.skypro.homework.dtos.SecurityUserDto;
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
        return "/users/" + user.getId() + "/image";
    }

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "registerReq.role")
    @Mapping(target = "email", source = "registerReq.username")
    @Mapping(target = "firstName", source = "registerReq.firstName")
    @Mapping(target = "lastName", source = "registerReq.lastName")
    @Mapping(target = "phone", source = "registerReq.phone")
    @Mapping(target = "password",source = "registerReq.password")
    User updateUserFromRegisterReq(RegisterReq registerReq, User user);

    SecurityUserDto toSecurityDto(User user);

}
