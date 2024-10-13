package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.exception.AccountNotFoundException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public TagService(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    public void saveTag(Long projectId, TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagName(tagDto.tagName());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));
        tag.setProject(project);
        tagRepository.save(tag);
    }
}
