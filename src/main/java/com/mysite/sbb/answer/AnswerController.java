package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(
            Model model,
            @PathVariable("id") Long id,
            @Valid AnswerForm answerForm,
            BindingResult bindingResult

    ) {
        Question question = this.questionService.findById(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "question_detail";
        }
        answerService.create(question,answerForm.getContent());
        return "redirect:/question/detail/%s".formatted(question.getId());
    }

}
