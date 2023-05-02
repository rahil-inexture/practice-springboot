package com.spring.practice.Utility;

import com.spring.practice.entity.Question;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExcelGenerator {

    private List<Question> questionList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<Question> questionList) {
        this.questionList = questionList;
        workbook = new XSSFWorkbook();
    }

    private void setHeaders() {
        sheet = workbook.createSheet("Question Sheet");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        createCell(row, 0, "QUESTION_ID", style);
        createCell(row, 1, "CATEGORY_ID", style);
        createCell(row, 2, "QUIZ_ID", style);
        createCell(row, 3, "QUESTION", style);
        createCell(row, 4, "OPTIONS", style);
        createCell(row, 5, "QUESTION_TYPE", style);
    }

    private void createCell(Row row, int columnCnt, Object cellValue, CellStyle style) {
        sheet.autoSizeColumn(columnCnt);
        Cell cell = row.createCell(columnCnt);
        if (cellValue instanceof Integer) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof Long) {
            cell.setCellValue((Long) cellValue);
        } else if (cellValue instanceof Boolean) {
            cell.setCellValue((Boolean) cellValue);
        }
        cell.setCellStyle(style);
    }

    private void writeExcel() {
        AtomicInteger rowCount = new AtomicInteger(1);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        questionList.forEach(question -> {
            Row row = sheet.createRow(rowCount.getAndIncrement());
            createCell(row, 0, question.getId(), style);
            createCell(row, 1, question.getCategoryId(), style);
            createCell(row, 2, question.getQuizId(), style);
            createCell(row, 3, question.getQuestion(), style);
            createCell(row, 4, question.getOptions(), style);
            if (question.getQuestionType() != null)
                createCell(row, 5, question.getQuestionType().name(), style);
        });
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        setHeaders();
        writeExcel();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}