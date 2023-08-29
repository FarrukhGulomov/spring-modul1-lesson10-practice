package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.Faculty;
import uz.pdp.online.entity.University;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Integer> {

public Boolean existsFacultyByNameAndUniversityId(String facultyName,Integer universityId);
@Query(value = "SELECT * FROM faculty f WHERE f.university_id = :universityId ",nativeQuery = true)
public List<Faculty> getFacultyListByUniversityId(Integer universityId);
}
