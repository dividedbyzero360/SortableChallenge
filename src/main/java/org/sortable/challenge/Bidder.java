package org.sortable.challenge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bidder {
    private static final Map<String, Bidder> bidderInstances = new HashMap<>();
    private static final String NAME_FIELD = "name";
    private static final String ADJUSTMENT_FIELD = "adjustment";

    private final String name;
    private final double adjustment;

    private Bidder(final String name, final double adjustment) {
        this.name = name;
        this.adjustment = adjustment;
    }

    public static Bidder getNamed(final String bidderName) {
        return bidderInstances.get(bidderName);
    }

    public static Collection<Bidder> createBiddersFromJSON(final JsonArray biddersList) {
        for (JsonElement eachBidder : biddersList) {
            JsonObject bidderJSON = eachBidder.getAsJsonObject();
            String name = bidderJSON.get(NAME_FIELD).getAsString();
            double adjustment = bidderJSON.get(ADJUSTMENT_FIELD).getAsDouble();
            new Bidder(name, adjustment).store();
        }
        return bidderInstances.values();
    }

    private void store() {
        bidderInstances.put(this.getName(), this);
    }

    public double amountAfterAdjustments(final double amount) {
        return amount + (amount * adjustment);
    }

    public String getName() {
        return name;
    }

    public double getAdjustment() {
        return adjustment;
    }
}
