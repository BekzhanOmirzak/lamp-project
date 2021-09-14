package com.example.roomlamps.repositories;

import com.example.roomlamps.entities.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class RoomRepoTest {

    @Autowired
    private RoomRepo roomRepo;

    @AfterEach
    void tearDown() {
        roomRepo.deleteAll();
    }

    @Test
    void shouldGetRoomByCountryAndName() {
        //given
        Room room = new Room();
        room.setName("First Room");
        room.setCountry("Taiwan");

        //when
        roomRepo.save(room);
        Room roomFromDB = roomRepo.getRoomByCountryAndName(room.getCountry(), room.getName()).get();

        assertThat(roomFromDB).isEqualTo(room);
    }

    @Test
    void shouldFindRoomById() {
        //given
        Room room = new Room();
        room.setName("First Room");
        room.setCountry("Taiwan");
        roomRepo.save(room);

        Room foundRoom = roomRepo.getById(1L);

        assertThat(foundRoom).isEqualTo(room);
    }

    





}