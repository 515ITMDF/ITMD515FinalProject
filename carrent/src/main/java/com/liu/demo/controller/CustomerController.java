package com.liu.demo.controller;


import com.liu.demo.entities.Car;
import com.liu.demo.entities.Customer;
import com.liu.demo.entities.Driver;
import com.liu.demo.entities.Transaction;
import com.liu.demo.service.CarService;
import com.liu.demo.service.CustomerService;
import com.liu.demo.service.DriverService;
import com.liu.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CarService carService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    DriverService driverService;
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String findAllCustomer(Map<String, Object> model){
       List<Car> carList= carService.findAllCar();
        //model.put("pos",request.getSession().getAttribute("customer"));
        model.put("carlist",carList);
        return "customerIndex";
    }
    @RequestMapping(value="/customerinfo",method = RequestMethod.GET)
    public String customerinfo(){
        return null;
    }

    @RequestMapping(value="/cardetial",method = RequestMethod.GET)
    public String cardetial(HttpServletRequest request,Map<String, Object> model){
        String carid=request.getParameter("carid");
        Optional<Car> ocar=carService.findCarById(carid);
        Car car=ocar.get();
        model.put("car",car);
        return "cardetial";
    }
    @RequestMapping(value="/payment",method = RequestMethod.GET)
    public String payment(HttpServletRequest request,Map<String, Object> model){

        String carid=request.getParameter("carid");
        Optional<Car> ocar=carService.findCarById(carid);
        Car car=ocar.get();
       List<Driver>drivers= driverService.findAllDriver();
       Driver driver = null;
       for(Driver d:drivers){
           if(d.getCar_id().equals(carid)){
               driver=d;
           }
       }
       model.put("driver",driver);
        model.put("car",car);
        request.getSession().setAttribute("driver",driver);
        request.getSession().setAttribute("car",car);
        return "payment";
    }
    @RequestMapping(value="/tran",method = RequestMethod.POST)
    public String addtransaction(HttpServletRequest request,Map<String, Object> model){
        Car car=(Car)request.getSession().getAttribute("car");
        Driver driver=(Driver)request.getSession().getAttribute("driver");
        String duration=request.getParameter("duration");
        Customer c=(Customer)request.getSession().getAttribute("customer");
        Transaction t=new Transaction();
      List<Transaction> tl= transactionService.findAllTransaction();
      int s=0;
      for(Transaction transaction:tl){
            if(Integer.parseInt(transaction.getId())>s){
                s=Integer.parseInt(transaction.getId());
            }
      }

        t.setId(String.valueOf(s+1));
        t.setCar_id(car.getId());
        t.setCustomer_id(c.getId());
        t.setDriver_id(driver.getId());
        t.setDuration(duration);
        Date date=new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String d = sdf.format(date);
        t.setDate(date);
        float dur=Float.parseFloat(duration);
        float money=dur*car.getPer_hour();
        t.setMoney(money);
        t.setState("open");
        transactionService.save(t);
        List<Transaction> tlist=transactionService.findAllTransaction();
        model.put("tran",tlist);
        return "customerInfo";
    }
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updatec(HttpServletRequest request,Map<String, Object> model){
       String password= request.getParameter("password");
       String username=request.getParameter("username");
        Customer c=(Customer)request.getSession().getAttribute("customer");
        c.setPassword(password);
        c.setName(username);
       customerService.save(c);
       return "customerInfo";
    }
    @RequestMapping(value="/customerin",method = RequestMethod.GET)
    public String seetran(HttpServletRequest request,Map<String, Object> model){
        List<Transaction> tlist= transactionService.findAllTransaction();
        model.put("tran",tlist);
        return "customerInfo";
    }
}
