package ru.demo.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.demo.sarafan.domain.Message;
import ru.demo.sarafan.domain.Views;
import ru.demo.sarafan.dto.EventType;
import ru.demo.sarafan.dto.ObjectType;
import ru.demo.sarafan.repo.MessageRepo;
import ru.demo.sarafan.util.WsSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    public MessageController(MessageRepo messageRepo, WsSender wsSender) {

        this.messageRepo = messageRepo;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    private final MessageRepo messageRepo;
    private final BiConsumer<EventType, Message> wsSender;

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> getAllMessages() { return messageRepo.findAll(); }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getMessageById(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {

        message.setCreationDate(LocalDateTime.now());
        Message updatedMessage = messageRepo.save(message);

        wsSender.accept(EventType.CREATE, updatedMessage);
        return updatedMessage;
    }

    @PutMapping("{id}")
    public Message updateMessage(
            @PathVariable("id") Message messageFromDB,
            @RequestBody Message message) {

        BeanUtils.copyProperties(message, messageFromDB, "id");

        Message updatedMessage = messageRepo.save(messageFromDB);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message) {
        messageRepo.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }
}
