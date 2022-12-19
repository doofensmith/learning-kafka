package notification.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationRequest implements Serializable {

    private static final long serialVersionUID = 7601736600455863051L;

    private String content;
    private String publisher;
    private String receiver;

}
