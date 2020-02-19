/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.compressor;

/**
 * @author anup
 * @version $Id: Compressor.java, v 0.1 2020-02-02 13:25 anup Exp $$
 */
public interface  Compressor {

    void compress(String inputDir, String outputDir, int perFileLimitInMB);
}