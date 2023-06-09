package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.models.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "image", expression = "java(user.getAvatar() != null ? user.getAvatar().getFilePath() : null)")
    UserDto userToUserDto(User user);

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    User userDtoToUser(UserDto userDto);

}
