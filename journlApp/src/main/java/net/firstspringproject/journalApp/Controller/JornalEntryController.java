package net.firstspringproject.journalApp.Controller;

import net.firstspringproject.journalApp.Service.JournalEntryService;
import net.firstspringproject.journalApp.Service.UserService;
import net.firstspringproject.journalApp.entity.JournalEntry;
import net.firstspringproject.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JornalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username){

        User user = userService.findByUserName(username);

        List<JournalEntry> all = user.getJournalEntries();

        if(all == null || all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> postJournal(@RequestBody JournalEntry entry,@PathVariable String username){
        try{
            journalEntryService.saveEntry(entry,username);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId id , @PathVariable String username){
        journalEntryService.delete(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // find by id
    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournal(@PathVariable ObjectId id){

        Optional<JournalEntry> journalEntry =journalEntryService.getById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // update
    @PutMapping("/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id , @RequestBody JournalEntry entry , @PathVariable String username){
        JournalEntry old = journalEntryService.getById(id).orElse(null);

        if(old != null) {
            old.setTitle((entry.getTitle() != null && !entry.getTitle().isEmpty()) ? entry.getTitle() : old.getTitle());
            old.setContent((entry.getContent() != null && !entry.getContent().isEmpty()) ? entry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
