package sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class InputCheck {

    public static final int LENGTH_PRODUCT_NAME = 30;
    public static final int LENGTH_USER_NAME = 30;
    public static final int LENGTH_USER_PASSWORD = 30;
    public static final int LENGTH_ROLE = 10;
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
            case "номеру":
            case "количеству":
                res = isInteger(data);
                break;
            case "названию":
            case "производителю":
                res = isCorrectString(data,LENGTH_PRODUCT_NAME);
                break;
        }
        return res;
    }
    public static boolean checkChoiceForSearchingUsers(String option, String data) {
        boolean res = true;
        switch(option) {
            case "номеру":
                res = isInteger(data);
                break;
            case "имени":
                res = isCorrectString(data,LENGTH_PRODUCT_NAME);
                break;
            case "роли":
            case "статусу":
                res = isCorrectString(data,LENGTH_ROLE);
                break;
        }
        return res;
    }
    public static boolean checkChoiceForSearchingOrders(String option, String data) {
        boolean res = true;
        switch(option) {
            case "номеру":
                res = isInteger(data);
                break;
            case "имени пользователя":
                res = isCorrectString(data,LENGTH_USER_NAME);
                break;
            case "названию товара":
                res = isCorrectString(data, LENGTH_PRODUCT_NAME);
                break;
            case "дате заказа":
                res = isDate(data);
                break;
        }
        return res;
    }

    public static boolean checkChoiceForSearchingSupplies(String option, String data) {
        boolean res = true;
        switch(option) {
            case "номеру":
                res = isInteger(data);
                break;
            case "имени пользователя":
                res = isCorrectString(data,LENGTH_USER_NAME);
                break;
            case "названию товара":
                res = isCorrectString(data, LENGTH_PRODUCT_NAME);
                break;
            case "дате поставки":
                res = isDate(data);
                break;
        }
        return res;
    }

}
