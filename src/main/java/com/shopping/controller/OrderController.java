package com.shopping.controller;

import com.shopping.dto.OrderDto;
import com.shopping.dto.OrderHistDto;
import com.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService ;

    // @RequestBody와 @ResponseBody는 스프링의 비동기 처리(ajax)시에 사용되는 어노테이션입니다.

    // @ResponseBody는 자바 객체를 다시 http 응답 객체로 변경해 줍니다.
    @PostMapping(value="/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                         BindingResult bindingResult,
                         Principal principal){
        // @RequestBody는 커맨드 객체가 json으로 넘어 오는데, 이것을 자바의 객체로 변환시켜주는 역할을 합니다.

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder() ;
            List<FieldError> fieldErrors = bindingResult.getFieldErrors() ;

            for(FieldError fieldError:fieldErrors){
               sb.append(fieldError.getDefaultMessage()) ;
            }
            // HttpEntity는 요청이나 응답에 대한 결과를 처리해주는 Entity입니다.
            // HttpEntity = HttpHeader + HttpBody
            // ResponseEntit의 수퍼 클래스가 HttpEntity입니다.
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST) ;
        }

        String email = principal.getName() ;

        Long orderId ;

        try{
            orderId = orderService.order(orderDto, email) ;

        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST) ;
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK) ;
    }

    // 구매 이력을 조회하는 컨트롤러 메소드를 구현합니다.
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal,
                            Model model){

        // 한번에 보여줄 개수(size)는 4개라고 가정하겠습니다.
        int mypage = page.isPresent() ? page.get() : 0 ;
        Pageable pageable = PageRequest.of(mypage, 4) ;

        String email = principal.getName() ;
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(email, pageable) ;

        model.addAttribute("orders", orderHistDtoList) ;
        model.addAttribute("page", pageable.getPageNumber()) ;
        model.addAttribute("maxPage", 5) ;

        return "order/orderHist" ;
    }

    @PostMapping(value = "/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(
        @PathVariable("orderId") Long orderId,
        Principal principal
        ) {
        String email = principal.getName();

        if (!orderService.validateOrder(orderId, email)) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOder(orderId);   // 주문 취소 로직 호출
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
