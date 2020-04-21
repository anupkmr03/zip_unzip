/**
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.decompressor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author anup
 * @version $Id: ZipDecompressor.java, v 0.1 2020-02-02 13:30 anup Exp $$
 */
public class ZipDecompressor implements Decompressor {

    private static final Logger logger = Logger.getLogger(ZipDecompressor.class.getName());

    @Override
    public void decompress(String inputFilePath, String outputFilePath) {
        List<String> zipFiles = getAllZipFiles(inputFilePath);
        decompress(zipFiles, outputFilePath);
    }

    private List<String> getAllZipFiles(String input) {
        logger.info("Collecting all zip files inside the given directory : " + input);
        List<String> result = null;
        try (Stream<Path> walk = Files.walk(Paths.get(input))) {
            result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".zip")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null)
            result.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

        return result;

    }

    private void decompress(List<String> inputFile, String output) {
        ZipEntry previous = null;
        InputStream is = null;
        FileOutputStream fos = null;
        for (String str : inputFile) {
            try {
                ZipFile zipFile = new ZipFile(str);
                Enumeration<?> enu = zipFile.entries();
                while (enu.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                    String name = zipEntry.getName();

                    // Do we need to create a directory ?
                    File file = new File(output + name);
                    logger.info("De-Compressing " + file.getPath());

                    if (name.endsWith("/")) {
                        file.mkdirs();
                        continue;
                    }

                    File parent = file.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }

                    if (previous == null) {
                        is = zipFile.getInputStream(zipEntry);
                        fos = new FileOutputStream(file);
                    } else if (!previous.getName().equals(zipEntry.getName())) {
                        fos.flush();
                        is = zipFile.getInputStream(zipEntry);
                        fos = new FileOutputStream(file);
                    } else {
                        is = zipFile.getInputStream(zipEntry);
                    }

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = is.read(bytes)) >= 0) {
                        fos.write(bytes, 0, length);
                    }
                    previous = zipEntry;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
