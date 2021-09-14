package com.example.roomlamps.services;


import com.example.roomlamps.entities.Room;
import com.example.roomlamps.repositories.RoomRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RoomService {


    private RoomRepo roomRepo;


    public List<Room> getListOfRooms() {
        return roomRepo.findAll();
    }

    public String createRoom(Room room) {
        Optional<Room> optionalRoom = roomRepo.getRoomByCountryAndName(room.getCountry(), room.getName());
        if (optionalRoom.isPresent()) {
            return "exist";
        }
        roomRepo.save(room);
        return "" + room.getId();
    }

    public Room getRoomById(Long id) {
        return roomRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Room can't be found"));
    }

    public Room updateLamp(boolean lamp, Long id) {
        Room room = roomRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Room can't be found"));
        room.setLamp(lamp);
        roomRepo.save(room);
        return room;
    }


}
