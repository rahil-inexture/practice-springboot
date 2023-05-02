package com.spring.practice.service;

import com.spring.practice.Utility.ExcelUtility;
import com.spring.practice.entity.Question;
import com.spring.practice.repository.QuestionRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private Logger logger = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    private QuestionRespository questionRespository;

    @Transactional
    public List<Question> saveFromExcelToDB(MultipartFile file) {
        try {
            List<Question> lstOfQuestion = ExcelUtility.excelToQuestion(file.getInputStream());
            return questionRespository.saveAll(lstOfQuestion);
        } catch (IOException ioException) {
            throw new RuntimeException(String.format("failed to store excel data {} %s", ioException.getMessage()));
        }
    }

    public List<Question> getAllQuestions() {
        return questionRespository.findAll();
    }
}
