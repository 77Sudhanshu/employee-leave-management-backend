package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.sudhanshu.employeeleavemanagementsystem.dto.ChangePasswordRequest;

import java.nio.file.Path;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public Employee getMyProfile() {
        return profileService.getMyProfile();
    }
    @PutMapping
public Employee updateProfile(@RequestBody Employee employee) {

    return profileService.updateProfile(employee);

}

    @PostMapping("/upload-image")
public Employee uploadProfileImage(
        @RequestParam("file") MultipartFile file) {

    return profileService.uploadProfileImage(file);
}
@GetMapping("/image/{fileName}")
public ResponseEntity<Resource> getProfileImage(
        @PathVariable String fileName) throws Exception {

    Path path = profileService.getProfileImage(fileName);

    Resource resource = new UrlResource(path.toUri());

    if (!resource.exists()) {
        throw new RuntimeException("Image not found.");
    }

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
}
@PutMapping("/change-password")
public ResponseEntity<String> changePassword(
        @RequestBody ChangePasswordRequest request) {

    String message = profileService.changePassword(request);

    return ResponseEntity.ok(message);
}
}