package com.livestream.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
    Cloudinary cloudinary;

    public List<String> uploadFile(MultipartFile[] files, String folderName) throws IOException {
        List<String> secureUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folderName,
                    "resource_type", "auto"
            );

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

            // Thêm URL của file đã upload vào danh sách
            String secureUrl = (String) uploadResult.get("secure_url");
            secureUrls.add(secureUrl);
        }

        return secureUrls;
    }
}
