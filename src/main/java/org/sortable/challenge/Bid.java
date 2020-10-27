package org.sortable.challenge;

public class Bid {
    private final Bidder bidder;
    private final String bidUnitName;
    private final double bidAmount;

    public Bid(final String bidderName, final String bidUnitName, final double bidAmount) {
        this.bidder = Bidder.getNamed(bidderName);
        this.bidUnitName = bidUnitName;
        this.bidAmount = bidAmount;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public String getBidUnitName() {
        return bidUnitName;
    }

}
