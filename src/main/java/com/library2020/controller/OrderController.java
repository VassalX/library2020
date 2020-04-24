package com.library2020.controller;

import com.library2020.model.*;
import com.library2020.payload.request.BookRequest;
import com.library2020.payload.request.OrderRequest;
import com.library2020.payload.response.MessageResponse;
import com.library2020.repository.BookInstanceRepository;
import com.library2020.repository.OrderRepository;
import com.library2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookInstanceRepository bookInstanceRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) throws IOException {
        Order order = new Order();

        order.setStatus(OrderStatus.ON_REVIEW);
        order.setExpectedReturnDate(orderRequest.getExpectedReturnDate());

        Optional<User> userFound = userRepository.findById(orderRequest.getUserId());
        if(userFound.isPresent()){
            order.setUser(userFound.get());
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: User with id %s doesn't exist",
                                    orderRequest.getUserId())));
        }

        Optional<BookInstance> bookInstanceFound = bookInstanceRepository.findById(orderRequest.getBookInstanceId());
        if(bookInstanceFound.isPresent()){
            order.setBookInstance(bookInstanceFound.get());
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: BookInstance with id %s doesn't exist",
                                    orderRequest.getBookInstanceId())));
        }

        orderRepository.save(order);

        return ResponseEntity.ok(new MessageResponse("Order was created successfully!"));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody OrderRequest orderRequest){
        Optional<Order> foundOrder = orderRepository.findById(id);
        if(!foundOrder.isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            String.format("Error: Order with id: %d doesn't exist",id)));
        }
        Order order = foundOrder.get();
        order.setExpectedReturnDate(orderRequest.getExpectedReturnDate());
        OrderStatus status = OrderStatus.fromString(orderRequest.getStatus());
        if(status != null){
            order.setStatus(status);
        }
        if(orderRequest.getActualReturnDate() != null){
            order.setActualReturnDate(orderRequest.getActualReturnDate());
        }

        orderRepository.save(order);

        return ResponseEntity.ok(new MessageResponse("Order was updated successfully!"));
    }

    @GetMapping("/")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }
}