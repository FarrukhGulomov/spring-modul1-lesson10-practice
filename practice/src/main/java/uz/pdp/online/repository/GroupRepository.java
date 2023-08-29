package uz.pdp.online.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query(value = "SELECT gr FROM groups gr WHERE gr.faculty.university.id= :universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);

    List<Group> getGroupsByFacultyId(Integer facultyId);
    @Query(value = "SELECT * FROM groups gr WHERE gr.id= :groupId ",nativeQuery = true)
    Group getGroupById(Integer groupId);
}
