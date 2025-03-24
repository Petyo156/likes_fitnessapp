package org.example.likes.web;

import org.example.likes.service.LikeService;
import org.example.likes.web.controller.LikeController;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
public class LikeControllerApiTest {

    @MockitoBean
    private LikeService likeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postLikeProgram_returns201AndCorrectDtoStructure() throws Exception {
        LikeProgramRequest requestDto = LikeProgramRequest.builder()
                .programId(UUID.randomUUID().toString())
                .likedByUserId(UUID.randomUUID().toString())
                .programOwnerId(UUID.randomUUID().toString())
                .build();

        LikeProgramResponse responseDto = LikeProgramResponse.builder()
                .programId(UUID.randomUUID())
                .likedByUserId(UUID.randomUUID())
                .programOwnerId(UUID.randomUUID())
                .build();

        when(likeService.likeProgram(any())).thenReturn(responseDto);

        MockHttpServletRequestBuilder request = post("/api/v1/likes/program")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(requestDto));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("programId").isNotEmpty())
                .andExpect(jsonPath("likedByUserId").isNotEmpty())
                .andExpect(jsonPath("programOwnerId").isNotEmpty());
    }

    @Test
    void getLikedProgramsByUser_returns200AndListOfProgramIds() throws Exception {
        String userId = UUID.randomUUID().toString();
        List<String> programIds = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        when(likeService.getAllLikedProgramIdsForUser(userId)).thenReturn(programIds);

        MockHttpServletRequestBuilder request = get("/api/v1/likes/programs/" + userId);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]").isNotEmpty())
                .andExpect(jsonPath("[1]").isNotEmpty());
    }
}
