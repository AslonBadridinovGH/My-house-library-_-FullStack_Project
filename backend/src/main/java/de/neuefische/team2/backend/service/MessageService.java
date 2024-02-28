package de.neuefische.team2.backend.service;


import de.neuefische.team2.backend.models.Message;
import de.neuefische.team2.backend.models.MessageDtoPost;
import de.neuefische.team2.backend.repos.MessageRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class MessageService {
    private final MessageRepo messageRepo;
    private final IdService idService;

    public MessageService(MessageRepo messageRepo, IdService idService) {
        this.messageRepo = messageRepo;
        this.idService = idService;
    }

    public List<Message> getMessages() {
        return messageRepo.findAll();
    }

    public Message deleteMessageById(String id) {
        Optional<Message> byId = messageRepo.findById(id);
        if (byId.isPresent()) {
            messageRepo.delete(byId.get());
            return byId.get();
        }
        throw (new NoSuchElementException("No message with such id"));
    }

    public Message updateStatus(String id) {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent() && message.get().read() == true) {
            return messageRepo.save(new Message(id, message.get().name(), message.get().mail(), message.get().message(), false));
        } else if (message.isPresent() && message.get().read() == false) {
            return messageRepo.save(new Message(id, message.get().name(), message.get().mail(), message.get().message(), true));
        }
        throw (new NoSuchElementException("No message with such id"));
    }

    public Message addMessage(MessageDtoPost messageDtoPost) {
        String id = idService.newId();
        Message messageNew = new Message(id, messageDtoPost.name(), messageDtoPost.mail(), messageDtoPost.message(), false);
        return messageRepo.save(messageNew);
    }

}
