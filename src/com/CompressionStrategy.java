/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com;

import com.compressor.Compressor;
import com.compressor.ZipCompressor;
import com.decompressor.Decompressor;
import com.decompressor.ZipDecompressor;

/**
 * @author anup
 * @version $Id: CompressionStrategy.java, v 0.1 2020-02-02 13:31 anup Exp $$
 */
public enum CompressionStrategy {

    ZIP(new ZipCompressor(),new ZipDecompressor());

    Compressor compressor;

    Decompressor decompressor;

    CompressionStrategy(Compressor compressor, Decompressor decompressor)
    {
        this.compressor = compressor;
        this.decompressor = decompressor;
    }


    public Compressor getCompressor() {
        return compressor;
    }


    public Decompressor getDecompressor() {
        return decompressor;
    }
}