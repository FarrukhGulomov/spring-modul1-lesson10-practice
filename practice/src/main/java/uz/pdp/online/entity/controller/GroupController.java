package uz.pdp.online.entity.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Faculty;
import uz.pdp.online.entity.Group;
import uz.pdp.online.entity.payload.GroupDto;
import uz.pdp.online.entity.repository.FacultyRepository;
import uz.pdp.online.entity.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/group")
public class GroupController {
    private GroupRepository groupRepository;
    private FacultyRepository facultyRepository;

    public GroupController(GroupRepository groupRepository, FacultyRepository facultyRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
    }
    @PostMapping()
    public String addGroup(@RequestBody GroupDto groupDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if(optionalFaculty.isEmpty()) return "Faculty not found!";
        Faculty faculty = optionalFaculty.get();
        Group group = new Group();
        group.setName(groupDto.getGroupName());
        group.setFaculty(faculty);
        groupRepository.save(group);
        return "Group is added!";
    }
    @GetMapping
    public List<Group> groupList(){
        return groupRepository.findAll();
    }


    //Univeritet uchun
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupListByUniversityId(@PathVariable Integer universityId){
       return groupRepository.getGroupsByUniversityId(universityId);
    }

    @GetMapping(value = "byFacultyId/{facultyId}")
    public List<Group> getGroupByFacultyId(@PathVariable Integer facultyId){
        return groupRepository.getGroupsByFacultyId(facultyId);
    }

    @GetMapping("byGroupId/{groupId}")
    public Group getGroupById(@PathVariable  Integer groupId){
        return groupRepository.getGroupById(groupId);
    }
}
