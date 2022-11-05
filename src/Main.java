import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class  product{
    @Override
    public String toString() {
        return "product{" +
                "productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    String productName;
    String category;
    int price;
    int discount;

    public product(String productName, String category,int price,int discount) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
public class Main {
    private static HashMap<String,product> productMap=new HashMap<>();
    private  static  int totalAmount=0;
    private  static  int totalDiscount=0;

    public static void main(String[] args) {


        productMap.put("TSHIRT",new product("TSHIRT","Clothing",1000,10));
        productMap.put("JACKET",new product("JACKET","Clothing",2000,5));
        productMap.put("CAP",new product("CAP","Clothing",500,20));
        productMap.put("NOTEBOOK",new product("NOTEBOOK","Stationery",200,20));
        productMap.put("PENS",new product("PENS","Stationery",300,10));
        productMap.put("MARKERS",new product("MARKERS","Stationery",500,5));

        HashMap<String,Integer> cart=new HashMap<>();

        String path=args[0];

        File file=new File(path);

        try
        {

            Scanner sc=new Scanner(file);
            while(sc.hasNext())
            {
                String in=sc.nextLine();

                String s[]=in.trim().split(" ");
                if(s[0].equals("ADD_ITEM"))
                {
                    addItem(s,cart);

                } else if (s[0].equals("PRINT_BILL")) {
                    printBill(cart);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  static  void  addItem(String s[],HashMap<String,Integer> cart)
    {
        product p=productMap.get(s[1]);
        int quantity=Integer.parseInt(s[2]);
        int limit=0;
        if(p.getCategory().equals("Stationery"))
        {
            limit=3;

        }else
        {

            limit=2;
        }

        if(quantity>limit||cart.containsKey(s[1])&&cart.get(s[1])+quantity>limit)
        {
            System.out.println("ERROR_QUANTITY_EXCEEDED");
            return;
        }
        cart.put(s[1],cart.getOrDefault(s[1],0)+quantity);
        totalAmount+=p.getPrice()*quantity;
        totalDiscount+=p.getPrice()*quantity*p.getDiscount()/100;
        System.out.println("ITEM_ADDED");

    }

    public  static  void  printBill(HashMap<String,Integer> cart)
    {
        double applicableDis=0;
        if(totalAmount>=1000)
        {
            applicableDis+=totalDiscount;
        }
        if(totalAmount>=3000)
            applicableDis+=totalAmount*5/100;
        double disAmount=totalAmount-applicableDis;
        System.out.println("TOTAL_DISCOUNT "+ String.format("%.2f",applicableDis));
        System.out.println("TOTAL_AMOUNT_TO_PAY "+String.format("%.2f",disAmount*(1.1)));
    }
}