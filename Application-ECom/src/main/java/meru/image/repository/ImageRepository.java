package meru.image.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import meru.image.ImageSize;
import meru.image.ImageSystem;
import app.domain.image.EntityImage;

public class ImageRepository<T extends EntityImage> {

    private static final String NO_IMAGE = "no-image.jpg";

    private static final byte BUCKET_SIZE = 100;

    private File mWebAppHome;
    private String mImgRoot;
    private Class<T> mImgClass;

    private ImageSize[] mImageSizes;
    private boolean mRequiresBucket;

    public ImageRepository(String appName,
                           String imgHome,
                           Class<T> imgClass,
                           ImageSize... imageSizes) {

        if (appName != null) {
            appName = appName + "/";
        }
        else {
            appName = "/";
        }

        mImgClass = imgClass;
        mImgRoot = appName + "img/" + imgHome + "/";

        mWebAppHome = new File("webapps");
        File imageHome = new File(mWebAppHome,
                                  mImgRoot);
        imageHome.mkdirs();
        mImageSizes = imageSizes;
    }

    // Temp Hack
    public static String getImagePath(String imagePath, ImageSize imageSize) {

        int index = imagePath.lastIndexOf('/') + 1;
        imagePath = imagePath.substring(0,
                                        index)
                + imageSize.getName() + "/" + imagePath.substring(index);
        return imagePath;
    }

    public void requiresBucket(boolean requiresBucket) {
        mRequiresBucket = requiresBucket;
    }

    public final String getNoImage() {
        return mImgRoot + NO_IMAGE;
    }

    public static int getImageBucket(String entityId) {
        return (int) (Math.abs(entityId.hashCode()) % BUCKET_SIZE);
    }

    private File storeImage(File targetFolder,
                            File imageFile,
                            String fileName) {

        targetFolder.mkdirs();

        try {

            if (fileName == null) {
                fileName = imageFile.getName();
            }
            else {
                fileName = fileName + ".jpg";
            }

            if (mImageSizes != null) {
                for (ImageSize imageSize : mImageSizes) {
                    createImage(imageFile,
                                targetFolder,
                                fileName,
                                imageSize);
                }

            }

            File targetFile = new File(targetFolder,
                                       fileName);
            imageFile.renameTo(targetFile);
            return targetFile;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createImage(File sourceImgFile,
                             File targetFolder,
                             String fileName,
                             ImageSize imageSize) throws IOException {

        File tFolder = new File(targetFolder,
                                imageSize.getName());
        tFolder.mkdir();

        File tnFile = new File(tFolder,
                               fileName);

        if (tnFile.exists()) {
            tnFile.delete();
        }
        else {
            tnFile.getParentFile()
                  .mkdir();
        }
        BufferedImage bufferedImage = ImageIO.read(sourceImgFile);

        int width = bufferedImage.getWidth();

        float widthPer = 0F;
        if (width > imageSize.getWidth()) {
            widthPer = (float) imageSize.getWidth() / width;
        }

        int height = bufferedImage.getHeight();
        float heightPer = 0F;
        if (height > imageSize.getHeight()) {
            heightPer = (float) imageSize.getHeight() / height;
        }

        float percent = 0F;
        if (widthPer == 0F && heightPer == 0F) {
            percent = 0;
        }

        else if (widthPer == 0F) {
            percent = heightPer;
        }
        else if (heightPer == 0F) {
            percent = widthPer;
        }
        else {
            percent = (widthPer < heightPer) ? widthPer : heightPer;
        }

        if (percent > 0) {

            width = Math.round(width * percent);
            height = Math.round(height * percent);

        }

        ImageSystem.resize(sourceImgFile,
                           tnFile,
                           width,
                           height);

    }

    private String getImageRelativePath(String relativePath, String entityId) {
        String path = mImgRoot;

        if (relativePath != null) {
            path += relativePath + "/";
        }

        if (mRequiresBucket) {
            path += getImageBucket(entityId) + "/" + entityId;
        }
        else {
            path += String.valueOf(entityId);
        }

        return path;

    }

    private File getImageFolder(String relativePath, String entityId) {
        return new File(mWebAppHome,
                        getImageRelativePath(relativePath,
                                             entityId));

    }

    public File storeImage(T entityImage) {

        if (entityImage.getId() != null) {
            deleteImage(entityImage.getId());
        }

        File imageFile = new File(entityImage.getImage());
        File imgFolder = getImageFolder(entityImage.getRelativePath(),
                                        entityImage.getEntityId());

        /*
         * if (imgFolder.exists()) {
         * 
         * IOSystem.delete(imgFolder); }
         */

        File targetFile = storeImage(imgFolder,
                                     imageFile,
                                     null);

        populateImage(entityImage,
                      targetFile);
        /*
         * String path = targetFile.getAbsolutePath() .replace('\\', '/'); path
         * = path.substring(path.indexOf(mImgRoot)); entityImage.setId(path);
         * entityImage.setImage(path);
         */
        return targetFile;

    }

    public void deleteImage(String id) {
        File imgFile = new File(mWebAppHome,
                                id);
        imgFile.delete();

        if (mImageSizes != null) {
            for (ImageSize imageSize : mImageSizes) {
                File file = new File(imgFile.getParent(),
                                     imageSize.getName());
                File isFile = new File(file,
                                       imgFile.getName());
                isFile.delete();
            }

        }
    }

    public T getEntityImage(String id) {

        T entityImage = null;
        try {
            entityImage = mImgClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File imgFile = new File(mWebAppHome,
                                id);
        T image = populateImage(entityImage,
                                imgFile);
        image.setId(id);
        return image;
    }

    public List<T> getEntityImages(String relativePath, String entityId) {
        return getEntityImages(relativePath,
                               entityId,
                               null);
    }

    public List<T> getEntityImages(String relativePath,
                                   String entityId,
                                   ImageSize imageSize) {

        
        
        File imgFolder = getImageFolder(relativePath,
                                        entityId);


        if (imageSize != null) {
            imgFolder = new File(imgFolder,
                                 imageSize.getName());
        }

        File[] imgFiles = imgFolder.listFiles();

        if (imgFiles == null) {
            return new ArrayList<T>(0);
        }

        List<T> entityImages = new ArrayList<T>(imgFiles.length);

        for (File imgFile : imgFiles) {
            String name = imgFile.getName();
            if (imgFile.isFile()) {
                entityImages.add(createImage(relativePath,
                                             entityId,
                                             name,
                                             imageSize));
            }
        }

        return entityImages;
    }

    private T createImage(String relativePath,
                          String entityId,
                          String name,
                          ImageSize imageSize) {

        T entityImage = null;
        try {
            entityImage = mImgClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        relativePath = getImageRelativePath(relativePath,
                                            entityId);

        if (imageSize != null) {
            relativePath += "/" + imageSize.getName();
        }

        name = relativePath + "/" + name;

        entityImage.setId(name);
        entityImage.setRelativePath(relativePath);
        entityImage.setEntityId(entityId);
        entityImage.setImage(name);

        return entityImage;
    }

    private T populateImage(T entityImage, File imgFile) {

        String filePath = imgFile.getAbsolutePath()
                                 .replace('\\',
                                          '/');
        filePath = filePath.substring(filePath.indexOf(mImgRoot));

        File entityFolder = imgFile.getParentFile();

        String entityId = entityFolder.getName();

        String relativePath = entityFolder.getParentFile()
                                          .getAbsolutePath()
                                          .replace('\\',
                                                   '/');
        relativePath = relativePath.substring(relativePath.indexOf(mImgRoot)
                + mImgRoot.length());

        entityImage.setId(filePath);
        entityImage.setRelativePath(relativePath);
        entityImage.setEntityId(entityId);
        entityImage.setImage(filePath);

        return entityImage;
    }
}
