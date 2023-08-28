package uz.pdp.online.entity.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Faculty;
import uz.pdp.online.entity.University;
import uz.pdp.online.entity.payload.FacultyDto;
import uz.pdp.online.entity.repository.FacultyRepository;
import uz.pdp.online.entity.repository.UniversityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {
    private FacultyRepository facultyRepository;
    private UniversityRepository universityRepository;

    public FacultyController(FacultyRepository facultyRepository, UniversityRepository universityRepository) {
        this.facultyRepository = facultyRepository;
        this.universityRepository = universityRepository;
    }

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {
        Boolean byNameAndUniversityId = facultyRepository.existsFacultyByNameAndUniversityId(facultyDto.getFacultyName(), facultyDto.getUniversityId());
        if (byNameAndUniversityId) return "Faculty already exists!";
        University universityById = universityRepository.getUniversityById(facultyDto.getUniversityId());
        if (universityById == null) return "University not found!";
        Faculty faculty = new Faculty(null, facultyDto.getFacultyName(), universityById);
        facultyRepository.save(faculty);
        return "Faculty is added!";

    }

    //Vazirlik uchun
    // faculty list
    @GetMapping
    public List<Faculty> getFacultyList() {
        return facultyRepository.findAll();
    }

    //xoxlagan faculty id-si orqali
    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Integer id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) return new Faculty();
        return optionalFaculty.get();
    }

    //universitet xodimi uchun
    @GetMapping(value = "/byUniversityId/{universityId}")
    public List<Faculty> getFacultyByUniversityId(@PathVariable Integer universityId) {
        Optional<University> optionalUniversity = universityRepository.findById(universityId);
        if (optionalUniversity.isPresent()) {
            return facultyRepository.getFacultyListByUniversityId(universityId);
        }
        return new ArrayList<Faculty>();
    }

}
