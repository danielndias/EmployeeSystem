package com.danieldias.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;


public class CreationPDF {

    private PDAcroForm acro;
    private PDDocument doc;
    private PDDocumentCatalog cat;
    private String source;
    private File fileLoaded;
    private PDField fieldNumber;
    private PDField fieldCost;
    private String sourcePDFTemplate;


    public CreationPDF(String sourcePDFTemplate) {
        this.source = "pdf/Report-Template.pdf";
        this.sourcePDFTemplate = source;

        this.fileLoaded = new File(this.sourcePDFTemplate);
        try {
            this.doc = PDDocument.load(this.fileLoaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cat = this.doc.getDocumentCatalog();
    }

    public PDDocument getDoc() {
        return this.doc;
    }

    public void writeTextBoxValue(String numberBoxName, int numberBoxValue, String costBoxName, String costBoxValue) {
        try {
            String nBoxName = numberBoxName;
            String nBoxValue = String.valueOf(numberBoxValue);
            String cBoxName = costBoxName;
            String cBoxValue = costBoxValue;
            this.acro = this.cat.getAcroForm();
            this.fieldNumber = acro.getField(nBoxName);
            this.fieldNumber.setReadOnly(true);
            this.fieldNumber.setRequired(false);
            this.acro.getField(nBoxName).setValue(nBoxValue);
            this.fieldCost = acro.getField(cBoxName);
            this.fieldCost.setReadOnly(true);
            this.fieldCost.setRequired(false);
            this.acro.getField(cBoxName).setValue(cBoxValue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
