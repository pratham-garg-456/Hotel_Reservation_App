package application.models;

//BillInfo class
public class BillInfo {
 private double discount=0;

 public BillInfo(double discount) {
     this.discount = discount;
 }

 // Getter method
 public double getDiscount() {
     return discount;
 }
}