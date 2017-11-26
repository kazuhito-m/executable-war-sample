package com.github.kazuhito_m;

import org.apache.catalina.startup.Tomcat;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class TomcatLauncher {
    public static void main(String[] args) throws Exception {
        URL warLocation = TomcatLauncher.class.getProtectionDomain().getCodeSource().getLocation();
        Path thisWarPath = Paths.get(warLocation.toURI());

        Tomcat tomcat = new Tomcat();

        tomcat.addWebapp(analyzeContextPath(args), thisWarPath.toString());

        Path createTempPath = Files.createTempFile(null, ".war");
        Files.copy(TomcatLauncher.class.getResourceAsStream("sample.war"), createTempPath, StandardCopyOption.REPLACE_EXISTING);
        tomcat.addWebapp("/innerWar", createTempPath.toString());

        Path jerUnpackDir = thisWarPath.getParent().resolve("unpack");
        decompressJar(thisWarPath, jerUnpackDir);
        tomcat.addWebapp("/unpackSelfWar", createTempPath.toString());

        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * jar解凍
     */
    protected static void decompressJar(Path inputFile, Path outputDir) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile.toFile());
             JarInputStream archive = new JarInputStream(fis)) {
            if (!Files.exists(outputDir)) Files.createDirectory(outputDir);
            ZipEntry entry = null;
            while ((entry = archive.getNextEntry()) != null) {
                Path targetPath = outputDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectory(targetPath);
                    continue;
                }
                Path parentPath = targetPath.getParent();
                if (Files.notExists(parentPath)) Files.createDirectory(parentPath);
                Files.copy(archive, targetPath);
            }
        }
    }

    private static String analyzeContextPath(String[] args) {
        if (args.length == 0) return "";
        return "/" + args[0].replaceAll("^\\/*", "");
    }

}