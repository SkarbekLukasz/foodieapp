package pl.javastart.foodieapp.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ItemController {
    private ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/danie/{name}")
    public String getItem(@PathVariable String name, Model model) {
        String newName = nameChanger(name);
        Optional<Item> item = itemRepository.getItemByNameIgnoreCase(newName);
        item.ifPresent(it -> model.addAttribute("item", it));
        return item.map(it -> "item").orElse("redirect:/");
    }

    private String nameChanger(String name) {
        return name.replaceAll("-", " ");
    }
}
