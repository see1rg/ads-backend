package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.models.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
// If we want to guarantee that we don't forget to map any target property, we can configure the unmappedTargetPolicy option on our mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(target= "image", source = "user", qualifiedByName = "ImageToUser")
//    UserDto userToUserDto(User user);
//
//    User userDtoToUser(UserDto userDto);
//
//    @Named("ImageToUser")
//    public User ImageToUser(UserDto userDto) {
//        User user = userService.getById(userDto.getUserId()).get();
//        user.setImageLink(userDto.getImage());
//        return user;
//    }
}
