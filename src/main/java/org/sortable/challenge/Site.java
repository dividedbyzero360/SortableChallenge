package org.sortable.challenge;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;


public class Site {

    private static final Map<String, Site> siteInstances = new HashMap<>();
    private static final String NAME_FIELD = "name";
    private static final String FLOOR_FIELD = "floor";
    private static final String BIDDERS_FIELD = "bidders";
    private final Set<Bidder> bidders;
    private final String name;
    private final double floor;

    private Site(final String name, final double floor, final Set<String> bidderNames) {
        this.name = name;
        this.floor = floor;
        this.bidders = new HashSet<>();
        constructBiddersFromBidderName(bidderNames);

    }

    public static Collection<Site> createSitesFromJSON(final JsonArray sitesList) {
        Iterator<JsonElement> iterator = sitesList.iterator();
        while (iterator.hasNext()) {
            JsonObject bidderJSON = iterator.next().getAsJsonObject();
            String name = bidderJSON.get(NAME_FIELD).getAsString();
            double floor = bidderJSON.get(FLOOR_FIELD).getAsDouble();
            Set<String> allowedBidderNames = new Gson().fromJson(bidderJSON.get(BIDDERS_FIELD), new TypeToken<Set<String>>() {
            }.getType());
            new Site(name, floor, allowedBidderNames).store();
        }
        return siteInstances.values();
    }

    public static Site getNamed(final String siteName) {
        return siteInstances.get(siteName);
    }

    private void constructBiddersFromBidderName(final Set<String> bidderNames) {
        if (bidderNames != null) {
            bidderNames.forEach((bidderName) -> {
                Bidder bidder = Bidder.getNamed(bidderName);
                if (bidder != null) {
                    this.bidders.add(bidder);
                }
            });
        }
    }

    public boolean isValidBidder(final Bidder bidder) {
        return this.bidders.stream().anyMatch((siteBidder) -> siteBidder.equals(bidder));
    }

    public Set<Bidder> getBidders() {
        return Collections.unmodifiableSet(bidders);
    }

    private void store() {
        siteInstances.put(this.getName(), this);
    }

    public String getName() {
        return name;
    }


    public double getFloor() {
        return floor;
    }
}