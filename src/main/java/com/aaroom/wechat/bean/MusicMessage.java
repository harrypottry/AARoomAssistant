package com.aaroom.wechat.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

import com.aaroom.wechat.bean.model.Music;

@XmlRootElement(name = "xml")
@Data
public class MusicMessage extends Message {

    @XmlElement(name = "MsgType")
    protected String MsgType = "music";

    /**
     * 音乐
     */
    @XmlElement(name = "Music")
    private Music Music;

}
