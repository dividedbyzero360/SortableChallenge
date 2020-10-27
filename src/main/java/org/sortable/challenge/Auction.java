package org.sortable.challenge;

import java.util.*;


public class Auction {

    private final Site site;
    private final Set<String> units;
    private final List<Bid> bids;
    private final Map<String, List<Bid>> unitToBids = new LinkedHashMap<>();

    public Auction(final String siteName, final Set<String> units, final List<Bid> bids) {
        this.site = Site.getNamed(siteName);
        this.units= units;
        this.bids = bids;
        constructUnitToBidsMap();
    }

    private void constructUnitToBidsMap() {
        units.forEach((unit) ->{
            unitToBids.put(unit, new ArrayList<>());
        } );

        for(Bid abid: bids){
            String unitName = abid.getBidUnitName();
            if(unitToBids.containsKey(unitName)){
                unitToBids.get(unitName).add(abid);
            }
        }
    }


    public List<Bid> getBidWinnersForTheAuction() {
        List<Bid> result = new ArrayList<>();
        if(isSiteValid()){
            for (String unitName : unitToBids.keySet()) {
                Bid winnerBid = getBidWinnerForAnUnit(unitName);
                if (winnerBid != null) {
                    result.add(winnerBid);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    private Bid getBidWinnerForAnUnit(final String unitName) {
        List<Bid> bids = unitToBids.get(unitName);
        double minBidAmount = site.getFloor();
        Bid winner = null;
        for (Bid aBid : bids) {
            Bidder bidder = aBid.getBidder();
            boolean isValidBidder = isBidderValid(bidder);
            if (isValidBidder) {
                double trueBidAmountForBidder = bidder.amountAfterAdjustments(aBid.getBidAmount());
                // Picking the first bidder in case of tie.
                if (trueBidAmountForBidder > minBidAmount) {
                    minBidAmount = trueBidAmountForBidder;
                    winner = aBid;
                }
            }
        }
        return winner;
    }

    private boolean isSiteValid(){
        return site!=null;
    }

    private boolean isBidderValid(final Bidder bidder){
        return bidder!=null && site.isValidBidder(bidder);
    }

    public static List<List<Bid>> getBidWinnersFromAuctions(final Auction[] auctions){
        List<List<Bid>> result = new ArrayList<>();
        for (Auction anAuction : auctions) {
            List<Bid> bidWinnerForTheAuction = anAuction.getBidWinnersForTheAuction();
            result.add(bidWinnerForTheAuction);
        }
        return Collections.unmodifiableList(result);
    }

}
