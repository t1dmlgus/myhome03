//package com.s1dmlgus.myhome03.domain.reply;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//class ReplyRepositoryTest {
//
//
//    @Autowired
//    private ReplyRepository repository;
//
//
//    @Test
//    @Transactional
//    public void 댓글findAll(){
//
//        List<Reply> all = repository.findAll();
//
//        for (Reply reply : all) {
//            System.out.println("reply = " + reply);
//        }
//    }
//}