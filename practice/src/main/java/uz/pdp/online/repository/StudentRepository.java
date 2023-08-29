package uz.pdp.online.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.online.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    //    @Query(value = "SELECT * FROM student s JOIN groups gr ON s.group_id = gr.id\n" +
//            "JOIN faculty f ON gr.faculty_id = f.id JOIN university u ON f.university_id = u.id\n" +
//            "WHERE U.id= :universityId",nativeQuery = true)
    @Query(value = "SELECT s FROM Student  s JOIN groups gr ON s.group.id = gr.id JOIN Faculty f ON gr.faculty.id=f.id JOIN " +
            "University u ON f.university.id=u.id WHERE u.id=:universityId")
    public List<Student> getAllStudentListByUniversityId(Integer universityId);

    Page<Student> findStudentsByGroup_Faculty_UniversityId(Integer universityId, Pageable pageable);

    @Query(value = "SELECT s FROM Student  s JOIN groups gr ON s.group.id = gr.id JOIN Faculty f ON gr.faculty.id=f.id  WHERE f.id=:facultyId")
    public List<Student> getStudentListByFacultyId(Integer facultyId);

    //page facultyId boyicha topish  pastki _ chiziqcha join qiladi group_faculty ...
    Page<Student> findStudentsByGroup_FacultyId(Integer facultyId, Pageable pageable);

    public List<Student> getStudentsByGroupId(Integer groupId);

    @Query(value = "SELECT * FROM student s where s.id=:id", nativeQuery = true)
    public Student getStudentBy_Id(Integer id);

    //guruh id boyicha page qilish

    Page<Student> findStudentsByGroupId(Integer groupId,Pageable pageable);
}
