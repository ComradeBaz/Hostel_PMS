/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.tools;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author ComradeBaz
 */
public class Logger {

    // C:\\devlogs\\int_logfile.txt
    private static final String FILE_NAME = "C:\\PMS Logs\\logfile.txt";
    private static final String DIRECTORY_NAME = "C:\\PMS Logs\\";
    private static File THE_FILE; //= new File(FILE_NAME);
    private static final File THE_DIRECTORY = new File(DIRECTORY_NAME);
    private static BufferedWriter bw = null;

    private static SimpleDateFormat sdfTime, sdfDate;
    private static String lastString = "";

    public static void safePrint(String s) {

        // Discard repeated lines
        if (lastString.equalsIgnoreCase(s)) {
            return;
        }
        
        initialiseLogger();

        if (checkFileSize()) {
            try {
                renameFile(THE_FILE, getNewFileName());
            } catch (IOException io) {
                Logger.safePrint("Rename logFile failed " + io.getMessage());
            }
        }

        sdfTime = new SimpleDateFormat("HH:mm:ss");
        sdfDate = new SimpleDateFormat("dd:MMM:yyyy");
        Date now = new Date();
        String strTime = sdfTime.format(now);
        String strDate = sdfDate.format(now);
        String newLine = System.getProperty("line.separator");

        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            bw = new BufferedWriter(fw);
            synchronized (bw) {
                bw.write(strTime + " " + strDate + "\t");
                bw.write(s);
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (IOException io) {
            System.out.println("Unable to open logFile " + FILE_NAME);
        }
        lastString = s;
    }

    public static void safePrint(int i) {

        checkFileSize();
        
        initialiseLogger();

        sdfTime = new SimpleDateFormat("HH:mm:ss");
        sdfDate = new SimpleDateFormat("dd:MMM:yyyy");
        Date now = new Date();
        String strTime = sdfTime.format(now);
        String strDate = sdfDate.format(now);

        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            bw = new BufferedWriter(fw);
            synchronized (bw) {
                bw.write(strTime + " " + strDate + "\t");
                bw.write(String.valueOf(i));
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (IOException io) {
            System.out.println("Unable to open logFile " + FILE_NAME);
        }
    }

    // Check log file size and archive if it's above 500KB
    public static boolean checkFileSize() {

        double fileSize = THE_FILE.length() / 1024;
        if (fileSize > 500) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static String getNewFileName() {
        sdfTime = new SimpleDateFormat("HH_mm_ss");
        sdfDate = new SimpleDateFormat("dd_MMM_yyyy");
        Date now = new Date();
        String strTime = sdfTime.format(now);
        String strDate = sdfDate.format(now);

        return "C:\\devlogs\\logfile_" + strDate + "_" + strTime + ".txt";
    }

    public static void renameFile(File toBeRenamed, String new_name) throws IOException {
        //need to be in the same path
        File fileWithNewName = new File(new_name);
        if (fileWithNewName.exists()) {
            throw new IOException("file exists");
        }
        // Rename file (or directory)
        boolean success = toBeRenamed.renameTo(fileWithNewName);

        if (success) {
            Logger.safePrint("Archived log file");
        }
    }
    private static void initialiseLogger() {
        
        if (!THE_DIRECTORY.exists()) {
            THE_DIRECTORY.mkdir();
            THE_FILE = new File(FILE_NAME);
        } else {
            THE_FILE = new File(FILE_NAME);
        }
    }
}
