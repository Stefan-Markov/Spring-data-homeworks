package com.example.demo.utils;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <T> void marshalToFile(String filePath, T rootDto) throws JAXBException;
    public <T> T unmarshalFromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException ;
}
