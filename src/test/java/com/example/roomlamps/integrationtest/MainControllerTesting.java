package com.example.roomlamps.integrationtest;


import com.example.roomlamps.controllers.MainController;
import com.example.roomlamps.entities.Room;
import com.example.roomlamps.services.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class MainControllerTesting {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private MainController mainController;

    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void getListOfRoom() throws Exception {

        when(roomService.getListOfRooms()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(roomService).getListOfRooms();

    }

    @Test
    public void createRoom() throws Exception {
        when(roomService.createRoom(any())).thenReturn("1L");
        Room room = new Room();
        room.setCountry("China");
        room.setName("First");
        mockMvc.perform(post("/create")
                .content(mapper.writeValueAsString(room))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(roomService, times(1)).createRoom(any());
    }

    @Test
    public void getRoomById() throws Exception {
        Room room = new Room();
        room.setCountry("China");
        room.setName("First");
        when(roomService.getRoomById(1L)).thenReturn(room);
        mockMvc.perform(get("/room/1"))
                .andExpect(status().isOk());
        verify(roomService).getRoomById(1L);
    }


}
