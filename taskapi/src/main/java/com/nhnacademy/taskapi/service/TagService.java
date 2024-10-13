package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.ProjectDto;
import com.nhnacademy.taskapi.dto.request.TagDto;
import com.nhnacademy.taskapi.entity.*;
import com.nhnacademy.taskapi.exception.AccountNotFoundException;
import com.nhnacademy.taskapi.exception.AccountNotMemberException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public TagService(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    public void saveTag(Long projectId, TagDto tagDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project" + "ID" + projectId));

        Tag tag = new Tag(tagDto.tagId(), project, tagDto.tagName());
        tagRepository.save(tag);
    }

    public List<TagDto> getTagsByProjectId(Long projectId) {
        List<Tag> tags = tagRepository.findAllByProjectProjectId(projectId);
        List<TagDto> tagDtos = new ArrayList<>();
        for(Tag tag : tags){
            TagDto tagDto = new TagDto(tag.getTagId(), tag.getTagName());
            tagDtos.add(tagDto);
        }
        return tagDtos;
    }

    public TagDto updateTag(Long projectID, TagDto tagDto) {
        Tag tag = tagRepository.findById(tagDto.tagId())
                        .orElseThrow(() -> new ResourceNotFoundException("TagId: " +tagDto.tagId()));
        tag.setTagName(tagDto.tagName());
        tagRepository.save(tag);
        return new TagDto(tag.getTagId(), tag.getTagName());
    }

    public void deleteTag(Long projectId, TagDto tagDto){
        tagRepository.deleteById(tagDto.tagId());
    }


}
