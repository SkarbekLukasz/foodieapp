package pl.javastart.foodieapp.panel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.foodieapp.item.Item;
import pl.javastart.foodieapp.order.Order;
import pl.javastart.foodieapp.order.OrderRepository;
import pl.javastart.foodieapp.order.OrderStatus;

import java.util.List;
import java.util.Optional;

import static pl.javastart.foodieapp.order.OrderStatus.*;

@Controller
public class PanelController {
    private OrderRepository orderRepository;

    public PanelController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/panel/zamowienia")
    public String orderPanel(@RequestParam(required = false) OrderStatus status, Model model) {
        List<Order> orders;
        if(status == null) {
            orders = (List<Order>) orderRepository.findAll();
        } else {
            orders = orderRepository.findAllByStatus(status);
        }
        model.addAttribute("orders", orders);
        return "panel";
    }

    @GetMapping("/panel/zamowienie/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(o -> singleOrderPage(o, model)).orElse("redirect:/");
    }

    private String singleOrderPage(Order order, Model model) {
        model.addAttribute("order", order);
        model.addAttribute("sum", order.getItems().stream().mapToDouble(Item::getPrice).sum());
        return "orderdetails";
    }

    @PostMapping("panel/zamowienie/{id}")
    public String changeOrderDetails(@PathVariable Long id, Model model){
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> o.setStatus(nextStatus(o.getStatus())));
        order.ifPresent(o -> orderRepository.save(o));
        return order.map(o -> singleOrderPage(o, model)).orElse("redirect:/");
    }

    private OrderStatus nextStatus(OrderStatus status) {
            if(status == NEW) {
                return IN_PROGRESS;
            }
            else if(status == IN_PROGRESS){
                return COMPLETE;
            }
            else {
                throw new IllegalArgumentException("There is no next status for provided value");
            }
        }
    }

