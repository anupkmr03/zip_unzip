/**
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.compressor;

import com.decompressor.ZipDecompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author anup
 * @version $Id: ZipCompressor.java, v 0.1 2020-02-02 13:30 anup Exp $$
 */
public class ZipCompressor implements Compressor {

    private static final Logger logger = Logger.getLogger(ZipDecompressor.class.getName());
    private List<String> fileList = new ArrayList<>();

    @Override
    public void compress(String inputDir, String outputDir, int perFileLimitInMB) {
        String outputDirName = inputDir.substring(inputDir.lastIndexOf("/") + 1);
        File f = new File(outputDir);
        f.mkdirs();
        outputDir = outputDir + "/" + outputDirName + ".zip";

        logger.info("Compression : Output Path " + outputDir);
        // mb to byte conversion
        perFileLimitInMB = perFileLimitInMB * 1000000;
        File directory = new File(inputDir);
        getFileList(directory);
        int currentSize = 0;
        int count = 0;
        boolean flag = true;
        ZipOutputStream zos = null;
        FileOutputStream fos = null;
        try {
            for (String filePath : fileList) {
                if (flag) {
                    fos = new FileOutputStream(getName(outputDir, count));
                    zos = new ZipOutputStream(fos);
                    flag = false;
                    currentSize = 0;
                    count++;
                }
                logger.info("Compressing: " + filePath);
                // Creates a zip entry.
                String name;
                if (directory.isFile()) {
                    name = directory.getName();
                } else {
                    name = filePath.substring(
                            directory.getAbsolutePath().length() + 1);
                }


                ZipEntry zipEntry = new ZipEntry(name);
                zos.putNextEntry(zipEntry);
                // Read file content and write to zip output stream.
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        if (flag) {
                            zos.close();
                            fos.close();
                            fos = new FileOutputStream(getName(outputDir, count));
                            zipEntry = new ZipEntry(name);
                            zos = new ZipOutputStream(fos);
                            zos.putNextEntry(zipEntry);
                            flag = false;
                            count++;
                        }
                        zos.write(buffer, 0, length);
                        currentSize += length;

                        if (currentSize >= perFileLimitInMB) {
                            currentSize = 0;
                            flag = true;
                        }
                    }
                    zos.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null)
                    zos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getName(String name, int count) {
        File file = new File(name);
        String fileName = file.getName();
        String pathPrefix = fileName.substring(0, fileName.lastIndexOf("."));
        String path = file.getPath();
        return path.substring(0, path.lastIndexOf("/")) + "/" +
                pathPrefix + count + ".zip";
    }

    /**
     * Get files list from the directory recursive to the sub directory.
     */
    private void getFileList(File directory) {
        if (directory.isFile()) {
            fileList.add(directory.getAbsolutePath());
        } else {
            checkRecursively(directory);
        }

    }

    private void checkRecursively(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }
}
