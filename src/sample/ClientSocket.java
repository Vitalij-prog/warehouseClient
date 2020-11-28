package sample;

import entities.Manufacturer;
import entities.Order;
import entities.Product;
import entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import javafx.application.Application;

public class ClientSocket extends Application {
    private static Socket client;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    public static String userName; //user object/ session data

    public static User user;

    public ClientSocket(String addr, int port) {
        try {
            client = new Socket(addr, port);
            objectInputStream = new ObjectInputStream(client.getInputStream());
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/authorization.fxml"));
        primaryStage.setTitle("Авторизация в системе");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void close() throws IOException {
        if (client != null) client.close();
        if (objectOutputStream != null) objectOutputStream.close();
        if (objectInputStream != null) objectInputStream.close();
    }


    public void exit() throws IOException {
        objectOutputStream.writeObject("ex");
    }

    public static String authorization(String login, String password) throws Exception {
        String str = "user/login";
        User user = new User(login, password);
        objectOutputStream.writeObject(str);
        objectOutputStream.writeObject(user);
        String answer = (String) objectInputStream.readObject();
/*
        if (answer.equals("user")) {
            System.out.println("Пользователь " + login + " вошел в программу");
        } else if (answer.equals("admin")) {
            System.out.println("Администратор " + login + " вошел в программу");
        }*/
        return answer;
    }
    public static String registrationByUser(User user) {
        String str = "user/signup";
        String answer = "";
        try {
            objectOutputStream.writeObject(str);
            objectOutputStream.writeObject(user);
            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (answer.equals("success")) {
            return user.getRole();
        }
        return answer;
    }

    public static String registrationByUser(String login, String password) throws Exception {
        String str = "signup" + "/" + login + "/" + password;
        objectOutputStream.writeObject(str);
        String answer = (String) objectInputStream.readObject();

        if (answer.equals("success")) {

            System.out.println("user " + login + " is registered successfully");
        } else  {
            System.out.println("user " + login + " already exists");
        }
        return answer;
    }

    public static ArrayList<Product> getListProducts() throws Exception {

        objectOutputStream.writeObject("product/getList");
        ArrayList<Product> list = (ArrayList<Product>) objectInputStream.readObject();
        return list;
    }

    public static ArrayList<Product> searchProducts(String condition, String data) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            objectOutputStream.writeObject("product/search");
            objectOutputStream.writeObject(condition);
            objectOutputStream.writeObject(data);
            list = (ArrayList<Product>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Order> getListOrders() throws Exception {
        objectOutputStream.writeObject("getListOrders");

        ArrayList<Order> list = (ArrayList<Order>) objectInputStream.readObject();

        //Product pr = (Product) objectInputStream.readObject();
        //User pr = (User) objectInputStream.readObject();
        //String str = (String) objectInputStream.readObject();
        //ArrayList<String> list = (ArrayList<String>) objectInputStream.readObject();
        // System.out.println();
        return list;

    }

    public static ArrayList<Order> getListOrders(String option, String dataSearching) throws Exception {
        objectOutputStream.writeObject("getListOrdersOfUser/" + option + "/" + dataSearching ); //+ userName for users

        ArrayList<Order> list = (ArrayList<Order>) objectInputStream.readObject();

        //Product pr = (Product) objectInputStream.readObject();
        //User pr = (User) objectInputStream.readObject();
        //String str = (String) objectInputStream.readObject();
        //ArrayList<String> list = (ArrayList<String>) objectInputStream.readObject();
        // System.out.println();
        return list;

    }

    public static String addProduct(String prod_name, double price, int amount) throws Exception {

        objectOutputStream.writeObject("add/product");

        Product pr = new Product(0, prod_name, amount,  price,0);
        objectOutputStream.writeObject(pr);

        String answer = (String) objectInputStream.readObject();
        System.out.println("Add Product: " + answer);
        return answer;
    }

    public static String addProduct(Product product) {
        String answer = "";
        try {
            objectOutputStream.writeObject("product/add");
            objectOutputStream.writeObject(product);

            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static Product getProductById(int id) {
        Product product = null;
        try {
            objectOutputStream.writeObject("product/get");
            objectOutputStream.writeObject(id);
            product = (Product) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static String setProductById(Product product) {
        String answer = "";
        try {
            objectOutputStream.writeObject("product/set");
            objectOutputStream.writeObject(product);
            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return answer;
    }
    //-------------------------------------------------------------------------------
    public static ArrayList<Manufacturer> getListManufacturers() {

        ArrayList<Manufacturer> list = null;
        try {
            objectOutputStream.writeObject("manufacturer/getList");
            list = (ArrayList<Manufacturer>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String setManufacturerById(Manufacturer manufacturer) {
        String answer = "";
        try {
            objectOutputStream.writeObject("manufacturer/set");
            objectOutputStream.writeObject(manufacturer);
            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static Manufacturer getManufacturerById(int id) {
        Manufacturer manufacturer = null;
        try {
            objectOutputStream.writeObject("manufacturer/get");
            objectOutputStream.writeObject(id);
            manufacturer = (Manufacturer) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return manufacturer;
    }

    public static String addManufacturer(Manufacturer manufacturer) {
        String answer = "";
        try {
            objectOutputStream.writeObject("manufacturer/add");
            objectOutputStream.writeObject(manufacturer);

            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return answer;
    }

   /* public static String editProduct( int id, String name, double price, int amount) throws Exception {

        objectOutputStream.writeObject("edit/product");

        Product pr = new Product(id, name,  amount, price, 0);
        objectOutputStream.writeObject(pr);

        String answer = (String) objectInputStream.readObject();
        System.out.println("Edit Product: " + answer);
        return answer;
    }*/

    public static String del(int id, String entity) {
        String answer = "";
        String request = entity + "/del";
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.writeObject(id);
            answer = (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return answer;
    }

   /* public static String delProduct(int id) throws Exception {

        objectOutputStream.writeObject("del/product");

        Product pr = new Product(id,"",0,0,0);
        objectOutputStream.writeObject(pr);

        String answer = (String) objectInputStream.readObject();
        System.out.println("Del Product: " + answer);
        return answer;
    }*/

    public static Double getPrice(String prod_name) throws Exception {
        objectOutputStream.writeObject("getPrice/" + prod_name);

        Double price = (Double) objectInputStream.readObject();
        return price;
    }

    public static String addOrder(String prod_name, String user_name, double price, int amount) throws Exception {

        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Time time = new Time(Calendar.getInstance().getTime().getTime());
        objectOutputStream.writeObject("add/order");
        Order or = new Order(0, user_name, prod_name, amount, price, date, time, "");
        objectOutputStream.writeObject(or);

        String answer = (String) objectInputStream.readObject();
        System.out.println("Add Product: " + answer);
        return answer;
    }

    public static String getOrderStatus(String id) throws Exception {
        objectOutputStream.writeObject("getStatus/" + id);

        String answer = (String) objectInputStream.readObject();
        System.out.println("Gotten status: " + answer);
        return answer;
    }

    public static String updateOrderStatus(String id , String newStatus) throws Exception {
        objectOutputStream.writeObject("updateStatus/" + id + "/" + newStatus);
        String answer = (String) objectInputStream.readObject();
        System.out.println("Update status: " + answer);
        return answer;
    }
    //-------------------------------------------
    public static ArrayList<User> getListUsers() throws Exception {
        objectOutputStream.writeObject("user/getList");

        ArrayList<User> list = (ArrayList<User>) objectInputStream.readObject();

        return list;
    }

   /* public static String delUser(String id) throws Exception {
        objectOutputStream.writeObject("deleteUser/" + id);

        return (String)objectInputStream.readObject();
    }*/

    public static String addUser(User user) throws Exception {
        objectOutputStream.writeObject("user/add");
        objectOutputStream.writeObject(user);

        return (String)objectInputStream.readObject();
    }

    public static ArrayList<User> searchUsers(String condition, String data) {
        ArrayList<User> list = new ArrayList<>();
        try {
            objectOutputStream.writeObject("user/search");
            objectOutputStream.writeObject(condition);
            objectOutputStream.writeObject(data);
            list = (ArrayList<User>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
