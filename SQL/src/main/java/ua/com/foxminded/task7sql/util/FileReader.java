package ua.com.foxminded.task7sql.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class FileReader {
    public List<String> readFiles(String input) {
        if (input == null || input == "") {
            throw new IllegalArgumentException("Wrong file name: " + input);
        }
        
        Path path;
        if (getClass().getResource(input) != null) {
            path = new File(getClass().getResource(input).getPath()).toPath();    
        } else {
            path = new File(input).toPath();
        }
        
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IllegalArgumentException("File not found on path: " + input);
        }
        
        List<String> filetData = new ArrayList<>();
        try {
            return filetData = Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filetData;
    }
    
    public Properties readPropertiesFiles(String input) {
        if (input == null || input == "") {
            throw new IllegalArgumentException("Wrong file name: " + input);
        }
        
        Path path;
        if (getClass().getResource(input) != null) {
            path = new File(getClass().getResource(input).getPath()).toPath();    
        } else {
            path = new File(input).toPath();
        }
        
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IllegalArgumentException("File not found on path: " + input);
        }
        
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(path.toString())) {
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }
}
