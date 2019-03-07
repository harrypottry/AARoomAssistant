package com.aaroom.service;

import com.aaroom.beans.*;
import com.aaroom.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 温建成 on 2019/2/26.
 */
@Service
public class HotelAdditionService {

    @Autowired
    private HotelAdditionMapper hotelAdditionMapper;

    @Autowired
    private HotelContactsMapper hotelContactsMapper;

    @Autowired
    private HotelPathMapper hotelPathMapper;

    @Autowired
    private HotelWifiMapper hotelWifiMapper;

    @Autowired
    private HotelBaseMapper hotelBaseMapper;

    public HotelAdditionView getHotelAdditionView(Integer hotelId) {
        HotelAdditionView hotelAdditionView = new HotelAdditionView();
        HotelAdditionExample example = new HotelAdditionExample();
        HotelContactsExample example1 = new HotelContactsExample();
        HotelPathExample example2 = new HotelPathExample();
        HotelWifiExample example3 = new HotelWifiExample();
        HotelAdditionExample.Criteria criteria = example.createCriteria();
        HotelContactsExample.Criteria criteria1 = example1.createCriteria();
        HotelPathExample.Criteria criteria2 = example2.createCriteria();
        HotelWifiExample.Criteria criteria3 = example3.createCriteria();
        criteria.andHotelIdEqualTo(hotelId);
        criteria1.andHotelIdEqualTo(hotelId);
        criteria2.andHotelIdEqualTo(hotelId);
        criteria3.andHotelIdEqualTo(hotelId);
        List<HotelAddition> hotelAdditions = hotelAdditionMapper.selectByExample(example);
        HotelAddition hotelAddition = hotelAdditions.get(0);
        List<HotelContacts> hotelContacts = hotelContactsMapper.selectByExample(example1);
        HotelContacts hotelContacts1 = hotelContacts.get(0);
        List<HotelPath> hotelPaths = hotelPathMapper.selectByExample(example2);
        List<HotelWifi> hotelWifis = hotelWifiMapper.selectByExample(example3);
        hotelAdditionView.setHotelAddition(hotelAddition);
        hotelAdditionView.setHotelContacts(hotelContacts1);
        hotelAdditionView.setHotelPathList(hotelPaths);
        hotelAdditionView.setHotelWifiList(hotelWifis);
        HotelBase byId = hotelBaseMapper.getById(hotelId);
        hotelAdditionView.setHotelId(byId.getId());
        hotelAdditionView.setHotelName(byId.getHotel_name());
        hotelAdditionView.setAreaId(byId.getArea_id());
        hotelAdditionView.setProvinceId(byId.getProvince_id());
        hotelAdditionView.setCityId(byId.getCity_id());
        hotelAdditionView.setStreetAddress(byId.getStreet_address());
        hotelAdditionView.setRoomNum(byId.getRoom_num());
        return hotelAdditionView;
    }

    public Map<String,Object> insertHotelAdditionView(HotelAdditionView hotelAdditionView) {
        try {
            HotelBase hotelBase = new HotelBase();
            hotelBase.setId(hotelAdditionView.getHotelId());
            hotelBase.setHotel_name(hotelAdditionView.getHotelName());
            hotelBase.setArea_id(hotelAdditionView.getAreaId());
            hotelBase.setProvince_id(hotelAdditionView.getProvinceId());
            hotelBase.setCity_id(hotelAdditionView.getCityId());
            hotelBase.setStreet_address(hotelAdditionView.getStreetAddress());
            hotelBase.setRoom_num(hotelAdditionView.getRoomNum());
            hotelBaseMapper.modify(hotelBase);
            Integer id = hotelAdditionView.getHotelAddition().getId();
            if (id!=null){
                hotelAdditionMapper.updateByPrimaryKeySelective(hotelAdditionView.getHotelAddition());
                hotelContactsMapper.updateByPrimaryKeySelective(hotelAdditionView.getHotelContacts());
                for (HotelPath hotelPath:hotelAdditionView.getHotelPathList()
                     ) {
                    hotelPathMapper.updateByPrimaryKeySelective(hotelPath);
                }
                for (HotelWifi hotelWifi:hotelAdditionView.getHotelWifiList()
                     ) {
                    hotelWifiMapper.updateByPrimaryKeySelective(hotelWifi);
                }
            }else{
                hotelAdditionMapper.insertSelective(hotelAdditionView.getHotelAddition());
                hotelContactsMapper.insertSelective(hotelAdditionView.getHotelContacts());
                for (HotelPath hotelPath:hotelAdditionView.getHotelPathList()
                        ) {
                    hotelPathMapper.insertSelective(hotelPath);
                }
                for (HotelWifi hotelWifi:hotelAdditionView.getHotelWifiList()
                        ) {
                    hotelWifiMapper.insertSelective(hotelWifi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap("error", "保存信息失败！");
        }
        return resultMap("success", "保存信息成功！");
    }
    public Map<String, Object> resultMap(String value1, String value2) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", value1);
        map.put("msg", value2);
        return map;
    }
}
