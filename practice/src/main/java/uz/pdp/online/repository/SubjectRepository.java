package uz.pdp.online.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Boolean existsSubjectByName(String name);
}
