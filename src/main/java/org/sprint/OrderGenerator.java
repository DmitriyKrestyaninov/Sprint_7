package org.sprint;
import org.sprint.ColorGenerator;
public class OrderGenerator{

    public static Order getDefault(){
        return new Order("Naruto","Uchiha","Konoha, 142 apt.",
                4,"+7 800 355 35 35",5,"2022-08-30",
                "Saske, come back to Konoha",ColorGenerator.getEmptyColors());
    }

    public static Order getBlackColor(){
        return new Order("Naruto","Uchiha","Konoha, 142 apt.",
                4,"+7 800 355 35 35",5,"2022-08-30",
                "Saske, come back to Konoha",ColorGenerator.getColorBlack());

    }
    public static Order getGreyColor(){
        return new Order("Naruto","Uchiha","Konoha, 142 apt.",
                4,"+7 800 355 35 35",5,"2022-08-30",
                "Saske, come back to Konoha",ColorGenerator.getColorGrey());
    }
    public static Order getBothColors(){
        return new Order("Naruto","Uchiha","Konoha, 142 apt.",
                4,"+7 800 355 35 35",5,"2022-08-30",
                "Saske, come back to Konoha",ColorGenerator.getColorBoth());
    }
}
