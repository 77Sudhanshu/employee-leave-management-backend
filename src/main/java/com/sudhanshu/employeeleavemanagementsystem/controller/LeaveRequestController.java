package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveRequest;
import com.sudhanshu.employeeleavemanagementsystem.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Files;

import java.util.List;

@Tag(name = "Leave Request APIs", description = "Leave management operations")
@RestController
@RequestMapping("/leaveRequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Operation(summary = "Apply for leave")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Leave applied successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
})
@PostMapping
public LeaveRequest applyLeave(@RequestBody LeaveRequest request) {
    return leaveRequestService.applyLeave(request);
}

@Operation(summary = "Get all leave requests")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Leave requests fetched successfully")
})
@GetMapping
public List<LeaveRequest> getAllLeaveRequests() {
    return leaveRequestService.getAllLeaveRequests();
}

    @GetMapping("/{id}")
    public LeaveRequest getLeaveRequestById(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @PutMapping("/{id}")
    public LeaveRequest updateLeaveRequest(@PathVariable Long id,
                                           @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.updateLeaveRequest(id, leaveRequest);
    }

    @Operation(summary = "Approve leave request")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leave approved successfully"),
        @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    @PutMapping("/{id}/approve")
    public LeaveRequest approveLeave(@PathVariable Long id) {
        return leaveRequestService.approveLeave(id);
    }
@PutMapping("/{id}/reject")
public LeaveRequest rejectLeave(@PathVariable Long id) {
    return leaveRequestService.rejectLeave(id);
}
    @DeleteMapping("/{id}")
    public String deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return "Leave request deleted successfully!";
    }
    @PostMapping("/{leaveId}/upload-certificate")
public ResponseEntity<LeaveRequest> uploadMedicalCertificate(
        @PathVariable Long leaveId,
        @RequestParam("file") MultipartFile file) {

    LeaveRequest updatedLeave =
            leaveRequestService.uploadMedicalCertificate(leaveId, file);

    return ResponseEntity.ok(updatedLeave);
}
@GetMapping("/{leaveId}/download-certificate")
public ResponseEntity<Resource> downloadCertificate(@PathVariable Long leaveId) {

    try {

        Path filePath = leaveRequestService.downloadMedicalCertificate(leaveId);

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found.");
        }

        String contentType = Files.probeContentType(filePath);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
}
@GetMapping("/recent")
public List<LeaveRequest> getRecentLeaveRequests() {
    return leaveRequestService.getRecentLeaveRequests();
}
@GetMapping("/my-leaves")
public List<LeaveRequest> getMyLeaveRequests() {
    return leaveRequestService.getMyLeaveRequests();
}
}