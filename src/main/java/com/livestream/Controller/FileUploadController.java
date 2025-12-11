package com.livestream.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.Service.CloudinaryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}files")
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUploadController {
    CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ApiResponse<List<String>> uploadImage(@RequestParam("file") MultipartFile[] file,
            @RequestParam("folder") String folderName) throws IOException {
        return ApiResponse.<List<String>>builder()
                .result(cloudinaryService.uploadFile(file, folderName))
                .build();
    }
}
