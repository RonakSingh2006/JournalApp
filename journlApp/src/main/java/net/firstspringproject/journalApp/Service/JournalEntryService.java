package net.firstspringproject.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.firstspringproject.journalApp.entity.JournalEntry;
import net.firstspringproject.journalApp.entity.User;
import net.firstspringproject.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    // save entry
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            journalEntry.setDate(LocalDateTime.now());

            User user = userService.findByUserName(username);

            JournalEntry saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e){
            log.error(e.toString(),"While saving Journal Entry");
        }
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

    @Transactional
    public boolean delete(ObjectId id, String username){
        boolean removed = false;
        try{
            User user = userService.findByUserName(username);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));

            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception e){
            log.error(e.toString(),"While Deleting Journal Entry");
        }

        return removed;
    }

}
