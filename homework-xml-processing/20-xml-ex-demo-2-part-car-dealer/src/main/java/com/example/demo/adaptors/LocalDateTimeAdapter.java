package com.example.demo.adaptors;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// The xml file holds a String in format yyyy-MM-dd'T'HH:mm:ss
// How to convert xml format yyyy-MM-dd'T'HH:mm:ss to LocalDateTime
@Component
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    // Get a String from a file and convert it to LocalDateTime
    @Override
    public LocalDateTime unmarshal(String s) throws Exception {
        return LocalDateTime
                .parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    // Get a LocalDateTime from a file and convert it to String
    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toString();
    }
}
