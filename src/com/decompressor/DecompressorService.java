/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.decompressor;

import com.CompressionStrategy;

/**
 * @author anup
 * @version $Id: DecompressorService.java, v 0.1 2020-02-02 13:39 anup Exp $$
 */
public class DecompressorService {

    public void decompress(CompressionStrategy compressionStrategy, String inputFilePath, String outputFilePath)
    {
        compressionStrategy.getDecompressor().decompress(inputFilePath,outputFilePath);
    }
}