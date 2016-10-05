package com.example.alexdnn10.pick4me;

class Person {
    String username;
    String time;
    int userphoto;
    int photoId_1;
    int photoId_2;
    int photoId_3;
    int photoId_4;

    Person(String username, String time, int userphoto, int photoId_1, int photoId_2, int photoId_3, int photoId_4) {
        this.username = username;
        this.time = time;
        this.userphoto = userphoto;
        this.photoId_1 = photoId_1;
        this.photoId_2 = photoId_2;
        this.photoId_3 = photoId_3;
        this.photoId_4 = photoId_4;
    }
}