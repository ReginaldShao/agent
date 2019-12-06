package com.fancy.agent;

import java.lang.instrument.Instrumentation;


public class Agent {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("before main execute");
        inst.addTransformer(new Transformer());
        javassist.ClassPool classPool = new javassist.ClassPool();
    }
}
