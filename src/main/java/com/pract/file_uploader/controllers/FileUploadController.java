package com.pract.file_uploader.controllers;

import com.pract.file_uploader.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/in/{app_name}/{account_id}")
    public ResponseEntity<String> uploadFile(
            @PathVariable("app_name") String appName,
            @PathVariable("account_id") String accountId,
            @RequestParam("file") MultipartFile file) {

        try {
            fileUploadService.uploadFile(appName, accountId, file);
            return ResponseEntity.ok("File uploaded");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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