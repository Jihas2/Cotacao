package org.example.Model;

public class Moeda {
    private String code;
    private String name;
    private double high;
    private double low;
    private double varBid;
    private double pctChange;
    private double bid;
    private double ask;
    private String timestamp;
    private String createDate;

    public Moeda() {}

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getHigh() { return high; }
    public void setHigh(double high) { this.high = high; }

    public double getLow() { return low; }
    public void setLow(double low) { this.low = low; }

    public double getVarBid() { return varBid; }
    public void setVarBid(double varBid) { this.varBid = varBid; }

    public double getPctChange() { return pctChange; }
    public void setPctChange(double pctChange) { this.pctChange = pctChange; }

    public double getBid() { return bid; }
    public void setBid(double bid) { this.bid = bid; }

    public double getAsk() { return ask; }
    public void setAsk(double ask) { this.ask = ask; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return String.format(
                "Moeda: %s (%s)\n" +
                        "Compra: R$ %.4f\n" +
                        "Venda: R$ %.4f\n" +
                        "Máxima: R$ %.4f\n" +
                        "Mínima: R$ %.4f\n" +
                        "Variação: %.2f%%\n" +
                        "Data/Hora: %s",
                name, code, bid, ask, high, low, pctChange, createDate
        );
    }
}