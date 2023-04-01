package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order> OrderDB;
    HashMap<String,DeliveryPartner> PartnerDB;
    HashMap<DeliveryPartner, List<Order>> DeleviryDB;
    HashMap<Order,DeliveryPartner> AssignedPartner;

    public OrderRepository() {
        OrderDB = new HashMap<>();
        PartnerDB = new HashMap<>();
        DeleviryDB = new HashMap<>();
        AssignedPartner = new HashMap<>();
    }

    public void addOrder(Order order){
        String id = order.getId();
        OrderDB.put(id,order);
    }

    public void addPartner(DeliveryPartner deliveryPartner){
        String id = deliveryPartner.getId();
        PartnerDB.put(id,deliveryPartner);
    }

    public void addPair(DeliveryPartner deliveryPartner,Order order){
        List<Order> Olist;
        if(DeleviryDB.containsKey(deliveryPartner)){
            Olist = DeleviryDB.get(deliveryPartner);
        }else {
            Olist = new ArrayList<>();
        }

        Olist.add(order);
        DeleviryDB.put(deliveryPartner,Olist);
        AssignedPartner.put(order,deliveryPartner);
    }

    public Order getOrder(String orderID){
        return OrderDB.get(orderID);
    }

    public DeliveryPartner getPartner(String partnerID){
        return PartnerDB.get(partnerID);
    }

    public List<Order> getOrderList(DeliveryPartner dp){
        return DeleviryDB.get(dp);
    }

    public List<String> getAllOrders(){
        List<String> Olist = new ArrayList<>();
        for(String id : OrderDB.keySet()){
            Olist.add(id);
        }
        return Olist;
    }

    public List<DeliveryPartner> getAllPartners(){
        List<DeliveryPartner> dp = new ArrayList<>();

        for(DeliveryPartner d : PartnerDB.values()){
            dp.add(d);
        }
        return dp;
    }



    public int numberOfOrders(){
        return OrderDB.size();
    }

    public int numberOfAssignedOrders(){
        return AssignedPartner.size();
    }

    public void deleteByPartner(DeliveryPartner deliveryPartner){
        List<Order> Olist = DeleviryDB.get(deliveryPartner);
        for(Order o : Olist){
            AssignedPartner.remove(o);
        }
        DeleviryDB.remove(deliveryPartner);
        PartnerDB.remove(deliveryPartner.getId());
    }

    public void deleteByOrder(Order order){
        DeliveryPartner dp = AssignedPartner.get(order);
        List<Order> Olist = DeleviryDB.get(dp);
        Olist.remove(order);
        dp.setNumberOfOrders(dp.getNumberOfOrders()-1);
        AssignedPartner.remove(order);
        OrderDB.remove(order.getId());
    }
}
