package com.example.roomlamps.controllers;


import com.example.roomlamps.entities.Room;
import com.example.roomlamps.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {


    private RoomService roomService;

    @Autowired
    public MainController(RoomService roomService){
         this.roomService=roomService;
    }


    @Autowired
    private SimpMessagingTemplate template;


    @RequestMapping
    public String mainPage() {
        return "index";
    }


    @GetMapping("/rooms")
    @ResponseBody
    @CrossOrigin
    public List<Room> getListOfRooms() {
        return roomService.getListOfRooms();
    }

    @PostMapping("/create")
    @CrossOrigin
    @ResponseBody
    public String createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @GetMapping("room/{id}")
    @ResponseBody
    @CrossOrigin
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }


    @GetMapping("/updateroom/{id}")
    @ResponseBody
    public void turnLampOnOrOff(@PathVariable Long id, @RequestParam boolean lamp) {
        Room room = roomService.updateLamp(lamp, id);
        this.template.convertAndSend("/topic/updatedroom", room);
    }



}
