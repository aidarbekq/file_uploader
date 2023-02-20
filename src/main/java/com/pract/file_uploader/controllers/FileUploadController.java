package com.pract.file_uploader.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
public class FileUploadController {

    @Value("${custom.basedir}")
    private String baseDir;

    @Value("${custom.apps}")
    private List<String> appNames;


    @PostMapping("/in/{app_name}/{account_id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable("app_name") String appName,
            @PathVariable("account_id") String accountId,
            @RequestParam("file") MultipartFile file) {

        if (!appNames.contains(appName)) {
            return ResponseEntity.badRequest().body("Invalid app name");
        }

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        String filePath = String.format("%s/%s/%s/%s_%s", baseDir, appName, date, accountId, uuid);
        Path uploadPath = Paths.get(filePath);
        System.out.println("filename: " + fileName);
        System.out.println("filepath: " + filePath);
        System.out.println("path_parent: " + Paths.get(filePath).getParent());

        try (InputStream inputStream = file.getInputStream()){
            Files.createDirectories(Paths.get(filePath));
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("File uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}
/*
      задача:
      реализовать сервис который будет принимать на ендпоин файлы, по одному. один файл=один вызов.
      файлы должны сохранятся в конкретную папку указанную в конфиге. под ключем custom.basedir

      ендпоинт иметь следующий вид
      POST http://localhost:4401/in/{app_name}/{account_id}

      файл должен сохранятся на сервере в папке $basedir/app_name/yyyy_dd_MM/account_id+uuidRand.*

      {app_name} - список представленный в конфиге сервиса, для старта вбить custom.apps = mobile,admin, web

 */