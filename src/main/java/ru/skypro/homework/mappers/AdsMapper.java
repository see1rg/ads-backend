package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.AdsDtoFull;
import ru.skypro.homework.models.Ads;

import java.util.Collection;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AdsMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(getImage(ads))")
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(target = "authorFirstName", source = "authorId.firstName")
    @Mapping(target = "authorLastName", source = "authorId.lastName")
    @Mapping(target = "email", source = "authorId.email")
    @Mapping(target = "image", expression = "java(getImage(ads))")
    @Mapping(target = "phone", source = "authorId.phone")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "description" , source = "description" )
    AdsDtoFull adsToAdsDtoFull(Ads ads);

    default String getImage(Ads ads) {
        if (ads.getImage() == null) {
            return null;
        }
        return "/ads/" + ads.getId() + "/getImage";
    }

    @InheritInverseConfiguration
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId.id", source = "author")
    Ads adsDtoToAds(AdsDto adsDto);

    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}
