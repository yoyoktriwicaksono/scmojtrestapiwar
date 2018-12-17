package org.scm.ojt.rest.jobs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

/**
 * Created by Yoyok_T on 20/11/2018.
 */
public class SiteMapThread implements Runnable {
    private ServletContext context;

    public SiteMapThread(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        System.out.println("Generate sitemap ... " + new Date());

        try {
            this.createFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finish generation");
    }

    private void createFile() throws IOException {
        System.out.println("Generate file sitemap.xml to: "
                + context.getRealPath(""));
        String path = context.getRealPath("sitemap.xml");
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("<?xml version='1.0' ?>");
        writer.write("<urlset xmlns='http://www.sitemaps.org/schemas/sitemap/0.9' "
                + " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'  "
                + " xsi:schemaLocation='http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd'>");
        //
        writer.write("<url>");
        writer.write("<loc>//o7planning.org</loc>");
        writer.write("<changefreq>daily</changefreq>");
        writer.write("<priority>0.80</priority>");
        writer.write("</url>");
        //
        writer.write("<url>");
        writer.write("<loc>//o7planning.org/index.html</loc>");
        writer.write("<changefreq>daily</changefreq>");
        writer.write("<priority>0.80</priority>");
        writer.write("</url>");
        //
        writer.write("</urlset>");
        writer.close();
    }
}