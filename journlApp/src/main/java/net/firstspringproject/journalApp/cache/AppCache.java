package net.firstspringproject.journalApp.cache;


import jakarta.annotation.PostConstruct;
import net.firstspringproject.journalApp.entity.ConfigJournalAppEntity;
import net.firstspringproject.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API,
        QUOTES_API
    }

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> apiList = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity e : apiList){
            appCache.put(e.getKey(),e.getValue());
        }
    }
}
