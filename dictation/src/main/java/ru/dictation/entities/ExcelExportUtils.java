package ru.dictation.entities;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelExportUtils {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private List<QuestionStat> questionStatList;

    private List<UserAnswer> userAnswerList;


    public ExcelExportUtils(List<QuestionStat> questionStats, List<UserAnswer> userAnswerList) {
        this.userAnswerList = userAnswerList;
        this.questionStatList = questionStats;
        workbook = new XSSFWorkbook();
    }


    public void exportQuestionDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRowQuestions();
        writeUserQuestionData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRowAnswers();
        writeUserAnswerData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeUserQuestionData() {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        for (QuestionStat questionStat : questionStatList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, questionStat.getId(), style);
            createCell(row, columnCount++, questionStat.getQuestion(), style);
            createCell(row, columnCount++, questionStat.getAllQuantity(), style);
            createCell(row, columnCount++, questionStat.getRightQuantity(), style);
        }
    }

    private void writeUserAnswerData() {

        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        for (UserAnswer userAnswer : userAnswerList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, userAnswer.getId(), style);
            createCell(row, columnCount++, userAnswer.getUsername(), style);
            createCell(row, columnCount++, userAnswer.getAge(), style);
            createCell(row, columnCount++, userAnswer.getGender(), style);
            createCell(row, columnCount++, userAnswer.getRegion(), style);
            createCell(row, columnCount++, userAnswer.getCity(), style);
            createCell(row, columnCount++, userAnswer.getMail(), style);
            createCell(row, columnCount++, userAnswer.getAccuracy(), style);
            createCell(row, columnCount++, userAnswer.getIdentifier(), style);
            createCell(row, columnCount++, userAnswer.getDateOfCreated().toString(), style);
        }
    }


    private void createHeaderRowAnswers() {

        sheet = workbook.createSheet("Answers information");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(row, 0, "Answers information", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Username", style);
        createCell(row, 2, "Age", style);
        createCell(row, 3, "Gender", style);
        createCell(row, 4, "Region", style);
        createCell(row, 5, "City", style);
        createCell(row, 6, "Email", style);
        createCell(row, 7, "Accuracy (%)", style);
        createCell(row, 8, "Identifier", style);
        createCell(row, 9, "Date of created", style);
    }

    private void createHeaderRowQuestions() {

        sheet = workbook.createSheet("Question stats");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);

        createCell(row, 0, "Question stats", style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        font.setFontHeightInPoints((short) 10);
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Question", style);
        createCell(row, 2, "All", style);
        createCell(row, 3, "Right", style);
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}
