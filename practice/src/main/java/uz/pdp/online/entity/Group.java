package uz.pdp.online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
@Table(uniqueConstraints = @UniqueConstraint(columnNames ={"name","faculty_id"}))
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne//MANY group TO ONE faculty
    private Faculty faculty;
//
//    @OneToMany//ONE group TO MANY students
//    private List<Student> students;


}
