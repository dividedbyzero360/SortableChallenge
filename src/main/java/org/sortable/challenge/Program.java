package org.sortable.challenge;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.sortable.challenge.gson.deserializers.AuctionDeserializer;
import org.sortable.challenge.gson.serializers.BidSerializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

public class Program {

    private static Gson gson;
    private static final String CONFIG_FILE = "/auction/config.json";

    public static void main(String[] args) {
        setUpFromConfig();
        gson = constructInstanceOfGson();
        List<Auction> auctions = getAuctionsFromInput();
        List<List<Bid>> allBidWinners = Auction.getBidWinnersFromAuctions(auctions);
        System.out.println(gson.toJson(allBidWinners));
    }

    private static List<Auction> getAuctionsFromInput() {
        JsonReader inputJsonReader = new JsonReader(new InputStreamReader(System.in));
        return gson.fromJson(inputJsonReader, new TypeToken<List<Auction>>() {
        }.getType());
    }

    private static Gson constructInstanceOfGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Auction.class, new AuctionDeserializer());
        gsonBuilder.registerTypeAdapter(Bid.class, new BidSerializer());

        return gsonBuilder.setPrettyPrinting().create();
    }


    private static void setUpFromConfig() {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(CONFIG_FILE));
            JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
            JsonArray biddersJSONArray = jsonObject.get("bidders").getAsJsonArray();
            JsonArray sitesJSONArray = jsonObject.get("sites").getAsJsonArray();

            Bidder.createBiddersFromJSON(biddersJSONArray);
            Site.createSitesFromJSON(sitesJSONArray);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
