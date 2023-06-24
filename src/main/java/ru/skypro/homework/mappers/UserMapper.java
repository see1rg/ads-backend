package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
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
    User userDtoToUser(UserDto userDto);

}
