package com.demoSB.api.v1;

import com.dto.ApiResponse;

import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

    @PersistenceUnit
    EntityManagerFactory emf;

    @GetMapping(value = "trainers/information")
    public ResponseEntity<ApiResponse<List<Pair<String, Integer>>>> getTrainersInformation() {
        ApiResponse<List<Pair<String, Integer>>> response = new ApiResponse<>();
        Map<String, Integer> map = new HashMap<>();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        NativeQuery query = session.createNativeQuery("select t.name Trainer, count(h.id) Horses from trainers t\n" +
                " left join horses h on t.id = h.trainer_id\n" +
                " group by Trainer order by Horses desc");
        List<Pair<String, Integer>> rs = query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
        response.setData(rs);
        response.setMessage("All trainers were loaded.");
        response.setStatus(20);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
