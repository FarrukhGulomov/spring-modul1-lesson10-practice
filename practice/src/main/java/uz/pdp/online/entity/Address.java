package uz.pdp.online.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//1

    @Column(nullable = false)
    private String city;//Toshkent

    @Column(nullable = false)
    private String district;//Mirobod

    @Column(nullable = false)
    private String street;//U.Nosir ko'chasi

}
