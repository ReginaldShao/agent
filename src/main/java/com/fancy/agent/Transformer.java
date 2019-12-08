package com.fancy.agent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * @author shaojie05
 */
public class Transformer implements ClassFileTransformer {
    private static final String prefix = "\nlong startTime = System.currentTimeMillis();";
    private static final String postfix = "\nlong endTime = System.currentTimeMillis();";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if (!className.startsWith("App")) {
            System.out.println("ignore retransform app");
            return null;
        }

        System.out.println("start retransform app");
        className = className.replace("/", ".");
        CtClass ctClass = null;
        try {
            ctClass = ClassPool.getDefault().getCtClass(className);
            for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                String methodName = ctMethod.getName();
                String oldMethodName = methodName + "$old";
                ctMethod.setName(oldMethodName);
                CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctClass, null);
                CtClass returnType = ctMethod.getReturnType();
                String retStr = "";
                if (!returnType.getName().equals("Void")) {
                    retStr = "return null;";
                }
                StringBuilder bodyStr = new StringBuilder();
                bodyStr.append("{")
                        .append("System.out.println(\"Enter↓: " + className + "." + methodName + "\");")
                        .append(prefix)
                        .append(oldMethodName + "($$);")
                        .append(postfix)
                        .append("System.out.println(\"Exit↑:" + className + "." + methodName + " Cost: \" + "
                                + "(endTime-startTime) +\"ms\");")
                .append(retStr).append("}");
                newMethod.setBody(bodyStr.toString());
                ctClass.addMethod(newMethod);
            }
            System.out.println("retransform finish");
            return ctClass.toBytecode();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
