package com.jlife.abon.util;

import com.jlife.abon.utility.CropProperty;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


// todo make better API: working with water mark

public class ImageUtil {

    public enum Format {
        jpg,
        png
    }

    public static byte[] resizeImage(InputStream originalImage, int width, int height, Format format) throws ImageUtilException {
        return resizeImage(originalImage, width, height, format, null, false);
    }

    public static byte[] resizeImage(InputStream originalImage, int width, int height, Format format, String waterMark) throws ImageUtilException {
        return resizeImage(originalImage, width, height, format, waterMark, true);
    }


    private static byte[] resizeImage(InputStream originalImage, int width, int height, Format format, String waterMark, boolean waterMarkEnabled) throws ImageUtilException {
        try {
            return resizeImage(ImageIO.read(originalImage), width, height, format, waterMark, waterMarkEnabled);
        } catch (IOException e) {
            throw new ImageUtilException("Failed resize image", e);
        }
    }

    private static byte[] resizeImage(BufferedImage bufferedImage, int width, int height,
                                      Format format, String waterMark, boolean waterMarkEnabled) throws ImageUtilException {
        try {

            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            int originWidth = bufferedImage.getWidth();
            int originHeight = bufferedImage.getHeight();
            float xScale = originWidth / (float) width;
            float yScale = originHeight / (float) height;

            if (xScale > yScale) {
                height = (int) (originHeight / xScale);
            } else {
                width = (int) (originWidth / yScale);
            }

            BufferedImage resizedImage = new BufferedImage(width, height, type);

            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (waterMarkEnabled) {
                drawingWatermark(resizedImage, waterMark);
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, format.toString(), os);

            return os.toByteArray();
        } catch (IOException e) {
            throw new ImageUtilException("Failed resize image", e);
        }
    }

    public static String resizeOriginImage(String imageDir, String originFilename, CropProperty cropProperty, int maxWidth, int maxHeight) throws ImageUtilException {
        try {
            File imgFile = new File(imageDir + "/" + originFilename);
            BufferedImage bufferedImage = ImageIO.read(imgFile);

            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            int x = cropProperty.getX().intValue();
            int y = cropProperty.getY().intValue();
            int originWidth = cropProperty.getWidth().intValue();
            int originHeight = cropProperty.getHeight().intValue();
            float xScale = originWidth / (float) maxWidth;
            float yScale = originHeight / (float) maxHeight;
            int destinationHeight = maxHeight;
            int destinationWitdth = maxWidth;
            if (xScale > yScale) {
                destinationHeight = (int) (originHeight / xScale);
            } else {
                destinationWitdth = (int) (originWidth / yScale);
            }

            BufferedImage resizedImage = new BufferedImage(destinationWitdth, destinationHeight, type);

            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, destinationWitdth, destinationHeight, x, y, x + originWidth, y + originHeight, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Format format = getFormat(originFilename);
            String resizedName = UUID.randomUUID().toString() + "." + format;
            File resizedFile = new File(imageDir, resizedName);
            ImageIO.write(resizedImage, format.toString(), resizedFile);

            return resizedName;
        } catch (IOException e) {
            throw new ImageUtilException("Failed resize image", e);
        }
    }

    private static void drawingWatermark(BufferedImage resizedImage, String waterMark) {
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.white);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (resizedImage.getWidth() < 300 || resizedImage.getHeight() < 300) {
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
        } else if (resizedImage.getWidth() > 1000 && resizedImage.getHeight() > 300) {
            g2d.setFont(new Font("Arial", Font.BOLD, 35));
        } else {
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
        }

        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(waterMark, g2d);

        int centerX = (resizedImage.getWidth() - (int) rect.getWidth()) - 10;
        int centerY = (resizedImage.getHeight() - (int) rect.getHeight() + 5);
        g2d.drawString(waterMark, centerX, centerY);

        g2d.dispose();
    }

    public static Format getFormat(String imageName) {
        if (imageName.endsWith(".png")) {
            return Format.png;
        } else if (imageName.endsWith(".jpg")) {
            return Format.jpg;
        } else if (imageName.endsWith(".jpeg")) {
            return Format.jpg;
        }
        throw new RuntimeException("Unknow image format");
    }
}
