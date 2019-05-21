package pl.masi.service;

import pl.masi.entity.Test;
import pl.masi.exception.PdfException;

public interface PdfService  {
    byte[] pdfCreator(Test test) throws PdfException;
}
