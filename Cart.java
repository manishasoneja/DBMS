import java.util.Iterator;
import java.util.*;

public class Cart{
    private List<CartItem> cart;
    public Cart(){
        cart = new ArrayList<CartItem>();
    }
    public void add(int pr_id,int ph_id,String pr_name,String ph_name,int price,int qty){
    Iterator<CartItem> itr = cart.iterator();  
    while(itr.hasNext()){
        CartItem item = itr.next();
        if(item.getProdId()==pr_id && item.getPhId()==ph_id){
            item.setquantity(qty+item.getquantity());
            return;
        }
        else{
            cart.add(new CartItem(pr_id,ph_id,pr_name,ph_name,price,qty));
        }

    }  

}
    public boolean update(int pr_id,int ph_id,int quantity,int price){
        Iterator<CartItem> itr = cart.iterator();
        while(itr.hasNext()){
            CartItem item = itr.next();
            if(item.getProdId()==pr_id && item.getPhId()==ph_id){
                item.setquantity(quantity);
                item.setPrice(price);
                return true;
            }

        }
        return false;

    }
    public void remove(int pr_id,int ph_id){
        Iterator<CartItem> itr = cart.iterator();
        while(itr.hasNext()){
            CartItem item = itr.next();
            if(item.getProdId()==pr_id && item.getPhId()==ph_id){
                cart.remove(item);
                return;
            }
        }

    }
    public int size(){
        return cart.size();
    }
    public boolean isEmpty(){
        return (cart.size()==0);
    }
    public List<CartItem> getItems(){
        return cart;
    }
    public void clear(){
        cart.clear();
    }

}