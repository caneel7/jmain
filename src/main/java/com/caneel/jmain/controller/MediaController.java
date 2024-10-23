package com.caneel.jmain.controller;

import com.caneel.jmain.dto.requests.UserRequestDto;
import com.caneel.jmain.dto.responses.ApiResponse;
import com.caneel.jmain.model.Media;
import com.caneel.jmain.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/media", produces = "application/json")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> register(@RequestParam("media") MultipartFile media)
    {
        return mediaService.uploadTos3(media);
    }



}
