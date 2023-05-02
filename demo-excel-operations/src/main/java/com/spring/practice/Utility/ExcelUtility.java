package com.spring.practice.Utility;

import com.spring.practice.entity.Question;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class ExcelUtility {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtility.class);

    public static List<Question> excelToQuestion(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Copy of Questions-BLOOD RELATIO");
            List<Question> questions = new ArrayList<>();
            IntStream.range(0, getNumberOfNonEmptyCells(sheet, 0) - 1)
                    .forEach(rowIndex -> {
                        //logger.info("Current Row {} " + rowIndex);
                        Question question = new Question();
                        XSSFRow row = sheet.getRow(rowIndex);
                        // skip the first row because it is a header row
                        if (rowIndex == 0) {
                            return;
                        }
                        question.setQuestionId(CommonUtil.isStrToLong((String) getValue(row.getCell(0))));
                        question.setCategoryId(CommonUtil.isStrToLong((String) getValue(row.getCell(1))));
                        question.setQuestion(getValue(row.getCell(2)).toString().replaceAll("[\n\t^]*", ""));
                        question.setOptions((String) getValue(row.getCell(3)));
                        String queType = (String) getValue(row.getCell(4));
                        Optional.ofNullable(queType)
                                .filter(StringUtil::isNotBlank)
                                .map(Question.QuestionType::valueOf)
                                .ifPresent(question::setQuestionType);
                        question.setCreatedDate(CommonUtil.isStrToDate((String) getValue(row.getCell(5))));
                        question.setModifiedDate(CommonUtil.isStrToDate((String) getValue(row.getCell(6))));
                        question.setQuizId(CommonUtil.isStrToLong((String) getValue(row.getCell(7))));
                        questions.add(question);
                    });
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Fail to parse Excel file: %s", e.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException(String.format("Fail to parse Excel file: %s", e.getMessage()));
        }
    }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        return (int) IntStream.rangeClosed(0, sheet.getLastRowNum())
                .mapToObj(sheet::getRow)
                .filter(Objects::nonNull)
                .map(row -> row.getCell(columnIndex))
                .filter(Objects::nonNull)
                .filter(cell -> cell.getCellType() != CellType.BLANK)
                .count();
    }

    private static Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case _NONE:
            default:
                return null;
        }
    }
}