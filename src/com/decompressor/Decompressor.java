/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.decompressor;

/**
 * @author anup
 * @version $Id: Decompressor.java, v 0.1 2020-02-02 13:28 anup Exp $$
 */
public interface Decompressor {

    void decompress(String inputFilePath, String outputFilePath);
}