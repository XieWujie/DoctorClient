package com.example.administrator.doctorClient.custom;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

@AVIMMessageType(
        type = 10
)
public class OrderMessage extends AVIMTypedMessage {

    @AVIMMessageField(name = "id")
    String id;

    public static final Creator<OrderMessage> CREATOR = new AVIMMessageCreator(OrderMessage.class);

    public OrderMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}