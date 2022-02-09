package pl.javastart.foodieapp.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.foodieapp.common.Message;
import pl.javastart.foodieapp.item.Item;
import pl.javastart.foodieapp.item.ItemRepository;

import java.util.Optional;

@Controller
public class OrderController {

    private ItemRepository itemRepository;
    private ClientOrder clientOrder;
    private OrderRepository orderRepository;

    public OrderController(ItemRepository itemRepository, ClientOrder clientOrder, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.clientOrder = clientOrder;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/zamowienie/dodaj")
    public String addItemToOrder(@RequestParam Long itemId, Model model) {
        Optional<Item> item = itemRepository.findById(itemId);
        item.ifPresent(clientOrder::add);
        if(item.isPresent()) {
            model.addAttribute("message", new Message("Dodano", "Do zamówienia dodano: " + item.get().getName()));
        }
        else {
            model.addAttribute("message", new Message("Nie dodano", "Podano błędne id z menu: " + itemId));
        } return "message";
    }

    @GetMapping("/zamowienie")
    public String order(Model model) {
        model.addAttribute("order", clientOrder.getOrder());
        model.addAttribute("sum", clientOrder.getOrder().getItems().stream().mapToDouble(Item::getPrice).sum());
        return "zamowienie";
    }

    @PostMapping("zamowienie/realizuj")
    public String requestOrder(@RequestParam String address, @RequestParam String telephone, Model model) {
        Order order = clientOrder.getOrder();
        order.setAddress(address);
        order.setTelephone(telephone);
        orderRepository.save(order);
        clientOrder.clear();
        model.addAttribute("message", new Message("Dziękujemy za zamówienie!", "Zamówienie przekazane do realizacji!"));
        return "message";
    }

}
