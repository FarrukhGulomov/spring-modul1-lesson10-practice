package uz.pdp.online.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Address;
import uz.pdp.online.entity.Group;
import uz.pdp.online.entity.Student;
import uz.pdp.online.entity.Subject;
import uz.pdp.online.repository.StudentRepository;
import uz.pdp.online.entity.repository.GroupRepository;
import uz.pdp.online.entity.repository.AddressRepository;
import uz.pdp.online.entity.repository.SubjectRepository;
import uz.pdp.online.entity.payload.StudentDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentRepository studentRepository;
    private uz.pdp.online.entity.repository.AddressRepository addressRepository;
    private uz.pdp.online.entity.repository.GroupRepository groupRepository;
    private final uz.pdp.online.entity.repository.SubjectRepository subjectRepository;

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

    @GetMapping("/forMinistry")
    public Page<Student> getAllStudentList(@RequestParam int page) {
        Pageable pageable= PageRequest.of(page,10);

        return studentRepository.findAll(pageable);
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Student> getStudentsByUniversityId(@PathVariable Integer universityId) {
        return studentRepository.getAllStudentListByUniversityId(universityId);
    }



    @GetMapping("byPageUniversityId/{universityId}")
    public Page<Student> getStudentPageByUniversityId(@PathVariable Integer universityId,@RequestParam int page){
        Pageable pageable=PageRequest.of(page,10);
      return   studentRepository.findStudentsByGroup_Faculty_UniversityId(universityId,pageable);

    }
    @GetMapping("/byFacultyId/{facultyId}")
    public List<Student> getStudentListByFacultyId(@PathVariable Integer facultyId) {
        return studentRepository.getStudentListByFacultyId(facultyId);
    }

    @GetMapping("/byFacultyIdByPage/{facultyId}")
    public Page<Student> getStudentPageBYFacultyId(@PathVariable Integer facultyId,@RequestParam Integer page){
        Pageable pageable=PageRequest.of(page,10);
        return studentRepository.findStudentsByGroup_FacultyId(facultyId,pageable);
    }

    @GetMapping("/byGroupIdByPage/{groupId}")
    public Page<Student> getStudentByGroupIdByPage(@PathVariable Integer groupId,@RequestParam int page){
        Pageable pageable=PageRequest.of(page,10);
        return studentRepository.findStudentsByGroupId(groupId,pageable);
    }

    @GetMapping("/byGroupId/{groupId}")
    public List<Student> getStudentsByGroupId(@PathVariable Integer groupId) {
        return studentRepository.getStudentsByGroupId(groupId);
    }

    @GetMapping("/byStudentId/{id}")
    public Student getStudentById(@PathVariable Integer id){
        return studentRepository.getStudentBy_Id(id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()) return "Student is not found by "+id+" id";
         studentRepository.deleteById(id);
        return "Student is deleted by "+id+" id";
    }
    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id,@RequestBody StudentDto dto){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()) return "Student is not found!";
        Student student = optionalStudent.get();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        Address address = student.getAddress();
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());
        Address savedAddress = addressRepository.save(address);
        Optional<Group> optionalGroup = groupRepository.findById(dto.getGroupId());
        if(optionalStudent.isEmpty()) return "Group is not found!";
        Group group = optionalGroup.get();
        student.setGroup(group);
        List<Subject> subjectList=new ArrayList<>();
        for (Integer subjectId : dto.getSubjectsId()) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if(optionalSubject.isEmpty()) return "Subject is not found!";
            Subject subject = optionalSubject.get();
            subjectList.add(subject);
        }
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return student.getFirstName()+student.getLastName()+" student is edited!";


    }
}
