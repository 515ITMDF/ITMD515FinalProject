package com.liu.demo.controller;

import com.liu.demo.entities.Customer;
import com.liu.demo.entities.Driver;
import com.liu.demo.entities.RootUser;
import com.liu.demo.service.CustomerService;
import com.liu.demo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@Controller
public class LoginController {
    @Autowired
    CustomerService customerService;
    @Autowired
    DriverService driverService;
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String start(){
        return "login";
    }

    @RequestMapping(value="login", method=RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userid=request.getParameter("userid");
        String password= request.getParameter("password");
        String role=request.getParameter("role");
        if(role.equals("customer")){
           Optional<Customer> oc= customerService.findCustomer(userid);

           if(oc.isPresent()&&oc.get().getPassword().equals(password)){
               request.getSession().setAttribute("customer",oc.get());
               return "redirect:/home";
           }else{
               return "redirect:/login";
           }
        }else if(role.equals("driver")){
            Optional<Driver> od= driverService.findDriver(userid);
            if(od.isPresent()&&od.get().getPassword().equals(password)){
                return "redirect:/dhome";
            }else{
                return"redirect:/login";
            }
        }else{
            return null;
        }



    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response){
        String userid=request.getParameter("userid");
        String username=request.getParameter("username");
        String password= request.getParameter("password");
        String role=request.getParameter("role");
        if(role.equals("customer")){
            Customer c=new Customer();
            c.setId(userid);
            c.setName(username);
            c.setPassword(password);
            customerService.save(c);
            return "redirect:/home";
        }else if(role.equals("driver")){
            Driver d=new Driver();
            d.setId(userid);
            d.setName(username);
            d.setPassword(password);
            driverService.save(d);
            return "redirect:/dhome";
        }

        return null;
    }
}
