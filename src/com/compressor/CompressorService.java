/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.compressor;

import com.CompressionStrategy;

/**
 * @author anup
 * @version $Id: CompressorService.java, v 0.1 2020-02-02 13:34 anup Exp $$
 */
public class CompressorService {

    public void compress(CompressionStrategy compressionStrategy,String inputDir, String outputDir,int perFileLimitInMB)
    {
        compressionStrategy.getCompressor().compress(inputDir,outputDir,perFileLimitInMB);
    }
}