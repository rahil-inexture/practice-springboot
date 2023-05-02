package com.practice.spring.utils;

import com.practice.spring.entity.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommonUtils {

    public static Map<String, Object> response(Page<Tutorial> pageTuts) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        Map<String, Object> response = new HashMap<>();
        tutorials = pageTuts.getContent();

        response.put("tutorials", tutorials);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return response;
    }
}
