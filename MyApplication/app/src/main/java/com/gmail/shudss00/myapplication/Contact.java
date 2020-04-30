package com.gmail.shudss00.myapplication;

class Contact {
    final private String name;
    final private String number;
    final private int image;
    final private String email;
    final private String description;
    final private String number2;
    final private String email2;

    static final Contact[] contacts = {
            new Contact("Вася", "88005353535", R.mipmap.ic_launcher_round,
                    "blabla@gmail.com", "Просто Вася!",
                    "null", "null"),
            new Contact("Петя", "89126661313", R.mipmap.ic_launcher_round,
                    "petya228@gmail.com", "Петя есть петя.",
                    "null", "null"),
            new Contact("Android", "localhost:8888", R.mipmap.ic_launcher_round,
                    "gmail@gmail.com", "Всего лишь пародия. Имитация жизни",
                    "null", "null")
    };

    private Contact(String name, String number, int image, String email, String description,
                    String number2, String email2){
        this.name = name;
        this.number = number;
        this.image = image;
        this.email = (email != null) ? email : "null";
        this.description = (description != null) ? description : "null";
        this.number2 = (number2 != null) ? number2 : "null";
        this.email2 = (email2 != null) ? email2 : "null";
    }

    String getName() {
            return name;
    }
    String getNumber() {
        return number;
    }
    int getImage() { return image; }
    String getEmail() {
        return email;
    }
    String getDescription() {
        return description;
    }
    String getNumber2() {
        return number2;
    }
    String getEmail2() { return email2; }
}
