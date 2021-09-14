package com.example.roomlamps.controllers;

import com.example.roomlamps.entities.Room;
import com.example.roomlamps.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MainControllerTest {


    @Mock
    private RoomService roomService;

    private MainController underTest;

    @BeforeEach
    void setUp() {
        underTest = new MainController(roomService);
    }


    @Test
    void getListOfRooms() {
        underTest.getListOfRooms();
        verify(roomService).getListOfRooms();
    }

    @Test
    void createRoom() {
        Room room = new Room();
        room.setCountry("China");
        room.setName("First");
        underTest.createRoom(room);
        ArgumentCaptor<Room> argumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomService).createRoom(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(room);
    }

    @Test
    void getRoomById() {
        underTest.getRoomById(1L);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(roomService).getRoomById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(1L);
    }





}