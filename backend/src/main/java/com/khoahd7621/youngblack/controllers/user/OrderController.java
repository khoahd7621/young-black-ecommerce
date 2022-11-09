package com.khoahd7621.youngblack.controllers.user;

import com.khoahd7621.youngblack.dtos.request.order.CreateNewOrderRequest;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.order.CreateNewOrderResponse;
import com.khoahd7621.youngblack.dtos.response.order.OrderResponse;
import com.khoahd7621.youngblack.exceptions.ForbiddenException;
import com.khoahd7621.youngblack.exceptions.NotFoundException;
import com.khoahd7621.youngblack.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public SuccessResponse<CreateNewOrderResponse> createNewOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest)
            throws NotFoundException {
        return orderService.createNewOrder(createNewOrderRequest);
    }

    @GetMapping("/{code}")
    public SuccessResponse<OrderResponse> getOrderByCode(@PathVariable String code) throws NotFoundException, ForbiddenException {
        return orderService.getOrderByCode(code);
    }
}
