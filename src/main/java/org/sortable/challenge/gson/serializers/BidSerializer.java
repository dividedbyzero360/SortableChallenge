package org.sortable.challenge.gson.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.sortable.challenge.Bid;

import java.lang.reflect.Type;

public class BidSerializer implements JsonSerializer<Bid> {

    @Override
    public JsonElement serialize(final Bid bid,final Type type,final JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("bidder", bid.getBidder().getName());
        obj.addProperty("unit", bid.getBidUnitName());
        if(isDisplayBidAmountAsLong(bid.getBidAmount())){
            obj.addProperty("bid", (long)bid.getBidAmount());
        }
        else{
            obj.addProperty("bid", bid.getBidAmount());
        }
        return obj;
    }

    private boolean isDisplayBidAmountAsLong(final double bidAmount) {
        double bidAmountInDouble = bidAmount;
        long bidAmountInLong = ((Double) bidAmountInDouble).longValue();
        return bidAmountInDouble == bidAmountInLong;
    }
}
