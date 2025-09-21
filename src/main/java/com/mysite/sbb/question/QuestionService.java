package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(new Sort.Order(Sort.Direction.DESC, "createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question findById(Long id){
        Optional<Question> oq =  questionRepository.findById(id);
        if(oq.isPresent()){
            return oq.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Question create(String subject, String content){
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
        return question;
    }
}
