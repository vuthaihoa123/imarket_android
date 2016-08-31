package com.example.framgia.imarketandroid.data;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CartItem;
import com.example.framgia.imarketandroid.data.model.Category;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.DrawerItem;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.data.model.Market;
import com.example.framgia.imarketandroid.util.Flog;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoavt on 25/08/2016.
 */
public class FakeContainer {
    public static final String[] SUGGESTIONS = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain", "Viet Name"
    };
    public static final long GIA_SP = 18600000;
    public static final long CHI_PHI_PHAT_SINH = 50000;
    public static final String STORE_TYPE_1 = "1";
    public static final String STORE_TYPE_2 = "2";
    public static LatLng sLatLng = new LatLng(21.007380, 105.793139);
    public static float sGroundFirstParameter = 116f;
    public static float sGroundSecondParameter = 150f;
    public static float sGroundThirdParameter = 53f;
    public static final String ID_PRODUCT = "ID Product : ";
    public static final int ZOOM_RANGE = 15;
    public static final int CAMERA_PARAMETER = 100;

    public static ArrayList<CartItem> initCartProductList() {
        ArrayList<CartItem> list = new ArrayList<>();
        list.add(new CartItem(R.drawable.ic_iphone6s, "Iphone 6S", 16000000, 1, false));
        list.add(new CartItem(R.drawable.ic_iphone5s, "Iphone 5S", 14000000, 1, false));
        list.add(new CartItem(R.drawable.ic_htc_one, "HTC One", 12000000, 1, false));
        list.add(new CartItem(R.drawable.ic_sky_a850, "Sky A850", 4000000, 1, false));
        list.add(new CartItem(R.drawable.ic_lg_optimus, "LG Optimus", 9000000, 1, false));
        list.add(new CartItem(R.drawable.ic_window_phone, "Window Phone", 6000000, 1, false));
        list.add(new CartItem(R.drawable.ic_blackberry, "Blackberry", 12000000, 1, false));
        list.add(new CartItem(R.drawable.ic_nokia_n8, "Nokia", 5000000, 1, false));
        return list;
    }

    public static List<DrawerItem> initDrawerItems() {
        List<DrawerItem> drawerItems = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (i != 15) {
                drawerItems.add(new DrawerItem("Test " + (i + 1), "test"));
            } else {
                drawerItems.add(new DrawerItem("Test 16", "test", true));
            }
        }
        return drawerItems;
    }

    public static List<Market> initMarkets() {
        List<Market> markets = new ArrayList<>();
        markets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        markets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        markets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        markets.add(new Market("M0001", "Big C", "Trần Duy Hưng, Trung Hoà, Cầu Giấy, Hà Nội"));
        return markets;
    }

    public static String getNameProduct() {
        return "Điện thoại HTC One M8 Eye";
    }

    public static String getPriceProduct() {
        return "5.990.000đ";
    }

    public static String getInfoProduct() {
        return "    Màn hình:Super LCD 3, 5\", Full HD\n" +
                "    Hệ điều hành:Android 5.0 (Lollipop)\n" +
                "    Camera sau:13 MP\n" +
                "    Camera trước:5 MP\n" +
                "    CPU:Qualcomm Snapdragon 801 4 nhân 32-bit, 2.3 GHz\n" +
                "    RAM:2 GB\n" +
                "    Bộ nhớ trong:16 GB\n" +
                "    Hỗ trợ thẻ nhớ:MicroSD, 128 GB\n" +
                "    Thẻ SIM:1 Sim, Nano SIM\n" +
                "    Kết nối:WiFi, 3G, 4G LTE Cat 4\n" +
                "    Dung lượng pin:2600 mAh\n" +
                "    Thiết kế:Nguyên khối\n" +
                "    Chức năng đặc biệt:HTC BoomSound";
    }

    public static ArrayList<Integer> initIdResList() {
        ArrayList<Integer> list = new ArrayList<>();
        int numFakePreviews = 7;
        for (int i = 0; i < numFakePreviews; i++) {
            list.add(R.drawable.ic_htc_preview_01 + i);
        }
        return list;
    }

    public static int getPresentIconProduct(String nameProduct) {
        int idRes = -1;
        switch (nameProduct) {
            case "Iphone 6S":
                idRes = R.drawable.ic_iphone6s;
                break;
            case "Iphone 5S":
                idRes = R.drawable.ic_iphone5s;
                break;
            case "HTC One":
                idRes = R.drawable.ic_htc_one;
                break;
            case "Sky A850":
                idRes = R.drawable.ic_sky_a850;
                break;
            case "LG Optimus":
                idRes = R.drawable.ic_lg_optimus;
                break;
            case "Window Phone":
                idRes = R.drawable.ic_window_phone;
                break;
            case "Blackberry":
                idRes = R.drawable.ic_blackberry;
                break;
            case "Nokia":
                idRes = R.drawable.ic_nokia_n8;
                break;
            default:
                Flog.i("Nothing is match");
                break;
        }
        return idRes;
    }

    public static ArrayList<ItemProduct> getListProducts() {
        // Fake data
        ArrayList<ItemProduct> list = new ArrayList<>();
        list.add(new ItemProduct("Iphone 6S", "0%", R.drawable.ic_iphone6s));
        list.add(new ItemProduct("Iphone 5S", "10%", R.drawable.ic_iphone5s));
        list.add(new ItemProduct("HTC One", "20%", R.drawable.ic_htc_one));
        list.add(new ItemProduct("Sky A850", "0%", R.drawable.ic_sky_a850));
        list.add(new ItemProduct("LG Optimus", "5%", R.drawable.ic_lg_optimus));
        list.add(new ItemProduct("Window Phone", "0%", R.drawable.ic_window_phone));
        list.add(new ItemProduct("Blackberry", "0%", R.drawable.ic_blackberry));
        list.add(new ItemProduct("Nokia", "15%", R.drawable.ic_nokia_n8));
        return list;
    }

    public static List<CustomMarker> getCustomMarker() {
        List<CustomMarker> list = new ArrayList<>();
        Category category1 = new Category(STORE_TYPE_1, "Bakery Store");
        Category category2 = new Category(STORE_TYPE_1, "Bakery Store");
        Category category3 = new Category(STORE_TYPE_2, "Drug Store");
        list.add(new CustomMarker(3, 21.007380, 105.793139, 5, category1));
        list.add(new CustomMarker(4, 21.007480, 105.793139, 10, category2));
        list.add(new CustomMarker(5, 21.007580, 105.793139, 15, category3));
        return list;
    }
}
