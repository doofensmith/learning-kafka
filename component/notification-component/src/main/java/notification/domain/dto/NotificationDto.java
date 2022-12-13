package notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationDto implements Serializable {

    private static final long serialVersionUID = -2056390012487463835L;

    private Long id;
    private String content;
    private String publisher;
    private String receiver;

}
