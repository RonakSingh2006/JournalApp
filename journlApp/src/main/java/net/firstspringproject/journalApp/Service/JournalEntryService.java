package net.firstspringproject.journalApp.Service;

import net.firstspringproject.journalApp.entity.JournalEntry;
import net.firstspringproject.journalApp.entity.User;
import net.firstspringproject.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;


    // save entry
    public void saveEntry(JournalEntry journalEntry, String username){
        journalEntry.setDate(LocalDateTime.now());

        User user = userService.findByUserName(username);

        JournalEntry saved = journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){

        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId() == id);
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }

}
