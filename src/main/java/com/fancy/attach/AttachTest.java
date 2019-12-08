package com.fancy.attach;

import com.fancy.server.App;

public class AttachTest {
    public AttachTest() {
    }

    public static void main(String[] args) throws InterruptedException {
        new App().sleep();
        int count = 0;
        while (count++ < 10) {
            Thread.sleep(1000);
            new App().sleep();
        }
    }
}
