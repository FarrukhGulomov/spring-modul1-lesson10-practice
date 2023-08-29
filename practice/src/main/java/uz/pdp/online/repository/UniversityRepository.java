package uz.pdp.online.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.University;

public interface UniversityRepository extends JpaRepository<University,Integer> {

    public boolean existsUniversityByName(String name);
    public University getUniversityById(Integer id);
}
