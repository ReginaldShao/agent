package com.fancy.server;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("in main");
        App app = new App();
        app.sleep();
    }

    private String name;
    public String getName() {
        return name;
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(100);
    }
}
