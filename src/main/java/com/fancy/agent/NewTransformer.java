/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.fancy.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CtMethod;

public class NewTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        return new byte[0];
    }

    public void cost(CtMethod ctMethod) {
        long startTime = System.currentTimeMillis();
    }
}
