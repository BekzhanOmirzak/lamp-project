package com.example.roomlamps.repositories;

import com.example.roomlamps.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {


    Optional<Room> getRoomByCountryAndName(String country, String name);

    Optional<Room> findById(Long id);




}
