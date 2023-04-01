package com.driver;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }

    public void addPartner(DeliveryPartner deliveryPartner){
        orderRepository.addPartner(deliveryPartner);
    }

    public void addPair(String PartnerID,String OrderID){
        Order order = orderRepository.getOrder(OrderID);
        DeliveryPartner partner= orderRepository.getPartner(PartnerID);

        orderRepository.addPair(partner,order);
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
    }

    public Order getOrder(String OrderID){
        return orderRepository.getOrder(OrderID);
    }

    public DeliveryPartner getPartner(String partnerID){
        return orderRepository.getPartner(partnerID);
    }

    public List<String> getOrderList(String partnerID){
        DeliveryPartner dp = orderRepository.getPartner(partnerID);
        List<Order> LO = orderRepository.getOrderList(dp);
        List<String> LS = new ArrayList<>();
        for(Order order:LO){
            LS.add(order.getId());
        }
        return LS;
    }

    public List<String> getAllOrders(){
      return orderRepository.getAllOrders();
    }

    public int getUnassignedOrders(){
        //List<DeliveryPartner> dp = orderRepository.getAllPartners();
        int assignedOrders=orderRepository.numberOfAssignedOrders();

        int totalOrders = orderRepository.numberOfOrders();

        return totalOrders-assignedOrders;
    }
    public int getOrdersAfterTime(int time,String id){
        DeliveryPartner dp = orderRepository.getPartner(id);
        List<Order> Olist = orderRepository.getOrderList(dp);

        int OrdersLeft=0;
        for(Order o:Olist){
            if(o.getDeliveryTime()>time) OrdersLeft++;
        }
        return OrdersLeft;
    }

    public String lastOrder(String partnerID){
        DeliveryPartner d = orderRepository.getPartner(partnerID);
        List<Order> orders= orderRepository.getOrderList(d);

        Order order = null;
        int time = 0;
        for(Order o : orders){
            if(o.getDeliveryTime()>time){
                time = o.getDeliveryTime();
            }
        }
        int hr = time/60;
        int min = time%60;
        String t = hr+":"+min;
        return t;
    }

    public void deleteByPartnerID(String partnerID){
        DeliveryPartner dp = orderRepository.getPartner(partnerID);
        orderRepository.deleteByPartner(dp);
    }

    public void deleteByOrderByID(String orderID){
        Order order = orderRepository.getOrder(orderID);
        orderRepository.deleteByOrder(order);
    }


}
