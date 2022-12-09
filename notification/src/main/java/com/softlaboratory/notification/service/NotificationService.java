package com.softlaboratory.notification.service;

import notification.domain.dto.NotificationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    ResponseEntity<Object> getNotification(String receiverReq, Integer page, Integer size);
    ResponseEntity<Object> pushNotification(NotificationDto request);

}
