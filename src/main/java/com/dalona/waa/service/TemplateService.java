package com.dalona.waa.service;

import com.dalona.waa.domain.Template;
import com.dalona.waa.dto.responseDto.TemplateResDto;
import com.dalona.waa.repository.TemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateResDto getTemplateById(Integer id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TEMPLATE_NOT_FOUND"));
        return new TemplateResDto(template);
    }
}
