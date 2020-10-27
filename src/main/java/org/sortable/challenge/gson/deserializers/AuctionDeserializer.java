package org.sortable.challenge.gson.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.sortable.challenge.Auction;
import org.sortable.challenge.Bid;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuctionDeserializer implements JsonDeserializer<Auction> {

    private static final String SITE_NAME_FIELD = "site";
    private static final String SITE_UNITS_FIELD = "units";
    private static final String BIDS_FIELD = "bids";
    private static final String BID_BIDDER_NAME_FIELD = "bidder";
    private static final String BID_BIDDER_UNIT_FIELD = "unit";
    private static final String BID_BIDDER_BID_AMOUNT_FIELD = "bid";

    @Override
    public Auction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String siteName = jsonObject.get(SITE_NAME_FIELD).getAsString();
        Set<String> units = new Gson().fromJson(jsonObject.get(SITE_UNITS_FIELD), new TypeToken<Set<String>>() {
        }.getType());

        JsonArray bidArray = jsonObject.get(BIDS_FIELD).getAsJsonArray();
        List<Bid> bids = new ArrayList<>();
        for (JsonElement eachBid : bidArray) {
            JsonObject bidJsonObj = eachBid.getAsJsonObject();
            String bidderName = bidJsonObj.get(BID_BIDDER_NAME_FIELD).getAsString();
            String unitName = bidJsonObj.get(BID_BIDDER_UNIT_FIELD).getAsString();
            double bidAmount = bidJsonObj.get(BID_BIDDER_BID_AMOUNT_FIELD).getAsDouble();
            Bid bid = new Bid(bidderName, unitName, bidAmount);
            bids.add(bid);
        }
        return new Auction(siteName, units, bids);
    }
}

