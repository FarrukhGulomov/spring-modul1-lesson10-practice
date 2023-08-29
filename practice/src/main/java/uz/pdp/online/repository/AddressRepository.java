package uz.pdp.online.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
