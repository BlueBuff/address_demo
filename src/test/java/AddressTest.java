import com.alibaba.fastjson.JSON;
import org.hdg.model.Address;
import org.hdg.util.JsonFormatUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @auther: huangxiaojun
 * @date: 2019/04/08 14:13
 */
public class AddressTest {

    private List<Address> addressList = new ArrayList<Address>();

    private Map<Integer, Address> addressMap = new HashMap<Integer, Address>();

    private Map<Integer, List<Address>> addressListMap = new HashMap<>();


    @Before
    public void before() {
        addressList.add(new Address.Builder()
                .setId(1)
                .setName("中国")
                .setParent(0)
                .build());
        addressList.add(new Address.Builder()
                .setId(2)
                .setName("美国")
                .setParent(0)
                .build());
        addressList.add(new Address.Builder()
                .setId(3)
                .setName("湖南省")
                .setParent(1)
                .build());
        addressList.add(new Address.Builder()
                .setId(4)
                .setName("湖北省")
                .setParent(1)
                .build());
        addressList.add(new Address.Builder()
                .setId(5)
                .setName("河南省")
                .setParent(1)
                .build());
        addressList.add(new Address.Builder()
                .setId(6)
                .setName("华盛顿")
                .setParent(2)
                .build());
        addressList.add(new Address.Builder()
                .setId(7)
                .setName("纽约")
                .setParent(2)
                .build());

        addressList.add(new Address.Builder()
                .setId(8)
                .setName("长沙")
                .setParent(3)
                .build());
        addressList.add(new Address.Builder()
                .setId(9)
                .setName("衡阳")
                .setParent(3)
                .build());

        addressList.add(new Address.Builder()
                .setId(10)
                .setName("武汉")
                .setParent(4)
                .build());
        for (Address address : addressList) {
            addressMap.put(address.getId(), address);
        }
        System.out.println(addressMap);
    }

    @After
    public void print(){
        try {
            String jsonString = JSON.toJSONString(this.trans(addressListMap));
            System.out.println(jsonString);
            JsonFormatUtil.printJson(jsonString);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个国家所有省份以及城市
     */
    @Test
    public void selectOne() {
        this.addressListMap.putAll(this.find(1));
    }


    /**
     * 获取所有国家
     */
    @Test
    public void selectAll() {
        for (Address address : addressList) {
            if (address.getParent() != 0 || this.addressListMap.containsKey(address.getId())) {
                continue;
            }
            this.addressListMap.putAll(this.find(address.getId()));
        }
    }

    /**
     * 转换
     * @param addressListMap
     * @return
     * @throws CloneNotSupportedException
     */
    private List<Address> trans(Map<Integer, List<Address>> addressListMap) throws CloneNotSupportedException {
        List<Address> addresses = new ArrayList<>();
        for (Map.Entry<Integer, List<Address>> entry : addressListMap.entrySet()) {
            Address address = (Address) addressMap.get(entry.getKey()).clone();
            address.setChild(entry.getValue());
            addresses.add(address);
        }
        return addresses;
    }


    private Map<Integer, List<Address>> find(int patent) {
        Map<Integer, List<Address>> addressListMap = new HashMap<>();//临时缓存同一节点下的子节点地址信息
        for (Address address : addressList) {
            if (address.getParent() != patent) {
                continue;
            }
            //根据当前地址节点获取父节点地址信息
            Address parentAddress = this.addressMap.get(address.getParent());
            if (parentAddress == null) {
                continue;
            }
            List<Address> addresses = addressListMap.get(parentAddress.getId());
            if (addresses == null) {
                addresses = new ArrayList<>();
                addressListMap.put(parentAddress.getId(), addresses);
            }
            //根据当前id获取子节点地址信息
            Map<Integer, List<Address>> childMap = this.find(address.getId());
            addresses.add(new Address.Builder()
                    .setId(address.getId())
                    .setName(address.getName())
                    .setParent(address.getParent())
                    .setChild(childMap.isEmpty() ? new ArrayList<Address>(0) : childMap.get(address.getId()))
                    .build());
        }
        return addressListMap;
    }


}
