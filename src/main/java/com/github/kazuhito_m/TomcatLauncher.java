package com.github.kazuhito_m;

import org.apache.catalina.startup.Tomcat;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TomcatLauncher {

    public static void main(String[] args) throws Exception {
        Parameters parameters = new Parameters(args);

        URL warLocation = TomcatLauncher.class.getProtectionDomain().getCodeSource().getLocation();
        Path thisWarPath = Paths.get(warLocation.toURI());

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(parameters.port());

        tomcat.addWebapp(parameters.contextRoot(), thisWarPath.toString());

        Path createTempPath = Files.createTempFile(null, ".war");
        Files.copy(TomcatLauncher.class.getResourceAsStream("sample.war"), createTempPath, StandardCopyOption.REPLACE_EXISTING);
        tomcat.addWebapp("/innerWar", createTempPath.toString());

        Path jerUnpackDir = thisWarPath.getParent().resolve("unpack");
        new JarExpander(thisWarPath).expand(jerUnpackDir);
        tomcat.addWebapp("/unpackSelfWar", createTempPath.toString());

        tomcat.start();
        tomcat.getServer().await();
    }

}