package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class SbbRep2ApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("데이터 생성 테스트")
    void t1(){
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);
    }

    @Test
    @DisplayName("find All 메서드")
    void t2(){
        List<Question> all = this.questionRepository.findAll();

        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    @DisplayName("findById 메서드")
    void t3(){
        Optional<Question> oq = this.questionRepository.findById(1L);

        if(oq.isPresent()){
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?",q.getSubject());
        }
    }

    @Test
    @DisplayName("findBySubject 메서드")
    void t4(){
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1,q.getId());
    }

    @Test
    @DisplayName("findBySubjectAndContent 메서드")
    void t5(){
        Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
        assertEquals(1,q.getId());
    }

    @Test
    @DisplayName("findBySubjectLike 메서드")
    void t6(){
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");

        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?",q.getSubject());
    }

    @Test
    @DisplayName("질문 데이터 수정하기")
    void t7(){
        Optional<Question> oq = this.questionRepository.findById(1L);

        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");

        this.questionRepository.save(q);
        oq = this.questionRepository.findById(1L);
        assertTrue(oq.isPresent());
        Question q2 = oq.get();
        assertEquals("수정된 제목",q2.getSubject());
    }

    @Test
    @DisplayName("질문 삭제하기")
    void t8(){
        Question q = new Question();
        q.setContent("삭제용");
        q.setSubject("삭제용");
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);

        assertEquals(3,this.questionRepository.count());
        q = this.questionRepository.findBySubject("삭제용");

        this.questionRepository.delete(q);
        assertEquals(2,this.questionRepository.count());

    }

    @Test
    @DisplayName("답변 저장하기")
    void t9(){
        Optional<Question> od = this.questionRepository.findById(2L);
        assertTrue(od.isPresent());
        Question q = od.get();

        Answer a = new Answer();
        a.setContent("네, 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    @DisplayName("답변 조회하기")
    void t10(){
        Optional<Answer> a = answerRepository.findById(1L);
        assertTrue(a.isPresent());
        Answer answer = a.get();
        assertEquals(1,answer.getId());
    }

    @Test
    @DisplayName("질문 데이터 통해 답변 조회하기")
    void t11(){
        Optional<Question> oq = this.questionRepository.findById(2L);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();
        assertEquals(1,answerList.size());
        assertEquals("네, 자동으로 생성됩니다.",answerList.get(0).getContent());

    }

}
