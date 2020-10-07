package techtest.com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Cache<T> {
    /**
     * The class Cache constructs a remote cache, with
     * 1. LRU cache memory, with fixed capacity
     * 2. an address and port, treating the cache as a server that listen to the requests from
     * devices via network
     */

    LinkedHashMap<Integer, Object> cache;
    static int capacity;   // the capacity of cache
    int occupied;

    final String address;
    final int port;

    /**
     * Create a new cache with a given capacity, address and port
     */
    public Cache(int cap, String address, int port){
        cache = new LinkedHashMap<>(cap);
        this.capacity = cap;
        this.occupied = 0;

        this.address = address;
        this.port = port;
    }

    /**
     * Add an element to the cache with a given value "value"
     * Return true if added successfully
     * @param value
     */
    private boolean addNewElement(int key, Object value){
        // if the element exists in the cache, shift it to the end of cache
        boolean res = false;

        if(this.cache.containsValue(value)){
            // the target exists, stop adding
            this.cache.remove(value);
            this.cache.put(key, value);
            res = true;
        }else{
            if(this.occupied < this.capacity){
                // add the element to the end, and increments the value of occupied
                this.cache.put(key, value);
                this.occupied++;
                res = true;
            }else{
                // get the value of the first element, and remove it by that
                int k = this.cache.keySet().iterator().next();
                Object v = this.cache.get(k);
                this.cache.remove(v);
                // insert the new element
                this.cache.put(key,value);
            }
        }
        return res;
    }

    /**
     * Add a list of elements to the cache
     * @param list migrated list to the cache
     */
    public boolean addListOfElements(LinkedHashMap<Integer, Object> list){
        for(Map.Entry<Integer,Object> cur: list.entrySet()){
            boolean res = addNewElement(cur.getKey(), cur.getValue());
            if(!res)
                return res;
        }
        return true;
    }

    /**
     * Receive the request from client/server, returns the target value if it exists in cache; otherwise return -1
     * @param key the key of target instruction/data
     * @return
     */
    public Object getElement(int key){
        if(this.cache.containsKey(key)){
            return this.cache.get(key);
        }
        else return -1;
    }

    /**
     * Get the address of the cache
     * @return address of the cache
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the port of the cache
     * @return port of the cache
     */
    public int getPort() {
        return port;
    }

    /**
     * The method takes the input from LRU_distributed object through socket,
     * decides whether to send from/put in data to the cache.
     * The result is sent through socket to the LRU_distributed side after the requested operation is done.
     * @throws IOException
     */
    public void sendDataToClient() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        String input = dis.readUTF();

        int index = 0;
        String[] inputs = new String[2];
        StringTokenizer st = new StringTokenizer(input," ");
        while(st.hasMoreTokens()){
            inputs[index++] = st.nextToken();
        }

        Object value = null;
        DataOutputStream dout = null;
        if(index==1){
            // only one input, corresponding to get(key) from client
            try{
                value = getElement(Integer.parseInt(inputs[0]));
                dout = new DataOutputStream(s.getOutputStream());
                if((int)value!=-1){
                    dout.writeUTF("Get the data");
                    dout.flush();
                }
            }catch (NumberFormatException e){
                dout.writeUTF("Wrong data format: input an integer to find your data.");
                dout.flush();
            }

        }else if(index==2){
            // two inputs, key and value
            try{
                int key = Integer.parseInt(inputs[0]);
                Object val = inputs[1]; //assumes the data has been processed to desired form
                addNewElement(key, val);
                dout.writeUTF("Data added on the cache");
                dout.flush();
            }catch (NumberFormatException e){
                dout.writeUTF("Wrong data format: input an integer for the key.");
                dout.flush();
            }
        }
        dout.close();
    }

}
