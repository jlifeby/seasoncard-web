package com.jlife.abon.service.impl;

import com.jlife.abon.dto.MediaData;
import com.jlife.abon.entity.Media;
import com.jlife.abon.repository.MediaRepository;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.MediaService;
import com.jlife.abon.util.ImageUtil;
import com.jlife.abon.util.ImageUtilException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class MediaServiceImpl extends AbstractService implements MediaService {

    @Value("${imageDir}")
    protected String imageDir;

    @Value("${waterMark}")
    private String waterMark;

    @Value("${uploaded.image.path}")
    protected String uploadedImagePath;

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public MediaData resizeOriginImage(MediaData media, int maxWidth, int maxHeight) throws ImageUtilException {
        try {
            String resizedFilename = ImageUtil.resizeOriginImage(imageDir, media.getOriginFilename(), media.getCropProperty(), maxWidth, maxHeight);
            media.setFilename(resizedFilename);
            media.setRelativePath(uploadedImagePath + resizedFilename);
            Media savedMedia = mediaRepository.save(dataMapper.fromMediaData(media));
            return dataMapper.toMediaData(savedMedia);
        } catch (IOException e) {
            throw new RuntimeException("Error save image", e);
        }
    }

    @Override
    public MediaData saveOriginImageAsMedia(MultipartFile multipartFile) throws ImageUtilException {
        try {
            byte[] bytes = multipartFile.getBytes();
            String fileName = UUID.randomUUID().toString() + "." + getImageFormat(multipartFile);
            File file = new File(imageDir, fileName);
            IOUtils.write(bytes, new FileOutputStream(file));
            Media media = new Media();
            media.setFilename(fileName);
            media.setOriginFilename(fileName);
            media.setRelativePath(uploadedImagePath + fileName);
            Media savedMedia = mediaRepository.save(media);
            return dataMapper.toMediaData(savedMedia);
        } catch (IOException e) {
            throw new RuntimeException("Error save image", e);
        }
    }

    @Override
    public String saveImage(InputStream inputStream, ImageUtil.Format format, int width, int height) {
        try {
            byte[] resized = ImageUtil.resizeImage(inputStream, width, height, format);
            String fileName = UUID.randomUUID().toString() + "." + format;
            File file = new File(imageDir, fileName);
            IOUtils.write(resized, new FileOutputStream(file));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error save image", e);
        }
    }

    @Override
    public String saveImage(MultipartFile multipartFile, int width, int height) throws ImageUtilException {
        if (validateImage(multipartFile)) {
            try {
                return saveImage(multipartFile.getInputStream(), getImageFormat(multipartFile), width, height);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("error on validate or save file");
    }

    @Override
    public String getImageDir() {
        return imageDir;
    }

    @Override
    public MediaData saveImageAsMedia(MultipartFile multipartFile, int width, int height) throws ImageUtilException {
        String filename = saveImage(multipartFile, width, height);
        String originalFilename = multipartFile.getOriginalFilename();
        Media media = new Media();
        media.setFilename(filename);
        media.setOriginFilename(originalFilename);
        media.setRelativePath(uploadedImagePath + filename);
        Media savedMedia = mediaRepository.save(media);
        return dataMapper.toMediaData(savedMedia);
    }

    @Override
    public MediaData findOne(String logoMediaId) {
        Media one = mediaRepository.findOne(logoMediaId);
        return dataMapper.toMediaData(one);
    }

    protected boolean validateImage(MultipartFile image) {
        return image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/png");
    }

    protected static ImageUtil.Format getImageFormat(MultipartFile image) {
        if (image.getContentType().equals("image/jpeg")) {
            return ImageUtil.Format.jpg;
        }
        if (image.getContentType().equals("image/png")) {
            return ImageUtil.Format.png;
        }
        return null;
    }
}
