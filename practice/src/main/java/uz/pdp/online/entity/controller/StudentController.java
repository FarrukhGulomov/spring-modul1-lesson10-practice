package uz.pdp.online.entity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.entity.Address;
import uz.pdp.online.entity.Group;
import uz.pdp.online.entity.Student;
import uz.pdp.online.entity.Subject;
import uz.pdp.online.entity.payload.StudentDto;
import uz.pdp.online.entity.repository.AddressRepository;
import uz.pdp.online.entity.repository.GroupRepository;
import uz.pdp.online.entity.repository.StudentRepository;
import uz.pdp.online.entity.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentRepository studentRepository;
    private AddressRepository addressRepository;
    private GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    public StudentController(StudentRepository studentRepository, AddressRepository addressRepository, GroupRepository groupRepository,
                             SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping()
    public String addStudent(@RequestBody StudentDto studentDto) {
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (optionalGroup.isEmpty()) return "Group is not found!";
        Group group = optionalGroup.get();
        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setStreet(studentDto.getStreet());
        address.setDistrict(studentDto.getDistrict());
        Address savedAddress = addressRepository.save(address);
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGroup(group);
        student.setAddress(savedAddress);
        Subject subject = new Subject();
        List<Subject> subjectList = new ArrayList<>();
        for (Integer id : studentDto.getSubjectsId()) {
            Optional<Subject> optionalSubject = subjectRepository.findById(id);
            if (optionalSubject.isEmpty()) return "Subject is not found with: " + id;
            Subject newSubject = optionalSubject.get();
            subjectList.add(newSubject);
        }
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return "Student is added!";

    }

}
