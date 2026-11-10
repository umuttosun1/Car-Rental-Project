package com.swiftwheels.ui;

import com.swiftwheels.model.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads pictures independent of the working directory.
 * <ul>
 *   <li>UI artwork is bundled on the classpath under {@code /images}.</li>
 *   <li>Vehicle photos uploaded by the admin go to {@code ~/SwiftWheels/vehicle-images}
 *       (writable even when the app is installed under Program Files).</li>
 * </ul>
 */
final class Images {

    private static final Map<String, Image> CLASSPATH_CACHE = new ConcurrentHashMap<>();

    private Images() {
    }

    /** A bundled image scaled to fit inside {@code width x height}, keeping its aspect ratio. */
    static ImageIcon icon(String name, int width, int height) {
        Image image = CLASSPATH_CACHE.computeIfAbsent(name, Images::readClasspathImage);
        return image == null ? new ImageIcon() : new ImageIcon(fit(image, width, height));
    }

    /** Photo of a vehicle: uploaded photo, bundled sample photo, or the type's default picture. */
    static ImageIcon vehicleIcon(Vehicle vehicle, int width, int height) {
        Path uploaded = vehicleImageFile(vehicle.getId());
        if (Files.isRegularFile(uploaded)) {
            try {
                BufferedImage img = ImageIO.read(uploaded.toFile());
                if (img != null) {
                    return new ImageIcon(fit(img, width, height));
                }
            } catch (IOException e) {
                System.err.println("Cannot read " + uploaded + ": " + e.getMessage());
            }
        }
        String sample = SAMPLE_PHOTOS.getProperty(String.valueOf(vehicle.getId()));
        return icon(sample != null ? sample : vehicle.getType().defaultImage(), width, height);
    }

    /** vehicle id -> bundled image, for the demo vehicles inserted by database/seed.sql */
    private static final Properties SAMPLE_PHOTOS = loadSamplePhotos();

    private static Properties loadSamplePhotos() {
        Properties p = new Properties();
        try (InputStream in = Images.class.getResourceAsStream("/images/sample-vehicle-photos.properties")) {
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            System.err.println("Cannot read sample-vehicle-photos.properties: " + e.getMessage());
        }
        return p;
    }

    static Path vehicleImagesDir() {
        return Paths.get(System.getProperty("user.home"), "SwiftWheels", "vehicle-images");
    }

    static Path vehicleImageFile(int vehicleId) {
        return vehicleImagesDir().resolve("vehicle_" + vehicleId + ".png");
    }

    /** Converts any image ImageIO understands (png, jpg, gif, bmp) to a PNG for the vehicle. */
    static void saveVehiclePhoto(File source, int vehicleId) throws IOException {
        BufferedImage img = ImageIO.read(source);
        if (img == null) {
            throw new IOException("Desteklenmeyen resim dosyası: " + source.getName());
        }
        Files.createDirectories(vehicleImagesDir());
        ImageIO.write(img, "png", vehicleImageFile(vehicleId).toFile());
    }

    static void deleteVehiclePhoto(int vehicleId) {
        try {
            Files.deleteIfExists(vehicleImageFile(vehicleId));
        } catch (IOException e) {
            System.err.println("Cannot delete photo of vehicle " + vehicleId + ": " + e.getMessage());
        }
    }

    static ImageIcon fromFile(File file, int width, int height) throws IOException {
        BufferedImage img = ImageIO.read(file);
        if (img == null) {
            throw new IOException("Desteklenmeyen resim dosyası: " + file.getName());
        }
        return new ImageIcon(fit(img, width, height));
    }

    private static Image readClasspathImage(String name) {
        URL url = Images.class.getResource("/images/" + name);
        if (url == null) {
            System.err.println("Missing bundled image: " + name);
            return null;
        }
        try (InputStream in = url.openStream()) {
            return ImageIO.read(in);
        } catch (IOException e) {
            System.err.println("Cannot read bundled image " + name + ": " + e.getMessage());
            return null;
        }
    }

    private static Image fit(Image image, int maxWidth, int maxHeight) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        if (w <= 0 || h <= 0) {
            return image;
        }
        double scale = Math.min((double) maxWidth / w, (double) maxHeight / h);
        int newW = Math.max(1, (int) Math.round(w * scale));
        int newH = Math.max(1, (int) Math.round(h * scale));
        return image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    }
}
