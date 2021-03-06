package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadingStringFromFile {
    private static final String FILENAME = "D:\\LOGS\\12-2017\\dsrequest.log.2017-12-01";

    private static final int BUFFER_SIZE = 1024;
    Map<Integer,String> listOfOwnerIP = new HashMap<>();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String newline = getOwnerDateCmd(line);
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

    public static String getOwnerDateCmd(String line) {
            String dateAndTimeFromLine, cmdFromLine = "", owner = "unknown";
            String[] lineParts = line.split("\t");//разбиваем на элементы по табуляции
            // выбираем первый элемент до табуляции с датой и веременем
            dateAndTimeFromLine = line.substring(0, lineParts[0].length());
            //System.out.println("prefix date: " + dateAndTimeFromLine);
            //dateAndTimeFromLine = line.substring(0,19);
            //System.out.println(dateAndTimeFromLine);
            String[] commands = line.split("\t");
            //проходим каждую подстроку
            String cmd;
        for (String command : commands) {
            //выбираем значения только с торговцем(owner) и с командой(cmd)
            cmd = command.toLowerCase();
            if (cmd.contains("type:res")) {
                return "##$$@@";
            }
            if (cmd.contains("owner:")) {
                owner = getValueCmd(cmd);
            }
            if (cmd.toLowerCase().contains("cmd:")) {
                cmdFromLine = getValueCmd(cmd);
            }
        }
        if (owner.equals("unknown")) {
            return "##$$@@";
        }
        String cmdString = owner + ": " + dateAndTimeFromLine + "\t" + cmdFromLine;
        return cmdString;

    }

    public static String getOwnerIP(String line) {
        String ip = "", owner = "unknown";
        String[] lineParts = line.split("\t");//разбиваем на элементы по табуляции

        //System.out.println(dateAndTimeFromLine);
        String[] commands = line.split("\t");
        //проходим каждую подстроку
        for (String command : commands) {
            String cmd = command.toLowerCase();
            //выбираем значения только с торговцем(owner) и с IP(ip)

            if (cmd.contains("type:res")) {//игнорируем строки с ответом TYPE:RES, нам нужны только TYPE:REQ
                return "##$$@@";
            }
            if (cmd.contains("owner:")) {
                owner = getValueCmd(cmd);
            }
            if (cmd.toLowerCase().contains("ip:")) {
                ip = getValueCmd(cmd);
            }
        }
        if (owner.equals("unknown")) {
            return "##$$@@";
        }
        String cmdString = owner + "\t" + ip;
        return cmdString;

    }


    private static String getValueCmd(String command) {
        //находим индекс первого вхождения символа ":" в подстроке
        int pos = command.indexOf(":");
        //вычленяем имя атрибута из подстроки
        String attributeName = command.substring(0, pos);
        //вычленяем значение атрибута
        String value = command.substring(pos + 1, command.length());
        //вывод на экран вычлененных значений в нужном нам формате.
        //System.out.println(attributeName + " - " + value);
        return value;
    }
}
