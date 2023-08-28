package uz.pdp.online.entity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.Address;
import uz.pdp.online.entity.University;
import uz.pdp.online.entity.payload.UniversityDto;
import uz.pdp.online.entity.repository.AddressRepository;
import uz.pdp.online.entity.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/university")
public class UniversityController {
    private UniversityRepository universityRepository;
    private AddressRepository addressRepository;

    public UniversityController(UniversityRepository universityRepository, AddressRepository addressRepository) {
        this.universityRepository = universityRepository;
        this.addressRepository = addressRepository;
    }

    @PostMapping
    public String addUniversity(@RequestBody UniversityDto dto) {
        Boolean hasUniversity = universityRepository.existsUniversityByName(dto.getName());
        System.out.println(hasUniversity);
        if (hasUniversity) return "This university is already exist!";
        Address address = new Address(null, dto.getStreet(), dto.getDistrict(), dto.getCity());
        Address savedAddress = addressRepository.save(address);
        University university = new University();
        university.setAddress(savedAddress);
        university.setName(dto.getName());
        universityRepository.save(university);
        return "University is added!";
    }
    @GetMapping()
    public List<University> getUniversityList(){
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }
    @GetMapping(value = "/getById/{universityId}")
    public University getUniversityById(@PathVariable Integer universityId){
        Optional<University> optionalUniversity = universityRepository.findById(universityId);
        if(optionalUniversity.isPresent()){
            return optionalUniversity.get();
        }
        return new University();
    }

    @PutMapping(value = "/update/{id}")
    public String editUniversityById(@PathVariable Integer id,@RequestBody UniversityDto dto){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if(optionalUniversity.isPresent()){
            University university = optionalUniversity.get();
            Address address = university.getAddress();
            address.setStreet(dto.getStreet());
            address.setDistrict(dto.getDistrict());
            address.setCity(dto.getCity());
            addressRepository.save(address);
            university.setName(dto.getName());
            universityRepository.save(university);
            return "University is edited!";
        }
        return "not found!";
    }
    @DeleteMapping(value = "deleteById/{id}")
        public String deleteUniversityById(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if(optionalUniversity.isEmpty()) return "not found!";
        universityRepository.deleteById(id);
        return optionalUniversity.get().getName()+ " is deleted!";
    }


}
