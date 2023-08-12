//package com.example.umc_insider.controller;
//
//import com.example.umc_insider.domain.Messages;
//import com.example.umc_insider.service.MessagesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/messages")
//public class MessagesController {
//
//    private MessagesService messagesService;
//
//    @Autowired
//    public MessagesController(MessagesService messagesService) {
//        this.messagesService = messagesService;
//    }
//    @PostMapping("/send")
//    public ResponseEntity<Messages> createMessage(@RequestBody Messages message) {
//        Messages createdMessage = messagesService.createMessage(message);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
//    }
//
////    @GetMapping("/{id}")
////    public ResponseEntity<Messages> getMessageById(@PathVariable("id") Long id) {
////        Messages message = messagesService.getMessageById(id);
////        return ResponseEntity.ok(message);
////    }
////
////    @GetMapping("/inbox")
////    public ResponseEntity<List<Messages>> getInboxForUser(@RequestParam("userId") Long userId) {
////        List<Messages> inbox = messagesService.getInboxForUser(userId);
////        return ResponseEntity.ok(inbox);
////    }
////
////    @GetMapping("/sent")
////    public ResponseEntity<List<Messages>> getSentMessagesForUser(@RequestParam("userId") Long userId) {
////        List<Messages> sentMessages = messagesService.getSentMessagesForUser(userId);
////        return ResponseEntity.ok(sentMessages);
////    }
//
//
//}
