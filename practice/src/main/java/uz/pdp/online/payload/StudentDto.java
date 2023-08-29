package uz.pdp.online.entity.payload;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uz.pdp.online.entity.Address;
import uz.pdp.online.entity.Group;
import uz.pdp.online.entity.Subject;

import java.util.List;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private String city;
    private String district;
    private String street;
    private Integer groupId;
    private List<Integer> subjectsId;
}
