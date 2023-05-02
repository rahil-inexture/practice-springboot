package com.spring.practice.Utility;

import com.spring.practice.entity.Question;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Component
public class CommonUtil {

    private static String isBlank(String value) {
        return StringUtil.isNotBlank(value) ? value : null;
    }

    public static Long isStrToLong(String value) {
        return StringUtil.isNotBlank(value) ? Long.parseLong(value) : 0L;
    }

    public static LocalDateTime isStrToDate(String value) {
        return StringUtil.isNotBlank(value) ? LocalDateTime.parse(value) : LocalDateTime.now();
    }


    public static Question.QuestionType of(String type) {
        return Stream.of(Question.QuestionType.values())
                .filter(t -> t.toString() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
