package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.Template;
import com.dalona.waa.dto.ChatMessage;
import java.util.List;
import lombok.Data;

@Data
public class TemplateResDto {

    private Integer id;

    private Integer organizationId;

    private String title;

    private String system;

    private List<ChatMessage> chat;

    public TemplateResDto(Template template) {
        this.id = template.getId();
        this.organizationId = template.getOrganizationId();
        this.title = template.getTitle();
        this.system = template.getSystem();
        this.chat = template.getChat();
    }
}
