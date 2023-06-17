package ru.skypro.homework.services;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.*;

import java.security.Principal;

public interface AdsService {
    ResponseWrapperAdsDto getAllAds();
    AdsDto createAds(CreateAdsDto createAds, MultipartFile file, Authentication authentication);
    ResponseWrapperCommentDto getAdsComments(int pk);
    CommentDto addAdsComment(int pk, CommentDto adsCommentDto, String username);
    FullAdsDto getAds(int id);
    AdsDto removeAds(int id, Authentication authentication);
    AdsDto updateAds(int id, CreateAdsDto adsDto, Authentication authentication);
    CommentDto getAdsComment(int pk, int id);
    CommentDto deleteAdsComment(int pk, int id, Authentication authentication);
    CommentDto updateAdsComment(int pk, int id, CommentDto adsCommentDto, Authentication authentication);
    ResponseWrapperAdsDto getAdsMe(Principal principal);
    AdsDto uploadAdsImage( MultipartFile file, Integer id);

    ResponseWrapperAdsDto getAdsByTitle(String title);
}
