package com.softuni.introtospringexercise.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImp implements FileUtil {

    @Override
    public String[] readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        Set<String> result = new LinkedHashSet<>();

        while ((line = reader.readLine()) != null) {
            if (!"".equals(line)) {
                result.add(line);
            }
        }

        return result.toArray(String[]::new);
    }
}
