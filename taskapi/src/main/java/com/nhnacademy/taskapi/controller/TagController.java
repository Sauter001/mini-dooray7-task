package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.service.ProjectService;
import com.nhnacademy.taskapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tags")
public class TagController {


    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    //태그 등록
    @PostMapping
    public ResponseEntity<DefaultDto> saveTag(@RequestBody TagDto tagDto,
                                              @PathVariable Long projectId
    ) {
        tagService.saveTag(projectId, tagDto);
        DefaultDto dto = new DefaultDto(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //태그 조회
}

