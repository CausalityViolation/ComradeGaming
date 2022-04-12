package repo;

import entities.Clothing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepo extends CrudRepository<Clothing, Integer> {
}
