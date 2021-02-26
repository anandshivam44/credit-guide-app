package emeraldrating.creditguideapp;

public class Calculator {

    String Country;
    String Industry;
    String currentAsset;
    String currentLiabilities;
    String Inventories;
    String Equity;
    String totalAssets;
    String Profit;
    String profitLastPeriod;

    public Calculator(String Country,
            String Industry,
            String currentAsset,
            String currentLiabilities,
            String Inventories,
            String Equity,
            String totalAssets,
            String Profit,
            String profitLastPeriod) {
        this.Country=Country;
        this.Industry=Industry;
        this.currentAsset=currentAsset;
        this.currentLiabilities=currentLiabilities;
        this.Inventories=Inventories;
        this.Equity=Equity;
        this.totalAssets=totalAssets;
        this.Profit=Profit;
        this.profitLastPeriod=profitLastPeriod;
    }

    public String getRiskClass(){
        double currentRatio=convertCurrencyToDecimal(currentAsset)/convertCurrencyToDecimal(currentLiabilities);
        currentRatio=round(currentRatio,1);
        return "High";
    }
    double convertCurrencyToDecimal(String currency){
        String num=currency.replace(",","");
        double Decimal=Double.valueOf(num);
        return Decimal;
    }
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
