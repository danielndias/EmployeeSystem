package com.danieldias.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class CreatePDF {
    private PDDocument docToCreate;
    private String PDFDirectoryPath;
    private static String pdfReportDir;
    private static Path path;

    public CreatePDF(){
            this.pdfReportDir = getUserDirectory();;
            path = Paths.get(CreatePDF.pdfReportDir);
            if (!(Files.exists(path))) {
                createPdfDir(pdfReportDir);
                this.PDFDirectoryPath = this.pdfReportDir;
            }
            else
                this.PDFDirectoryPath = this.pdfReportDir;
    }

    private static DateTimeFormatter getTimeFormat() {	return DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(new Locale("en")); }
    public static String formatTime(LocalTime time) { return getTimeFormat().format(time); }
    private static DateTimeFormatter getDateFormat() {	return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(new Locale("en")); }
    public static String formatDate(LocalDate date) { return  getDateFormat().format(date);}


    public String getPDFName(){
        String finalPDFName, dateToday, timeToday;
        String[] dateParts, timeParts, sec;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate dateOfToday = currentTime.toLocalDate();
        dateToday = formatDate(dateOfToday);
        LocalTime timeOfToday = currentTime.toLocalTime();
        timeToday = formatTime(timeOfToday);
        dateParts = dateToday.split("/");
        timeParts = timeToday.split(":");
        sec = timeParts[2].split(" ");
        finalPDFName = this.PDFDirectoryPath+"\\Employee Cost Report Generated "+dateParts[1]+"-"+dateParts[0]+"-"+dateParts[2]+" at "+timeParts[0]+"-"+timeParts[1]+"-"+sec[0]+".pdf";
        return finalPDFName;
    }

    public void savePDF(PDDocument docToCreate, String PDFFileName) {
        try {
            this.docToCreate = docToCreate;
            checkPDFFileExist(PDFFileName);
            this.docToCreate.save(PDFFileName);
            this.docToCreate.close();
        }catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void checkPDFFileExist(String PDFFileName) {
        File oldFile = new File(PDFFileName);
        if (oldFile.exists()) {
            oldFile.delete();
        }
    }

    // get PDF-Reports path
    private static String getUserDirectory() {
        String filePath;
        filePath = System.getProperty("user.home");
        filePath += "\\Desktop\\Employee_Cost_Report";
        return filePath;
    }

    // create PDF-Reports Directory on disk
    private static void createPdfDir(String pdfReportDir) {
        File dir = new File(pdfReportDir);
        dir.mkdir();
    }
}
