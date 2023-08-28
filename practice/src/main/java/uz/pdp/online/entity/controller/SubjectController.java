package uz.pdp.online.entity.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Subject;
import uz.pdp.online.entity.repository.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public String addSubject(@RequestBody Subject subject){
        Boolean isName = subjectRepository.existsSubjectByName(subject.getName());
        if(isName) return "Subject is already exists!";
        subjectRepository.save(subject);
        return subject.getName()+" is added!";
    }
    @GetMapping
    public List<Subject> getSubjectList(){
        return subjectRepository.findAll();
    }
}
