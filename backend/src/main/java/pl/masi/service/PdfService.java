package pl.masi.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.masi.entity.Question;
import pl.masi.entity.Test;
import pl.masi.exception.PdfException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService  {

    @Autowired
    private TestService testService;

    public byte[] pdfCreator(Test test) throws PdfException {
        try {
            Document doc = new Document();

            ByteArrayOutputStream byt = new ByteArrayOutputStream();

            PdfWriter.getInstance(doc, byt);
            doc.open();
            BaseFont times_roman = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.TIMES_ROMAN, BaseFont.EMBEDDED);
            Font font = new Font(times_roman, 12, Font.NORMAL);
            for (int i = 0; i < test.getQuestions().size(); i++) {
                doc.add(new Paragraph(i + 1 + ". " + test.getQuestions().get(i).getContent(), font));
                doc.add(Chunk.NEWLINE);

                String typeName = test.getQuestions().get(i).getType().name();
                if (typeName.equals(String.valueOf(Question.Type.OPEN))) {
                    for (int j = 0; j < 4; j++) {
                        doc.add(Chunk.NEWLINE);
                    }
                } if (typeName.equals(String.valueOf(Question.Type.CHOICE))){
                    test.getQuestions().get(i).getAvailableChoices();
                    doc.add(Chunk.NEWLINE);
                }
                if (typeName.equals(String.valueOf(Question.Type.SCALE))){
                    test.getQuestions().get(i).getAvailableRange();
                    doc.add(Chunk.NEWLINE);
                }
                if(typeName.equals(String.valueOf(Question.Type.NUMBER))){
                    doc.add(Chunk.NEWLINE);
                }
                doc.add(Chunk.NEWLINE);
            }
            doc.close();
            byt.close();
            return byt.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new PdfException();
        }
    }




}
