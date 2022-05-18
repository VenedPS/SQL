package ua.com.foxminded.task7sql.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderTest {

    private FileReader fileReader = new FileReader();
    
    @TempDir
    Path tempFolder;
    
    @Test
    void readFiles_shouldThrowIllegalArgumentException_whenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readFiles(null);
        });
    }

    @Test
    void readFiles_shouldThrowIllegalArgumentException_whenInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readFiles("");
        });
    }

    @Test
    void readFiles_shouldThrowIllegalArgumentException_whenInputDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readFiles("/test2.log");
        });
    }

    @Test
    void readFiles_shouldThrowIllegalArgumentException_whenInputIsDirectory() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readFiles("/");
        });
    }
    
    @Test
    void readFiles_shouldReturnReadData_whenInputFileExist(@TempDir Path tempFolder) {
        List<String> expected = new ArrayList<>();
        expected.add("Biology_Biology");
        
        Path tempFile = tempFolder.resolve("tempFile.txt");
        try {
            FileWriter fileWriter = new FileWriter(tempFile.toFile());
            fileWriter.write("Biology_Biology");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> actual = fileReader.readFiles(tempFile.toString());

        if(tempFile.toFile().delete()) {
            tempFolder.toFile().delete();
        }
        
        assertEquals(expected, actual);
    }
    @Test
    void readPropertiesFiles_shouldThrowIllegalArgumentException_whenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readPropertiesFiles(null);
        });
    }
    
    @Test
    void readPropertiesFiles_shouldThrowIllegalArgumentException_whenInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readPropertiesFiles("");
        });
    }
    
    @Test
    void readPropertiesFiles_shouldThrowIllegalArgumentException_whenInputDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readPropertiesFiles("/test2.log");
        });
    }
    
    @Test
    void readPropertiesFiles_shouldThrowIllegalArgumentException_whenInputIsDirectory() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileReader.readPropertiesFiles("/");
        });
    }
    
    @Test
    void readPropertiesFiles_shouldReturnReadData_whenInputFileExist(@TempDir Path tempFolder) {
        Path tempFile = tempFolder.resolve("tempFile.txt");
        try (FileWriter fileWriter = new FileWriter(tempFile.toFile())) {
            fileWriter.write("Bio = Biology_Biology");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Properties expected = new Properties();
        try (FileInputStream fis = new FileInputStream(tempFile.toString())) {
            expected.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Properties actual = fileReader.readPropertiesFiles(tempFile.toString());
        
        if(tempFile.toFile().delete()) {
            tempFolder.toFile().delete();
        }
        
        assertEquals(expected, actual);
    }

}
