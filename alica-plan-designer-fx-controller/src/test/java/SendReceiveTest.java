import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SendReceiveTest {
    final boolean DEBUG = true;

    final String ADDRESS = "udp://localhost:5555";
    final String GROUPNAME = "TestGroupName";

    public interface ZeroMQLibrary extends Library {

        int ZMQ_RADIO = 14;
        int ZMQ_DISH = 15;
        int ZMQ_RCVTIMEO = 27;

        ZeroMQLibrary INSTANCE = Native.load("libzmq", ZeroMQLibrary.class);

        void zmq_version(IntByReference major, IntByReference minor, IntByReference patch);

        PointerByReference zmq_ctx_new();

        PointerByReference zmq_socket(PointerByReference ctx, int type);

        int zmq_connect(PointerByReference ctx, String address);

        int zmq_setsockopt(PointerByReference socket, int option, IntByReference value, int valueLength);

        int zmq_join(PointerByReference socket, String groupName);

        int zmq_bind(PointerByReference socket, String address);

        int zmq_msg_init(Message msg);

        int zmq_msg_init_data(Message msg, PointerByReference data, int size, PointerByReference freeMethod, PointerByReference hint);

        PointerByReference zmq_msg_data(Message msg);

        int zmq_msg_size(Message msg);

        int zmq_msg_set_group(Message msg, String groupName);

        int zmq_msg_send(Message msg, PointerByReference socket, int flags);

        int zmq_msg_recv(Message msg, PointerByReference socket, int flags);

        int zmq_msg_close(Message msg);

        class Message extends Structure {
            public String _;

            public Message() {
                _ = "";
            }

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("_");
            }
        }

    }

    @Test
    public void testSend() {
        System.out.println("Test started");
        IntByReference major = new IntByReference();
        IntByReference minor = new IntByReference();
        IntByReference patch = new IntByReference();
        ZeroMQLibrary.INSTANCE.zmq_version(major, minor, patch);
        PointerByReference ctx = ZeroMQLibrary.INSTANCE.zmq_ctx_new();
        System.out.println("ZMQ Version: (" + major.getValue() + ", " + minor.getValue() + ", " + patch.getValue() + ")");

        // pub
        final PointerByReference pub_socket = ZeroMQLibrary.INSTANCE.zmq_socket(ctx, ZeroMQLibrary.ZMQ_RADIO);
        check(ZeroMQLibrary.INSTANCE.zmq_connect(pub_socket, ADDRESS), "zmq_connect");

        // sub
        IntByReference timeout = new IntByReference(500);
        final PointerByReference sub_socket = ZeroMQLibrary.INSTANCE.zmq_socket(ctx, ZeroMQLibrary.ZMQ_DISH);
        check(ZeroMQLibrary.INSTANCE.zmq_setsockopt(sub_socket, ZeroMQLibrary.ZMQ_RCVTIMEO, timeout, 4), "zmq_setsockopt"); //TODO maybe not only 4
        check(ZeroMQLibrary.INSTANCE.zmq_join(sub_socket, GROUPNAME), "zmq_join");
        check(ZeroMQLibrary.INSTANCE.zmq_bind(sub_socket, "udp://*:5555"), "zmq_bind");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Started Publisher");
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);

                        // publish a message
                        ZeroMQLibrary.Message msg = new ZeroMQLibrary.Message();
                        check(ZeroMQLibrary.INSTANCE.zmq_msg_init(msg), "zmq_msg_init");
                        PointerByReference data = new PointerByReference();
                        String str = "Hallo " + i; // TODO: > 8
                        Pointer pointer = data.getPointer();
                        pointer.setString(0, str);
                        check(ZeroMQLibrary.INSTANCE.zmq_msg_init_data(msg, data, str.length() + 1, null, null), "zmq_msg_init_data");
                        check(ZeroMQLibrary.INSTANCE.zmq_msg_set_group(msg, GROUPNAME), "zmq_msg_set_group");
                        System.out.print("Sending on Group \"" + GROUPNAME + "\": \"" + str + "\"");
                        int bytes = ZeroMQLibrary.INSTANCE.zmq_msg_send(msg, pub_socket, 0);
                        System.out.println(" (" + bytes + " bytes)" + "... done");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Started Subscriber");

                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);

                        // Some errors :(

                        ZeroMQLibrary.Message msg = new ZeroMQLibrary.Message();
                        check(ZeroMQLibrary.INSTANCE.zmq_msg_init(msg), "zmq_msg_init");
                        int bytes = ZeroMQLibrary.INSTANCE.zmq_msg_recv(msg, sub_socket, 0);
                        System.out.print("bytes: " + bytes + " | ");
                        if (bytes < 0) {
                            check(ZeroMQLibrary.INSTANCE.zmq_msg_close(msg), "zmq_msg_close");
                        }
                        if (bytes > 0) {
                            PointerByReference data = ZeroMQLibrary.INSTANCE.zmq_msg_data(msg);
                            int size = ZeroMQLibrary.INSTANCE.zmq_msg_size(msg);
                            System.out.print("Received \"" + "\".");
                        }
                        System.out.println();
                        check(ZeroMQLibrary.INSTANCE.zmq_msg_close(msg), "zmq_msg_close");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //t2.start();
        t1.run();

        System.out.println();
    }

    private void check(int returnCode, String nameOfMethod) {
        if (DEBUG && returnCode == 0) {
            System.out.println(nameOfMethod + " returned: " + returnCode);
        }
        if (returnCode != 0) {
            System.err.println(nameOfMethod + " returned: " + returnCode);
        }
        assertEquals(0, returnCode);
    }

}
