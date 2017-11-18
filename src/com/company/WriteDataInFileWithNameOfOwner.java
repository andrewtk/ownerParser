package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class WriteDataInFileWithNameOfOwner {


    public WriteDataInFileWithNameOfOwner() {
    }

    public void saveLine (String line) {
        /*
        взять из лайн первое значение до :
        проверить есть ли файл с таким именем
        если нет то создать и записать строку
        если есть, то открыть файл записать строку
        закрыть файл.
        */

            //находим индекс первого вхождения символа ":" в подстроке
            int pos = line.indexOf(":");
            //вычленяем имя торговца которое будет именем файла из подстроки
            String ownerFileName = line.substring(0, pos);
            //вычленяем значение времяДату и команду
            String value = line.substring(pos + 1, line.length());





        try(FileWriter writer = new FileWriter("D:\\LOGS\\OWNERS\\" +
                ownerFileName, true))
        {
           // запись всей строки
            writer.write(line);
            // запись по символам
            writer.append('\n');


            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }




    }
}
