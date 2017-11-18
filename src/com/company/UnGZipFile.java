package com.company;

import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class UnGZipFile {

    private static final String FILENAME = "D:\\LOGS\\11-2017\\" +
            "dsrequest.log.2017-11-01--172.19.18.11.tar.gz";

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();


        UnGZipFile app = new UnGZipFile();
        app.unGZIP(FILENAME);

        long timeWorkCode = System.currentTimeMillis() - start;

        System.out.println("Скорость выполнения программы: " + timeWorkCode + " миллисекунд");

    }

    private void unGZIP(final String filename) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            GZIPInputStream gzipis = new GZIPInputStream(new FileInputStream(filename));
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipis) /*FileReader(FILENAME)*/);
            String line;
            while ((line = reader.readLine()) != null) {

                //System.out.println(line);
                   String newline = ReadingStringFromFile.getOwnerDateCmd(line);
                    if (newline.contains("##$$@@")) {
                        continue;
                    }
                    WriteDataInFileWithNameOfOwner writeData = new WriteDataInFileWithNameOfOwner();
                    writeData.saveLine(newline);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(UnGZipFile.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UnGZipFile.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getDstFileName(final String srcFileName) {
        return srcFileName.substring(0, srcFileName.lastIndexOf("."));
    }
}





