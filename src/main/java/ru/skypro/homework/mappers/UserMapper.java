package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.models.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto userToUserDto(User user);

    List<UserDto> userToUserDto(List<User> userList);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "adsList", ignore = true)
    @Mapping(target = "commentList", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    List<User> userDtoToUser(List<UserDto> userDto);

}
