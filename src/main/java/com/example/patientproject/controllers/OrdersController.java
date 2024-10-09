package com.example.patientproject.controllers;

import com.example.patientproject.Response;
import com.example.patientproject.models.Orders;
import com.example.patientproject.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<Orders> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public Response addOrder(@RequestBody Orders order) {
        System.out.println("Order Status: " + order.getStatus());
        System.out.println("Order Priority: " + order.getPriority());
        System.out.println("Order Requst Date: " + order.getRequstDate());
        if (ordersService.addOrUpdateOrder(order)) {
            return new Response(200, "Order added successfully", "Order with ID " + order.getId() + " has been added.");
        } else {
            return new Response(404, "Failed to add order", "Order with ID " + order.getId() + " already exists.");
        }
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @DeleteMapping("/delete/{id}/{deletedBy}")
//    public Response deleteOrder(@PathVariable Long id, @PathVariable String deletedBy) {
//        // Check if the order exists
//        if (!ordersService.checkIfOrderExistsById(id)) {
//            return new Response(404, "Not Found", "Order with ID " + id + " does not exist.");
//        }
//
//        // Delete the order by updating the deleted fields
//        if (ordersService.deleteOrder(id, deletedBy)) {
//            return new Response(200, "Deleted Successfully", "Order with ID " + id + " has been deleted by " + deletedBy);
//        } else {
//            return new Response(500, "Failed to Delete", "Failed to delete Order with ID " + id);
//        }
//    }
//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/update/{id}")
//    public Response updateOrder(@PathVariable Long id, @RequestBody Orders order) {
//        // Check if the order exists and has not been deleted
//        if (!ordersService.checkIfOrderExistsById(id)) {
//            return new Response(404, "Not Found", "Order with ID " + id + " does not exist.");
//        }
//
//        // Update the order
//        if (ordersService.updateOrder(id, order)) {
//            return new Response(200, "Updated Successfully", "Order with ID " + id + " has been updated.");
//        } else {
//            return new Response(500, "Failed to Update", "Failed to update Order with ID " + id);
//        }
//    }
}
