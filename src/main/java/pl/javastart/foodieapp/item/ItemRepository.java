package pl.javastart.foodieapp.item;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    public Optional<Item> getItemByNameIgnoreCase(String name);
}
