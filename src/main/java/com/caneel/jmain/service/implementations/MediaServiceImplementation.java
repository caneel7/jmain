package com.caneel.jmain.service.implementations;

import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.Media;
import com.caneel.jmain.repository.MediaRepository;
import com.caneel.jmain.service.MediaService;
import com.caneel.jmain.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MediaServiceImplementation implements MediaService {

    @Autowired
    private S3Service s3service;

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public ResponseEntity<ApiResponse<String>> uploadTos3(MultipartFile media)
    {
        try{

            File mediaToUpload = multipartToFile(media);

            String id = s3service.uploadFile(mediaToUpload);

            Media newMedia = new Media();
            newMedia.setId(id);
            newMedia.setDirectory("/media/" + id);
            newMedia.setValue(mediaToUpload.getName());

            mediaRepository.save(newMedia);

            return ResponseEntity.ok().body(new ApiResponse<>(true, "Success", newMedia.getURL()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false,e.getMessage()));
        }
    }

    public File multipartToFile(MultipartFile file) throws IOException {
        File tmpFile = File.createTempFile(file.getOriginalFilename(),file.getOriginalFilename());
        file.transferTo(tmpFile);
        return tmpFile;
    }
}
