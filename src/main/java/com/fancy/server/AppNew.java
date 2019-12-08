package com.fancy.server;

public class AppNew {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("in main");
        AppNew app = new AppNew();
        app.sleep();
        System.out.println("finish main");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void sleep() throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("start:" + start);
        Thread.sleep(100);
        long end = System.currentTimeMillis();
        System.out.println("end:" + end + "\n cost:" + (end - start));
    }
}
