package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return questionRepository.findAll();
    }

    public Question findById(Long id){
        Optional<Question> oq =  questionRepository.findById(id);
        if(oq.isPresent()){
            return oq.get();
        } else {
            throw new DataNotFoundException("question not found");
        }

    }
}
