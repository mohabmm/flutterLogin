package main.model;

/**
 * Created by Moha on 2/24/2018.
 */

public class Category {

    private  String Image;
    private String Name ;

    public Category() {
    }

    public Category(String image, String name) {
        Image = image;
        Name = name;
    }


    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }
}
