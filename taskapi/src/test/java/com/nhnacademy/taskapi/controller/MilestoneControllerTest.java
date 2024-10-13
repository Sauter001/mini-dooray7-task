package com.nhnacademy.taskapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.entity.view.MilestoneView;
import com.nhnacademy.taskapi.service.MilestoneService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MileStoneController.class)
class MilestoneControllerTest {
    private final String PROJECTS_PATH = "/projects";
    // mock object
    List<MilestoneView> mockMilestones = List.of(
            mock(MilestoneView.class), mock(MilestoneView.class));
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MilestoneService milestoneService;


    @Test
    void getMilestones() throws Exception {
        when(milestoneService.getMilestones(1L)).thenReturn(mockMilestones);
        MvcResult mvcResult = mockMvc.perform(get(PROJECTS_PATH + "/1/milestones")
                                                      .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        DefaultDto<List<MilestoneView>> dto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertEquals(2, dto.data().size());
    }
}
