package com.pract.file_uploader.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${custom.basedir}")
    private String baseDir;

    @Value("${custom.apps}")
    private List<String> appNames;

    public void uploadFile(String appName, String accountId, MultipartFile file)
            throws IOException {

        if (!appNames.contains(appName)) {
            throw new IllegalArgumentException("Invalid app name");
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String uuid = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        String filePath = String.format("%s/%s/%s/%s_%s", baseDir, appName, date, accountId, uuid);
        Path uploadPath = Paths.get(filePath);

        try (InputStream inputStream = file.getInputStream()){
            Files.createDirectories(uploadPath.getParent());
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to upload file", e);
        }
    }
}