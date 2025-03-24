package org.example.likes.web;

import org.example.likes.model.Notification;
import org.example.likes.service.NotificationService;
import org.example.likes.web.controller.NotificationController;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.likes.web.mapper.DtoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerApiTest {

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postNotifyUser_returns201AndCorrectDtoStructure() throws Exception {
        NotifyUserRequest requestDto = NotifyUserRequest.builder()
                .receiverId(UUID.randomUUID().toString())
                .senderId(UUID.randomUUID().toString())
                .message("You have a new like!")
                .build();

        NotifyUserResponse responseDto = NotifyUserResponse.builder()
                .receiverId(requestDto.getReceiverId())
                .senderId(requestDto.getSenderId())
                .message(requestDto.getMessage())
                .date("2025-03-24")
                .build();

        when(notificationService.notifyUserForLikedProgram(any())).thenReturn(responseDto);

        MockHttpServletRequestBuilder request = post("/api/v1/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(requestDto));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("receiverId").value(requestDto.getReceiverId()))
                .andExpect(jsonPath("senderId").value(requestDto.getSenderId()))
                .andExpect(jsonPath("message").value(requestDto.getMessage()))
                .andExpect(jsonPath("date").isNotEmpty());
    }

    @Test
    void getNotificationsForUser_returns200AndListOfNotifications() throws Exception {
        String userId = UUID.randomUUID().toString();

        Notification notification1 = Notification.builder()
                .id(UUID.randomUUID())
                .senderId(UUID.randomUUID())
                .receiverId(UUID.randomUUID())
                .message("You have a new like!")
                .createdAt(LocalDateTime.now())
                .build();
        Notification notification2 = Notification.builder()
                .id(UUID.randomUUID())
                .senderId(UUID.randomUUID())
                .receiverId(UUID.randomUUID())
                .message("You have a new like 2!")
                .createdAt(LocalDateTime.now())
                .build();

        when(notificationService.getNotificationsForUser(userId)).thenReturn(List.of(notification1, notification2));

        List<NotifyUserResponse> responseList = List.of(
                new NotifyUserResponse(notification1.getReceiverId().toString(),
                        notification1.getSenderId().toString(),
                        notification1.getMessage(),
                        notification1.getCreatedAt().toString()),
                new NotifyUserResponse(notification2.getReceiverId().toString(),
                        notification2.getSenderId().toString(),
                        notification2.getMessage(),
                        notification2.getCreatedAt().toString())
        );

        try (MockedStatic<DtoMapper> mockedMapper = mockStatic(DtoMapper.class)) {
            mockedMapper.when(() -> DtoMapper.fromNotifications(any())).thenReturn(responseList);

            MockHttpServletRequestBuilder request = get("/api/v1/notifications/" + userId);

            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("[0].receiverId").value(responseList.get(0).getReceiverId()))
                    .andExpect(jsonPath("[0].senderId").value(responseList.get(0).getSenderId()))
                    .andExpect(jsonPath("[1].receiverId").value(responseList.get(1).getReceiverId()))
                    .andExpect(jsonPath("[1].senderId").value(responseList.get(1).getSenderId()));
        }
    }

}