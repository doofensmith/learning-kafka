package notification.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import notification.constant.NotificationConstant;
import notification.domain.dto.NotificationDto;
import notification.domain.request.NotificationRequest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Objects;

@AllArgsConstructor
public class NotificationProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String topic, NotificationRequest request) throws JsonProcessingException {
        String publisher = Objects.requireNonNullElse(request.getPublisher(), NotificationConstant.DEFAULT_PUBLISHER);
        String receiver = Objects.requireNonNullElse(request.getReceiver(), NotificationConstant.DEFAULT_RECEIVER);

        NotificationDto dto = NotificationDto.builder()
                .receiver(receiver)
                .publisher(publisher)
                .content(request.getContent())
                .build();

        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);

    }

    public void sendNotification(String topic, String content) throws JsonProcessingException {
        String publisher = NotificationConstant.DEFAULT_PUBLISHER;
        String receiver = NotificationConstant.DEFAULT_RECEIVER;

        NotificationDto dto = NotificationDto.builder()
                .receiver(receiver)
                .publisher(publisher)
                .content(content)
                .build();

        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);

    }

    public void sendNotification(String topic, String content, String receiver) throws JsonProcessingException {
        String publisher = NotificationConstant.DEFAULT_PUBLISHER;

        NotificationDto dto = NotificationDto.builder()
                .receiver(receiver)
                .publisher(publisher)
                .content(content)
                .build();

        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);

    }

    public void sendNotification(String topic, String content, String receiver, String publisher) throws JsonProcessingException {

        NotificationDto dto = NotificationDto.builder()
                .receiver(receiver)
                .publisher(publisher)
                .content(content)
                .build();

        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);

    }

}
