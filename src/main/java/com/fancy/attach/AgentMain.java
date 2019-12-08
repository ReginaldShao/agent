package com.fancy.attach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.fancy.agent.Transformer;
import com.fancy.server.App;

public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst)
            throws UnmodifiableClassException, ClassNotFoundException {
        System.out.println("before main execute");
        Transformer transformer = new Transformer();
        inst.addTransformer(transformer);
        inst.retransformClasses(App.class);
        //        ClassDefinition classDefinition = new ClassDefinition(App.class, getBytesFromFile
        //       ("/Users/shaojie05/baidu"
        //                + "/icafe/agent/target/classes/com/fancy/server/AppNew.class"));
        //        inst.redefineClasses(classDefinition);
        System.out.println("Agent main finished");
    }

    public static byte[] getBytesFromFile(String fileName) {
        try {
            // precondition
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        }
    }
}
