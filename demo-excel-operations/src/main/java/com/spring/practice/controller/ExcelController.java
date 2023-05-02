package com.spring.practice.controller;

import com.spring.practice.Utility.ExcelGenerator;
import com.spring.practice.entity.Question;
import com.spring.practice.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    private Logger logger = LoggerFactory.getLogger(ExcelController.class);
    @Autowired
    ExcelService excelService;

    @PostMapping("/uploadExcel")
    public ResponseEntity<List<Question>> importDataFromExcelToDB(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(excelService.saveFromExcelToDB(file));
    }

    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=question" + currentDateTime + ".xlsx");

        List<Question> lstQuestions = excelService.getAllQuestions();
        ExcelGenerator excelGenerator = new ExcelGenerator(lstQuestions);
        excelGenerator.generateExcelFile(response);
    }
}
