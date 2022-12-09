package com.softlaboratory.notification.service.impl;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.notification.service.NotificationService;
import notification.domain.dao.NotificationDao;
import notification.domain.dto.NotificationDto;
import notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public ResponseEntity<Object> getNotification(String receiverReq, Integer page, Integer size) {
        String receiver = Objects.requireNonNullElse(receiverReq, "");
        Pageable pageable = PageRequest.of(
                Objects.requireNonNullElse(page, 0),
                Objects.requireNonNullElse(size, 5)
        );
        Page<NotificationDao> notificationPage = notificationRepository.findByReceiverEqualsOrderByIdDesc(receiver, pageable);
        List<NotificationDao> notificationDaoList = notificationPage.getContent();
        List<NotificationDto> dto = new ArrayList<>();
        for (NotificationDao dao : notificationDaoList) {
            dto.add(NotificationDto.builder()
                    .id(dao.getId())
                    .content(dao.getContent())
                    .receiver(dao.getReceiver())
                    .publisher(dao.getPublisher())
                    .build());
        }

        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), dto);
    }

    @Override
    public ResponseEntity<Object> pushNotification(NotificationDto request) {
        NotificationDao dao = new NotificationDao();
        dao.setContent(request.getContent());
        if (!request.getPublisher().isEmpty() && !request.getPublisher().isBlank()) {
            dao.setPublisher(request.getPublisher());
        }
        if (!request.getReceiver().isEmpty() && !request.getReceiver().isBlank()) {
            dao.setReceiver(request.getReceiver());
        }

        dao = notificationRepository.save(dao);

        return ResponseUtil.build(HttpStatus.OK, "Notification created.", null);
    }

}
