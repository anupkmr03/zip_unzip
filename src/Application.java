/**
 * Copyright (c) 2004-2020 All Rights Reserved.
 */

import com.CompressionStrategy;
import com.compressor.Compressor;
import com.decompressor.Decompressor;

/**
 * @author anup
 * @version $Id: Application.java, v 0.1 2020-02-15 13:51 anup Exp $$
 */
public class Application {
    public static void main(String[] args) {
        CompressionStrategy compressionStrategy = CompressionStrategy.ZIP;
        Compressor compressor = compressionStrategy.getCompressor();
        Decompressor decompressor = compressionStrategy.getDecompressor();

        // ----------   Compression ------------------//
        System.out.println("// ----------   Compression ------------------//");
        String compressInputDirectory = "/Users/anupkumar/zip_unzip/src/test_input";
        String compressOutputDirectory = "/Users/anupkumar/zip_unzip/src/compress_output";
        compressor.compress(compressInputDirectory, compressOutputDirectory, 2);

        // ----------   Decompression ------------------//
        System.out.println("// ----------   Decompression ------------------//");
        String deCompressInputDirectory = "/Users/anupkumar/zip_unzip/src/compress_output";
        String deCompressOutputDirectory = "/Users/anupkumar/zip_unzip/src/decompress_output/";
        decompressor.decompress(deCompressInputDirectory, deCompressOutputDirectory);

    }
}
