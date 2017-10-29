package com.jlife.abon.service;

import com.jlife.abon.dto.MediaData;
import com.jlife.abon.util.ImageUtil;
import com.jlife.abon.util.ImageUtilException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Dzmitry Misiuk
 */
public interface MediaService {

    MediaData resizeOriginImage(MediaData media, int maxWidth, int maxHeight) throws ImageUtilException;

    MediaData saveOriginImageAsMedia(MultipartFile multipartFile) throws ImageUtilException;

    /**
     * Scales and save image.
     *
     * @param inputStream bytes of image
     * @param format      format
     * @param width       output width
     * @param height      output height
     * @return relative path to image
     */
    String saveImage(InputStream inputStream, ImageUtil.Format format, int width, int height) throws ImageUtilException;

    String saveImage(MultipartFile multipartFile, int width, int height) throws ImageUtilException;

    String getImageDir();


    MediaData saveImageAsMedia(MultipartFile multipartFile, int width, int height) throws ImageUtilException;

    MediaData findOne(String logoMediaId);
}
