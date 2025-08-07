package net.firstspringproject.journalApp.Controller;

import net.firstspringproject.journalApp.Service.JournalEntryService;
import net.firstspringproject.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JornalEntryController {

    @Autowired
    JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry postJournal(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(entry);
        return entry;
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteJournal(@PathVariable ObjectId id){
        journalEntryService.delete(id);
        return true;
    }

    // find by id
    @GetMapping("id/{id}")
    public JournalEntry getJournal(@PathVariable ObjectId id){
        return journalEntryService.getById(id);
    }

    // update
    @PutMapping("/id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id , @RequestBody JournalEntry entry){
        JournalEntry old = getJournal(id);

        if(old != null) {
            old.setTitle((entry.getTitle() != null && !entry.getTitle().isEmpty()) ? entry.getTitle() : old.getTitle());
            old.setContent((entry.getContent() != null && !entry.getContent().isEmpty()) ? entry.getContent() : old.getContent());
        }

        journalEntryService.saveEntry(old);

        return old;
    }

}
