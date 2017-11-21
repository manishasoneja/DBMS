public class CartItem{
    int product_id;
    int pharmacy_id;
    String product_name;
    String pharmacy_name;
    float price_unit;
    int price;
    int quantity;

    public CartItem(int product_id,int pharmacy_id,String product_name,String pharmacy_name,int price,int quantity){
        this.product_id=product_id;
        this.pharmacy_id=pharmacy_id;
        this.product_name=product_name;
        this.pharmacy_name=pharmacy_name;
       // this.price_unit=price_unit;
        this.price=price;
        this.quantity=quantity;

    }
    public int getPhId(){
        return pharmacy_id;    
    }
    public int getProdId(){
        return product_id;
    }
    public String getProdname(){
        return product_name;
    }
    public String getPhname(){
        return pharmacy_name;
    }
    /*public float getUnitPrice(){
        return price_unit;
    }*/
    public int getPrice(){
        return price;
    }
    public int getquantity(){
        return quantity;
    }
    public void setquantity(int quantity1){
        this.quantity=quantity1;
    }
    public void setPrice(int price1){
        this.price=price1;
    }
}

