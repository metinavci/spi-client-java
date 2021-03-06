package com.assemblypayments.spi.model;

import com.assemblypayments.spi.util.Events;
import com.assemblypayments.spi.util.RequestIdHelper;

import java.util.HashMap;
import java.util.Map;

public class AccountVerifyRequest implements Message.Compatible {

    private final String posRefId;

    public AccountVerifyRequest(String posRefId) {
        this.posRefId = posRefId;
    }

    public String getPosRefId() {
        return posRefId;
    }

    @Override
    public Message toMessage() {
        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("pos_ref_id", posRefId);

        return new Message(RequestIdHelper.id("prav"), Events.ACCOUNT_VERIFY_REQUEST, data, true);
    }

}
