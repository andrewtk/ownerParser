package com.company;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnGZipFile {

    private static final String FILENAME = "D:\\LOGS\\11-2017\\" +
            "dsrequest.log.2017-11-01--172.19.18.11.tar.gz";

    private static final int BUFFER_SIZE = 1024;
    Map<String, BufferedWriter> nameOfFiles = new HashMap<>();

    public static void main(String[] args) throws IOException{
        //Map<String, FileWriter> nameOfFiles = new HashMap<String, FileWriter>();
        long start = System.currentTimeMillis();
        UnGZipFile app = new UnGZipFile();
        String fileDir = "D:\\LOGS\\10-2017\\REQUESTs";
        Stream<Path> files = Files.list(Paths.get(fileDir));
        files.forEach(file -> {
            long startOfFile = System.currentTimeMillis();
                  app.unGZIP(file.toString());
            System.out.println("Время обработки файла "+ (System.currentTimeMillis()-startOfFile)/1000 + " секунд");

            //System.out.println(file.toString());
        });
       app.closeAllFiles();

        //app.unGZIP(FILENAME);

        long timeWorkCode = System.currentTimeMillis() - start;
        System.out.println("Скорость выполнения программы: " + timeWorkCode / 1000 + " секунд");
    }

    /**
     * разархивируем файл и передаем строки для распарсинга
     * выбираем дату, время и команду
     * сохраняем данные в файл, который называется как оунер
     *
     * @param filename имя файла в котором находиться архив
     */
    private void unGZIP(final String filename) {
        System.out.println("обрабатываем " + filename);

        try {
            GZIPInputStream gzipis = new GZIPInputStream(new FileInputStream(filename));
            BufferedReader reader = new BufferedReader(new InputStreamReader(gzipis) /*FileReader(FILENAME)*/);
            String line;
            //WriteDataInFileWithNameOfOwner writeData = new WriteDataInFileWithNameOfOwner();
            int lineCounter=0;
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                if (line.equals("\t")){
                    continue;
                }
                //System.out.println(line);
                try {
                    String newline = ReadingStringFromFile.getOwnerDateCmd(line);
                    if (newline.contains("##$$@@")) {
                        continue;
                    }
                    //writeData.saveLine(newline);
                    saveLine(newline);
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Error on line "+ line);
                }
            }
            gzipis.close();
            //закрываем все файлы
            //closeAllFiles();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UnGZipFile.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UnGZipFile.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private  void closeAllFiles() throws IOException {
        for (BufferedWriter writer : nameOfFiles.values()) {
            writer.close();
        }
    }

    private String getDstFileName(final String srcFileName) {
        return srcFileName.substring(0, srcFileName.lastIndexOf("."));
    }

    public void saveLine(String line) {

        /*
        взять из лайн первое значение до ":"
        проверить есть ли файл с таким именем
        если нет то создать и записать строку
        если есть, то открыть файл записать строку
        закрыть файл.
        */
        String tempFolderName = "D:\\LOGS\\OWNERS\\";

        //находим индекс первого вхождения символа ":" в подстроке
        int pos = line.indexOf(":");
        //вычленяем имя торговца которое будет именем файла из подстроки
        String ownerFileName = line.substring(0, pos);
        //вычленяем значение времяДату и команду
        //String value = line.substring(pos + 1, line.length());


        try {
            if (!nameOfFiles.containsKey(ownerFileName)) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFolderName + ownerFileName + ".txt", true));
                nameOfFiles.put(ownerFileName, writer);
                writer.write(line);
                writer.append('\n');
                //writer.flush();
                //System.out.println(ownerFileName);
            } else {
                BufferedWriter writer = nameOfFiles.get(ownerFileName);
                //nameOfCurrentFile = nameOfFiles.get(ownerFileName);
                //writer = nameOfFiles.
                writer.write(line);
                writer.append('\n');
                //writer.flush();
                //System.out.println(ownerFileName);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + "\n" + ex.getCause());
            //System.out.println(ex.getStackTrace());
            ex.printStackTrace();
            System.out.println();

        }
    }


}





