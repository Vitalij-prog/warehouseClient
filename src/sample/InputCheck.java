package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class InputCheck {

    public static final int LENGTH_PRODUCT_NAME = 30;
    public static final int LENGTH_USER_NAME = 30;
    public static final int LENGTH_USER_PASSWORD = 30;
    public static boolean isCorrectString(String str, int correct_size) {
        if(str.length() <= correct_size) {
            return true;
        }
        return false;
    }

    public static boolean isInteger(String str) {
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDate(String str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy");
            java.util.Date docDate = format.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean checkChoiceForSearchingProducts(String option, String data) {
        boolean res = true;
        switch(option) {
            case "product_name":
                res = isCorrectString(data,LENGTH_PRODUCT_NAME);
                break;
            case "product_price":
                res = isDouble(data);
                break;
            case "amount":
                res = isInteger(data);
                break;
        }
        return res;
    }

    public static boolean checkChoiceForSearchingOrders(String option, String data) {
        boolean res = true;
        switch(option) {
            case "product_name":
                res = isCorrectString(data,LENGTH_PRODUCT_NAME);
                break;
            case "user_name":
                res = isCorrectString(data,LENGTH_USER_NAME);
                break;
            case "price":
                res = isDouble(data);
                break;
            case "date":
                res = isDate(data);
                break;
        }
        return res;
    }


}
