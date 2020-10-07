package techtest.com;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LRU_distributed {

    private Socket socket = null;
    private OutputStream out = null;
    private DataOutputStream dataOut = null;

    private String target_Addr;
    private int target_port;

    public static int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout of connection
     * @param timeout
     */
    public static void setTimeout(int timeout) {
        LRU_distributed.timeout = timeout;
    }

    static int timeout;

    /**
     * Construct a new LRU_distributed object
     * @param address the address of target cache
     * @param port the port of the target cache
     * @param timeout
     */
    public LRU_distributed(String address, int port, int timeout) {
        this.target_Addr = address;
        this.target_port = port;
        timeout = timeout;
    }

    /**
     * Build a socket connection with the target socket
     * @throws IOException
     */
    private void buildConnection() throws IOException{
        socket = new Socket();
        socket.connect(new InetSocketAddress(this.target_Addr, this.target_port), timeout);
        out = socket.getOutputStream();
        dataOut = new DataOutputStream(out);
    }

    /**
     * Try to send the get message with key k to the cache
     * @param k the key for getting data from cache
     * @return
     */
    public Object getData(int k) throws IOException{
        buildConnection();
        dataOut.writeUTF(""+k);
        dataOut.flush();
        dataOut.close();

        DataInputStream res = new DataInputStream(socket.getInputStream());
        socket.close();

        return processResult(res);
    }

    /**
     * Try to put the data to the cache
     * @param k key of the data
     * @param value
     * @throws IOException
     */
    public void putData(int k, Object value) throws IOException {
        buildConnection();
        dataOut.writeUTF(k+" "+value);
        dataOut.flush();
        dataOut.close();
        socket.close();
    }

    /**
     * Translate the data input from socket to the desired type
     * (Here takes String as an example)
     * @param inputStream
     * @return
     * @throws IOException
     */
    private Object processResult(DataInputStream inputStream) throws IOException {
        String result = (String)inputStream.readUTF();
        return result;
    }

}
