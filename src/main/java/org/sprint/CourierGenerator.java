package org.sprint;

public class CourierGenerator {
    public static Courier getDeffault() {
        return new Courier("alexander_51", "1234", "Александр");
    }

    public static Courier getWithoutLogin() {
        return new Courier("1234", "Александр");
    }

    public static Courier getWithoutPassword() {
        return new Courier("Homelander");
    }

    public static Courier getNotExistsLogin() {
        return new Courier("alexander_5001",
                "1234", "Александр");
    }
}

