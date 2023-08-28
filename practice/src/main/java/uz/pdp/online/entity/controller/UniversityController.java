package uz.pdp.online.entity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.entity.repository.UniversityRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {
    private UniversityRepository universityRepository;



}
