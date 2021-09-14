package com.example.roomlamps.services;

import com.example.roomlamps.entities.Room;
import com.example.roomlamps.repositories.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RoomServiceTest {


    private RoomService underTest;

    @Mock
    private RoomRepo roomRepo;

    @BeforeEach
    void setUp() {
        underTest = new RoomService(roomRepo);
    }


    @Test
    void canGetListOfRooms() {
        //when
        underTest.getListOfRooms();
        //then
        verify(roomRepo).findAll();
    }


    @Test
    void canCreateRoom() {
        //given
        Room room = new Room();
        room.setCountry("Russian");
        room.setLamp(false);
        //when
        underTest.createRoom(room);

        //then
        ArgumentCaptor<Room> roomArgumentCaptor =
                ArgumentCaptor.forClass(Room.class);

        verify(roomRepo).save(roomArgumentCaptor.capture());

        Room capturedRoom = roomArgumentCaptor.getValue();
        assertThat(capturedRoom).isEqualTo(room);

    }

    @Test
    void canNotCreateRoomWithTheSameNameAndCountry() {
        //given
        Room room = new Room();
        room.setCountry("Russian");
        room.setName("First");
        room.setLamp(false);
        //when
        given(roomRepo.getRoomByCountryAndName(room.getCountry(), room.getName())).willReturn(Optional.of(room));

        //then
        assertThat(underTest.createRoom(room)).isEqualTo("exist");

    }


    @Test
    void canGetRoomById() {
        //when
        try {
            underTest.getRoomById(1L);
        }catch (Exception ex){

        }
        //given
        ArgumentCaptor<Long> argumentCaptor=ArgumentCaptor.forClass(Long.class);
        verify(roomRepo).findById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(1L);
    }

     @Test
    void canNotGetRoomById() {
        //when
         boolean notFound=false;
        try {
            underTest.getRoomById(1L);
        }catch (Exception ex){
          notFound=true;
        }
        //given
        assertThat(notFound).isEqualTo(true);

    }






}