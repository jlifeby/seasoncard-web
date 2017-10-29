package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.MediaData;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.BadRequestException;
import com.jlife.abon.service.MediaService;
import com.jlife.abon.utility.CropProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Khralovich Dzmitry
 */
@RestController
public class ImageController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private MediaService mediaService;

    @Value("${uploaded.image.path}")
    protected String uploadedImagePath;

    @RequestMapping(value = "/api/upload/image/save", method = RequestMethod.POST, produces = "application/json")
    public MediaData saveImage(@RequestParam(value = "image", required = false) MultipartFile image) {
        if (image != null) {
            if (image.getSize() != 0) {
                try {
                    MediaData media = mediaService.saveOriginImageAsMedia(image);
                    return media;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new BadRequestException(ApiErrorCode.GENERAL_CODE);
    }

    @RequestMapping(value = "/api/upload/image/crop", method = RequestMethod.POST, produces = "application/json")
    public MediaData cropImage(@ModelAttribute("cropProperty") CropProperty cropProperty,
                               @ModelAttribute("logoMediaId") String logoMediaId,
                               @RequestParam(value = "maxHeight", defaultValue = "640") int maxHeight,
                               @RequestParam(value = "maxWidth", defaultValue = "640") int maxWidth) {
        try {
            MediaData media = mediaService.findOne(logoMediaId);
            media.setCropProperty(cropProperty);
            mediaService.resizeOriginImage(media, maxWidth, maxHeight);
            return media;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}