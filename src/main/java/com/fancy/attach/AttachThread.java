package com.fancy.attach;

import java.io.IOException;
import java.util.List;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class AttachThread extends Thread {
    private static final int TIMES = 10;
    private List<VirtualMachineDescriptor> listBefore;
    private String jar;

    public AttachThread(String attachJar, List<VirtualMachineDescriptor> vms) {
        this.jar = attachJar;
        this.listBefore = vms;
    }

    public static void main(String[] args) {
        new AttachThread("/Users/shaojie05/baidu/icafe/agent/target/agent-1.0-SNAPSHOT.jar", VirtualMachine.list()).start();
    }

    @Override
    public void run() {
        List<VirtualMachineDescriptor> listAfter;
        VirtualMachine vm = null;
        int count = 0;
        while (count++ < TIMES) {
            listAfter = VirtualMachine.list();
            for (VirtualMachineDescriptor vmd : listAfter) {
                if (!listBefore.contains(vmd)) {
                    try {
                        // 取得vm
                        vm = VirtualMachine.attach(vmd);
                        break;
                    } catch (AttachNotSupportedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (vm != null) {
                break;
            }
        }

        try {
            if (vm != null) {
                // 命令虚拟机加载agent
                vm.loadAgent(jar);
                // 断开连接
                vm.detach();
                System.out.println("attach finished");
            }
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
