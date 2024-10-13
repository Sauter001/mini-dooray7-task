package com.nhnacademy.taskapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.dto.request.MilestonePostDto;
import com.nhnacademy.taskapi.dto.request.MilestonePutDto;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import com.nhnacademy.taskapi.service.MilestoneService;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MileStoneController.class)
class MilestoneControllerTest {
    private final String PROJECTS_PATH = "/projects";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MilestoneService milestoneService;

    @Test
    void getMilestones() throws Exception {
        // mock object
        MilestoneView milestone1 = new MilestoneViewImpl();
        MilestoneView milestone2 = new MilestoneViewImpl();
        List<MilestoneView> mockMilestones = List.of(milestone1, milestone2);
        when(milestoneService.getMilestones(1L)).thenReturn(mockMilestones);

        MvcResult mvcResult = mockMvc.perform(get(PROJECTS_PATH + "/1/milestones")
                                                      .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<List<MilestoneViewImpl>> dto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(200, dto.code());
        assertEquals(2, dto.data().size());
    }

    @Test
    void getMilestones_notFound() throws Exception {
        when(milestoneService.getMilestones(1L)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(PROJECTS_PATH + "/1/milestones")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void postMilestone() throws Exception {
        MilestonePostDto postDto = new MilestonePostDto("name");
        doNothing().when(milestoneService).post(1L, postDto);
        mockMvc.perform(post(PROJECTS_PATH + "/1/milestones")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void postMilestone_projectNotFound() throws Exception {
        MilestonePostDto postDto = new MilestonePostDto("name");
        doThrow(ResourceNotFoundException.class).when(milestoneService).post(1L, postDto);
        mockMvc.perform(post(PROJECTS_PATH + "/1/milestones")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMilestone() throws Exception {
        MilestoneView milestone1 = new MilestoneViewImpl();
        MilestonePutDto putDto = new MilestonePutDto("name");
        when(milestoneService.update(1L, 1L, putDto)).thenReturn(milestone1);

        MvcResult result = mockMvc.perform(put(PROJECTS_PATH + "/1/milestones/1")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(putDto)))
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<MilestoneViewImpl> dto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertEquals(200, dto.code());
        assertEquals(milestone1, dto.data());
    }

    @Test
    void updateMilestone_notFound() throws Exception {
        MilestonePutDto putDto = new MilestonePutDto("name");
        when(milestoneService.update(1L, 1L, putDto)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(PROJECTS_PATH + "/1/milestones/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(putDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMilestone() throws Exception {
        doNothing().when(milestoneService).delete(1L, 1L);
        mockMvc.perform(delete(PROJECTS_PATH + "/1/milestones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMilestone_notFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(milestoneService).delete(1L, 1L);
        mockMvc.perform(delete(PROJECTS_PATH + "/1/milestones/1"))
                .andExpect(status().isNotFound());
    }

    @NoArgsConstructor
    @EqualsAndHashCode
    static class MilestoneViewImpl implements MilestoneView {
        @Override
        public Long getMileStoneId() {
            return 1L;
        }

        @Override
        public String getProgress() {
            return "마일스톤";
        }
    }
}
