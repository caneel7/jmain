package com.caneel.jmain.service;

import com.caneel.jmain.dto.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    ResponseEntity<ApiResponse<String>> uploadTos3(MultipartFile media);
}
