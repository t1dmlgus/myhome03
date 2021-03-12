package com.s1dmlgus.myhome03.service;


import com.s1dmlgus.myhome03.config.auth.PrincipalDetails;
import com.s1dmlgus.myhome03.domain.reply.ReplyRepository;
import com.s1dmlgus.myhome03.domain.user.Member;
import com.s1dmlgus.myhome03.web.dto.reply.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;


    // 등록
    @Transactional
    public void saveReply(Long id, ReplyRequestDto replyRequestDto, PrincipalDetails userDetails){

        
         //세션 유저
        Member user = userDetails.getUser();

    }


}
