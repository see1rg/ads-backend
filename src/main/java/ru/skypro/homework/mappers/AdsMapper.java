package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.AdsDtoFull;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Image;
import ru.skypro.homework.models.User;
import ru.skypro.homework.repositories.ImageRepository;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AdsMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(getImage(ads))")
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
    AdsDto adsToAdsDto(Ads ads);

    default String getImage(Ads ads) {
        if (ads.getImage() == null) {
            return null;
        }
        return "/ads/" + ads.getId() + "/getImage";
    }

    @InheritInverseConfiguration
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    Ads adsDtoToAds(AdsDto adsDto);
    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}
