package pl.javastart.foodieapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.foodieapp.item.Item;
import pl.javastart.foodieapp.item.ItemRepository;

import java.util.List;

@Controller
public class HomeController {
    private ItemRepository itemRepository;

    public HomeController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Item> itemList = (List<Item>) itemRepository.findAll();
        model.addAttribute("items", itemList);
        return "index";
    }
}
