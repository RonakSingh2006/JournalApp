package net.firstspringproject.journalApp.repository;

import net.firstspringproject.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

public class UserRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){ // get user for sentiment analysis
        Query query = new Query();

        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",Pattern.CASE_INSENSITIVE);

        query.addCriteria(Criteria.where("email").regex(emailPattern));
        query.addCriteria((Criteria.where("sentimentAnalysis")).is(true));

        List<User> userList = mongoTemplate.find(query, User.class);

        return userList;
    }

}
