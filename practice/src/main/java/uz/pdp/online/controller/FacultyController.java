package uz.pdp.online.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Faculty;
import uz.pdp.online.entity.University;
import uz.pdp.online.entity.payload.FacultyDto;
import uz.pdp.online.repository.FacultyRepository;
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

    @DeleteMapping("/{id}")

    public String deleteFaculty(@PathVariable Integer id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) return "faculty is not found!";
        try {
            facultyRepository.deleteById(id);
            return "Faculty is deleted!";
        } catch (Exception e) {
            return "Error in deleting!";
        }
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto dto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) return "Faculty is not found!";
        Faculty faculty = optionalFaculty.get();
        faculty.setName(dto.getFacultyName());
        Optional<University> optionalUniversity = universityRepository.findById(dto.getUniversityId());
        if(optionalUniversity.isEmpty()) return "University is not found!";
        University university = optionalUniversity.get();
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "Faculty is edited!";
    }

}
