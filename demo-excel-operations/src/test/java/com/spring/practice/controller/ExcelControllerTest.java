package com.spring.practice.controller;

import com.spring.practice.Utility.ExcelGenerator;
import com.spring.practice.entity.Question;
import com.spring.practice.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExcelControllerTest {

    @Mock
    private ExcelService excelService;

    @InjectMocks
    private ExcelController excelController;

    @Test
    public void testImportDataFromExcelToDB() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        List<Question> questionList = Arrays.asList(new Question(), new Question());
        when(excelService.saveFromExcelToDB(file)).thenReturn(questionList);

        ResponseEntity<List<Question>> responseEntity = excelController.importDataFromExcelToDB(file);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questionList, responseEntity.getBody());
    }

    @Test
    public void testDownloadExcel() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        List<Question> questionList = Arrays.asList(new Question(), new Question());
        ExcelGenerator excelGenerator = mock(ExcelGenerator.class);
        doNothing().when(excelGenerator).generateExcelFile(response);
        when(excelService.getAllQuestions()).thenReturn(questionList);

        excelController.downloadExcel(response);

        verify(response).setContentType("application/octet-stream");
        verify(response).setHeader(eq("Content-Disposition"), anyString());
        verify(excelService).getAllQuestions();
        verify(excelGenerator).generateExcelFile(response);
    }
}
