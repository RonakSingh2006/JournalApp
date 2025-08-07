package net.firstspringproject.journalApp.Service;

import net.firstspringproject.journalApp.entity.JournalEntry;
import net.firstspringproject.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;


    // save entry
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public void delete(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
