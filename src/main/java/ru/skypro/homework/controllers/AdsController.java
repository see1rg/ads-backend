package ru.skypro.homework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.services.impl.ImageServiceImpl;

import java.security.Principal;
import java.util.Collections;


@Slf4j
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdsController {

    private final AdsService adsService;

    public AdsController(AdsService adsService, ImageServiceImpl imageServiceImpl) {
        this.adsService = adsService;
    }

      /**
     * Получить все существующие объявления GET <a href="http://localhost:3000/ads">...</a>
     **/

    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        ResponseWrapperAdsDto allAds = adsService.getAllAds();
            if (allAds.getCount() == 0) {
                allAds.setResults(Collections.emptyList());
            }
    return ResponseEntity.ok(allAds);
    }

    /**
     * POST <a href="http://localhost:3000/ads">...</a>
     * Добавление объявления.
     */

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<AdsDto> addAds(@RequestPart("properties") CreateAdsDto createAds,
                                         @RequestPart("image") MultipartFile file) {
        if (createAds == null) {
            return ResponseEntity.badRequest().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok(adsService.createAds(createAds, file, authentication));
    }

    /**
     * Получить все комментарии(отзывы) к объявлению GET <a href="http://localhost:3000/ads/">...</a>{ad_pk}/comment
     **/

    @GetMapping(value = "/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(@PathVariable("ad_pk") int adPk) {
        ResponseWrapperCommentDto adsComment = adsService.getAdsComments(adPk);
        if (adsComment.getCount() == 0) {
            adsComment.setResults(Collections.emptyList());
        }
    return ResponseEntity.ok(adsComment);
    }
    /**
     * POST <a href="http://localhost:3000/ads">...</a>{ad_pk}/comment
     * Добавление комментария к объявлению.
     */

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<CommentDto> addComments(@PathVariable("ad_pk")  int adPk,
                                                      @RequestBody CommentDto comment) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CommentDto adsComment = adsService.addAdsComment(adPk, comment, authentication.getName());
            if (adsComment == null) {
                adsComment= new CommentDto();
            }
    return ResponseEntity.ok(adsComment);
    }
    /**
     * Получить объявление по его идентификатору, то-есть по id
     * GET <a href="http://localhost:3000/ads/">...</a>{id}
     **/

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable int id) {
        FullAdsDto fullAds = adsService.getAds(id);
        if (fullAds == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok(fullAds);
    }
    /**
     * Удалить объявление по его идентификатору, то-есть по id.
     * DELETE <a href="http://localhost:3000/ads/{">...</a>id}
     **/

    @DeleteMapping("/{id}")
    public ResponseEntity<AdsDto> removeAds(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdsDto adsDto = adsService.removeAds(id, authentication);
        if (adsDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok().build();
    }
    /** Редактировать объявление по его идентификатору,
     * PUT <a href="http://localhost:3000/ads/">...</a>{id}
     **/

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id, @RequestBody CreateAdsDto ads) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdsDto adsDto = adsService.updateAds(id, ads, authentication);
        if (ads == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok(adsDto);
    }
    /**
     * POST <a href="http://localhost:3000/ads">...</a>{ad_pk}/comment
     * Получение комментария к объявлению.
     */

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> getAdsComment(@PathVariable("ad_pk") int adPk, @PathVariable int id) {
        CommentDto adsCommentDto = adsService.getAdsComment(adPk, id);
        if (adsCommentDto == null) {
            adsCommentDto= new CommentDto();
        }
    return ResponseEntity.ok(adsCommentDto);
    }

    /**
     * DELETE <a href="http://localhost:3000/ads">...</a>{ad_pk}/comment/{id}
     * Удаление комментария к объявлению.
     */

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("ad_pk") int adPk,
                                                         @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentDto adsCommentDto = adsService.deleteAdsComment(adPk, id, authentication);
        if (adsCommentDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok().build();
    }

    /**
     * POST <a href="http://localhost:3000/ads/">...</a>{ad_pk}/comment
     * Обновление отзыва(комментария) к объявлению. Объявление должно существовать.
     */

    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> updateAdsComment(@PathVariable("ad_pk") int adPk,
                                                       @PathVariable int id,
                                                       @RequestBody CommentDto comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CommentDto adsComment = adsService.updateAdsComment(adPk, id, comment, authentication);
        if (adsComment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok(adsComment);
    }
    /**
     * Получить все объявления автора GET <a href="http://localhost:3000/ads">...</a>
     **/

     @GetMapping("/me")
     public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Principal principal) {
            ResponseWrapperAdsDto Ads = adsService.getAdsMe(principal);
            if (Ads.getCount() == 0) {
                Ads.setResults(Collections.emptyList());
            }
     return ResponseEntity.ok(Ads);
     }
    /**
     * обновление картинки пользователя
     */

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> uploadAdsImage(@RequestParam MultipartFile image, @PathVariable("id") int id) {
        AdsDto adsDto = adsService.uploadAdsImage(image, id);
        if (adsDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    return ResponseEntity.ok(adsDto);
    }
    /*
     * Поиск объявления по заголовку
     */
    @GetMapping("/title")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsByTitle(@RequestParam(required = false) String title) {
        ResponseWrapperAdsDto responseWrapperAds = adsService.getAdsByTitle(title);
        if (responseWrapperAds == null) {
            responseWrapperAds.setResults(Collections.emptyList());
        }
        return ResponseEntity.ok(responseWrapperAds);
    }
}
