package com.example.hoteljavafx.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageManager {

    public static String copyImageToResources(String absoluteImagePath,String Dir) throws IOException {
        File sourceFile = new File(absoluteImagePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new IllegalArgumentException("Le fichier source n'existe pas : " + absoluteImagePath);
        }
        String resourcesDir = "src/main/resources/Images/"+Dir;
        File targetDir = new File(resourcesDir);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        File targetFile = new File(targetDir, sourceFile.getName());
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return "/Images/" +Dir+"/"+ sourceFile.getName();
    }

}

