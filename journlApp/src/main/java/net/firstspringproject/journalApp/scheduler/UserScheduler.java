package net.firstspringproject.journalApp.scheduler;


import net.firstspringproject.journalApp.Service.EmailService;
import net.firstspringproject.journalApp.cache.AppCache;
import net.firstspringproject.journalApp.entity.JournalEntry;
import net.firstspringproject.journalApp.entity.User;
import net.firstspringproject.journalApp.enums.Sentiment;
import net.firstspringproject.journalApp.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Autowired
    EmailService emailService;


    @Autowired
    AppCache appCache;

    @Scheduled(cron = "0 0 12 ? * SUN *") // every sunday
//    @Scheduled(cron = "0 * * * * *") // every minute
    public void findUserAndSendSA(){

        List<User> users = userRepositoryImpl.getUserForSA();

        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentimentList = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            // find which sentiment occurs most
            HashMap<Sentiment,Integer> map = new HashMap<>();
            for(Sentiment st : sentimentList ){
                if(st!=null) map.put(st,map.getOrDefault(st,0)+1);
            }

            int maxCount = 0;
            Sentiment mostFreqSentiment = null;

            for(Map.Entry<Sentiment,Integer> e : map.entrySet()){
                if(e.getValue() > maxCount){
                    maxCount = e.getValue();
                    mostFreqSentiment = e.getKey();
                }
            }

            if(mostFreqSentiment != null){
                emailService.sendMail(user.getEmail(),"Your Sentiment in Last Week",mostFreqSentiment.toString());
            }


        }

    }

    @Scheduled(cron = "0 0/5 * * * *") // every 5 Min
    public void cacheRelod(){
        appCache.init();
    }

}
