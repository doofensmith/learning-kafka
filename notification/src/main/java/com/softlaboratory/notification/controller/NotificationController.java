package com.softlaboratory.notification.controller;

import com.softlaboratory.notification.service.NotificationService;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getNotification(
            @RequestParam(value = "receiver", required = false) String receiverReq,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        try {
            return notificationService.getNotification(receiverReq, page, size);
        }catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAuthority(ADMIN)")
    @PostMapping(value = "/push")
    public ResponseEntity<Object> pushNotification(@RequestBody NotificationDto request) {
        try {
            return notificationService.pushNotification(request);
        }catch (Exception e) {
            throw e;
        }
    }

}
