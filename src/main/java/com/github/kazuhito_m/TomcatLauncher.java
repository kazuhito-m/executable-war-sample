package com.github.kazuhito_m;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.net.URL;

public class TomcatLauncher {
    public static void main(String[] args) throws ServletException, LifecycleException {
        URL warLocation = TomcatLauncher.class.getProtectionDomain().getCodeSource().getLocation();
        Tomcat tomcat = new Tomcat();
        tomcat.addWebapp("", warLocation.getPath());
        tomcat.start();
        tomcat.getServer().await();
    }
}