package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.models.Ads;

import java.util.Collection;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression ="java(ads.getImage().getFilePath())" )
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
    AdsDto adsToAdsDto(Ads ads);

    @InheritInverseConfiguration
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    Ads adsDtoToAds(AdsDto adsDto);

    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}
