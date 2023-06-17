package ru.skypro.homework.services.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.models.Ads;
import ru.skypro.homework.models.Comment;
import ru.skypro.homework.models.User;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.exception.AdsCommentNotFoundException;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
@Service
public class AdsServiceImpl  implements AdsService {
    private final AdsRepository adsRepository;
    private final AdsMapper mapper = Mappers.getMapper(AdsMapper.class);
    private final CommentRepository adsCommentRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageServiceImpl imageServiceImpl;

    public AdsServiceImpl(AdsRepository adsRepository, CommentRepository adsCommentRepository, UserRepository userRepository, ImageServiceImpl imageServiceImpl,ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.adsCommentRepository = adsCommentRepository;
        this.userRepository = userRepository;
        this.imageServiceImpl = imageServiceImpl;
        this.imageRepository = imageRepository;
    }
    /**
     * Получение всех объявлений
     */
    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ads> adsList = adsRepository.findAll();
        return getResponseWrapperAds(adsList);
    }
    private ResponseWrapperAdsDto getResponseWrapperAds(List<Ads> adsList) {
        List<AdsDto> adsDtoList = mapper.adsToAdsDto(adsList);
        ResponseWrapperAdsDto responseWrapperAds = new ResponseWrapperAdsDto();
        responseWrapperAds.setCount(adsDtoList.size());
        responseWrapperAds.setResults(adsDtoList);
        return responseWrapperAds;
    }
    /**
     * Создание объявления
     */
    @Override
    public AdsDto createAds(CreateAdsDto createAds, MultipartFile file, Authentication authentication) {
        Ads ads = mapper.createAdsToAds(createAds);
        ads.setAuthor(userRepository.findUserByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new));
        ads.setImage("/image/" + imageServiceImpl.saveImage(file));
        adsRepository.save(ads);
        return mapper.adsToAdsDto(ads);
    }
    /**
     * Получение списка комментариев к объявлению
     */
    @Override
    public ResponseWrapperCommentDto getAdsComments(int pk) {
        Ads ads = adsRepository.findById(pk).orElseThrow(AdsNotFoundException::new);
        List<Comment> adsCommentList = adsCommentRepository.findByPk(ads);
        List<CommentDto> adsCommentDtoList = mapper.adsCommentToAdsCommentDto(adsCommentList);
        ResponseWrapperCommentDto responseWrapperAdsComment = new ResponseWrapperCommentDto();
        responseWrapperAdsComment.setCount(adsCommentDtoList.size());
        responseWrapperAdsComment.setResults(adsCommentDtoList);
        return responseWrapperAdsComment;
    }
    /**
     *Добавление комментария
     */
    @Override
    public CommentDto addAdsComment(int pk, CommentDto adsCommentDto, String username) {
        Comment adsComment = new Comment();
        adsComment.setAuthor(userRepository.findUserByEmail(username).orElseThrow(UserNotFoundException::new));
        adsComment.setPk(adsRepository.findById(pk).orElseThrow(AdsNotFoundException::new));
        adsComment.setCreatedAt(OffsetDateTime.now().toString());
        adsComment.setText(adsCommentDto.getText());
        adsCommentRepository.save(adsComment);
        return mapper.adsCommentToAdsCommentDto(adsComment);
    }
    /**
     * Получения объявления по номеру
     */
    @Override
    public FullAdsDto getAds(int id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        User user = userRepository.findById(ads.getAuthor().getId()).orElseThrow(UserNotFoundException::new);
        FullAdsDto fullAds = new FullAdsDto();
        fullAds.setAuthorFirstName(user.getFirstName());
        fullAds.setAuthorLastName(user.getLastName());
        fullAds.setDescription(ads.getDescription());
        fullAds.setImage(ads.getImage());
        fullAds.setEmail(user.getEmail());
        fullAds.setPhone(user.getPhone());
        fullAds.setPk(ads.getPk());
        fullAds.setPrice(ads.getPrice());
        fullAds.setTitle(ads.getTitle());
        return fullAds;
    }
    /**
     * Удаление объявления
     */
    @Override
    public AdsDto removeAds(int id, Authentication authentication) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (checkRole(ads, authentication)) {
            adsCommentRepository.deleteByAdsId(id);
            adsRepository.deleteById(id);
            String[] ls = ads.getImage().split("/");
            imageRepository.deleteById(ls[2]);
            return mapper.adsToAdsDto(ads);
        } else {
            throw new NoAccessException();
        }
    }
    /**
     * Обновление объявления
     */
    @Override
    public AdsDto updateAds(int id, CreateAdsDto adsDto, Authentication authentication) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        if (checkRole(ads, authentication)) {
            ads.setTitle(adsDto.getTitle());
            ads.setPrice(adsDto.getPrice());
            ads.setDescription(adsDto.getDescription());
            adsRepository.save(ads);
            AdsDto adsDtoRen = mapper.adsToAdsDto(ads);
            return adsDtoRen;
        } else {
            throw new NoAccessException();
        }
    }
    @Override
    public CommentDto getAdsComment(int pk, int id) {
        adsRepository.findById(pk).orElseThrow(AdsNotFoundException::new);
        Comment adsComment = adsCommentRepository.findById(id).orElseThrow(AdsCommentNotFoundException::new);
        return mapper.adsCommentToAdsCommentDto(adsComment);
    }
    /**
     * Удаление комментария
     */
    @Override
    public CommentDto deleteAdsComment(int pk, int id, Authentication authentication) {
        Comment adsComment = adsCommentRepository.findById(id).orElseThrow(AdsCommentNotFoundException::new);
        Ads ads = adsRepository.findById(pk).orElseThrow(AdsNotFoundException::new);
        if (checkRole(ads, authentication)) {
            adsCommentRepository.deleteById(id);
            return mapper.adsCommentToAdsCommentDto(adsComment);
        } else {
            throw new NoAccessException();
        }
    }
    /**
     * Обновление комментария
     */
    @Override
    public CommentDto updateAdsComment(int pk, int id, CommentDto adsCommentDto, Authentication authentication) {
        Comment adsComment = adsCommentRepository.findById(id).orElseThrow(AdsCommentNotFoundException::new);
        Ads ads = adsRepository.findById(pk).orElseThrow(AdsNotFoundException::new);
        if (checkRole(ads, authentication)) {
            adsComment.setAuthor(userRepository.findById(adsComment.getAuthor().getId()).orElseThrow(UserNotFoundException::new));
            adsComment.setPk(ads);
            adsComment.setText(adsCommentDto.getText());
            adsComment.setCreatedAt(OffsetDateTime.now().toString());
            adsCommentRepository.save(adsComment);
            return adsCommentDto;
        } else {
            throw new NoAccessException();
        }

    }
    /**
     * Обновление картинки объявления
     */
    public AdsDto uploadAdsImage( MultipartFile file, Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        ads.setImage("/image/" + imageServiceImpl.saveImage(file));
        adsRepository.save(ads);
        return mapper.adsToAdsDto(ads);
    }
    /**
     * Получения списка объявления пользователя
     */
    @Override
    public ResponseWrapperAdsDto getAdsMe(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        List<Ads> adsList = adsRepository.findAdsByAuthorOrderByPk(user);
        return getResponseWrapperAds(adsList);
    }
    private boolean checkRole(Ads ads, Authentication authentication){
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains("ADMIN"))
                || authentication.getName().equals(ads.getAuthor().getEmail())){
            return true;
        }
        return false;
    }
    /**
     * Поиск списка объявления по названию
     */
    @Override
    public ResponseWrapperAdsDto getAdsByTitle(String title) {
        List<Ads> adsList = adsRepository.findLikeTitle(title);
        return getResponseWrapperAds(adsList);
    }
}
