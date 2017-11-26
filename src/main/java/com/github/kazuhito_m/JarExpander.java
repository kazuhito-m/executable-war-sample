package com.github.kazuhito_m;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

class JarExpander {

    void expand(Path directoryPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(jarPath.toFile());
             JarInputStream archive = new JarInputStream(fis)) {
            if (!Files.exists(directoryPath)) Files.createDirectory(directoryPath);
            ZipEntry entry = null;
            while ((entry = archive.getNextEntry()) != null) {
                Path targetPath = directoryPath.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectory(targetPath);
                    continue;
                }
                Path parentPath = targetPath.getParent();
                if (Files.notExists(parentPath)) Files.createDirectory(parentPath);
                Files.copy(archive, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    final Path jarPath;

    JarExpander(Path jarPath) {
        this.jarPath = jarPath;
    }
}
