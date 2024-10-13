package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.dto.request.MilestonePostDto;
import com.nhnacademy.taskapi.dto.request.MilestonePutDto;
import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.ProjectStatus;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {
    // mock 객체
    List<MilestoneView> mockMilestones = List.of(mock(MilestoneView.class), mock(MilestoneView.class));
    Project mockProject = mock(Project.class);
    Milestone mockMilestone = mock(Milestone.class);
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private MilestoneService milestoneService;

    @Test
    void getMilestones() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        when(milestoneRepository.findAllByProjectProjectId(1L)).thenReturn(mockMilestones);
        List<MilestoneView> result = milestoneService.getMilestones(1L);

        assertEquals(2, result.size());
        assertEquals(mockMilestones, result);
    }

    @Test
    void getMilestones_notFound() {
        when(projectRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> milestoneService.getMilestones(1L));
    }

    @Test
    void post() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(any(Milestone.class));
        milestoneService.post(1L, new MilestonePostDto("name"));
    }

    @Test
    void post_projectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> milestoneService.post(1L, new MilestonePostDto("name")));
    }

    @Test
    void update() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                new Milestone(1L, null, "name"),
                mock(Milestone.class)
        ), null);
        MilestoneView mockMilestoneView = mock(MilestoneView.class);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectWithMilestone));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(mockMilestone));
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(mock(Milestone.class));
        when(milestoneRepository.findByMileStoneId(anyLong())).thenReturn(Optional.of(mockMilestoneView));
        MilestoneView actual = milestoneService.update(1L, 1L, new MilestonePutDto("name"));
        assertEquals(mockMilestoneView, actual);
    }

    @Test
    void update_projectIdNotFound() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                mock(Milestone.class),
                mock(Milestone.class)
        ), null);
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> milestoneService.update(1L, 1L, new MilestonePutDto("name"
        )));
    }

    @Test
    void update_milestoneNotFound() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                new Milestone(1L, null, "name"),
                mock(Milestone.class)
        ), null);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectWithMilestone));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> milestoneService.update(1L, 1L, new MilestonePutDto("name")
                )
        );
    }

    @Test
    void update_findUpdatedMilestoneFailed() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                new Milestone(1L, null, "name"),
                mock(Milestone.class)
        ), null);
        MilestoneView mockMilestoneView = mock(MilestoneView.class);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectWithMilestone));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(mockMilestone));
        when(milestoneRepository.save(any(Milestone.class))).thenReturn(mock(Milestone.class));
        when(milestoneRepository.findByMileStoneId(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> milestoneService.update(1L, 1L, new MilestonePutDto("name")
                )
        );
    }

    @Test
    void delete() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                new Milestone(1L, null, "name"),
                mock(Milestone.class)
        ), null);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectWithMilestone));
        doNothing().when(milestoneRepository).deleteById(anyLong());
        milestoneService.delete(1L, 1L);
    }

    @Test
    void delete_projectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> milestoneService.delete(1L, 1L));
    }

    @Test
    void delete_milestoneInProjectNotFound() {
        Project projectWithMilestone = new Project(
                1L, new Account(1L), "name", ProjectStatus.COMPLETE, List.of(
                mock(Milestone.class),
                mock(Milestone.class)
        ), null);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectWithMilestone));
        assertThrows(ResourceNotFoundException.class, () -> milestoneService.delete(1L, 1L));
    }
}
