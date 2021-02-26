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
    int countryRiskScore;
    int industryRiskScore;

    public Calculator(String Country,
                      String Industry,
                      String currentAsset,
                      String currentLiabilities,
                      String Inventories,
                      String Equity,
                      String totalAssets,
                      String Profit,
                      String profitLastPeriod,
                      int countryRiskScore,
                      int industryRiskScore) {
        this.Country = Country;
        this.Industry = Industry;
        this.currentAsset = currentAsset;
        this.currentLiabilities = currentLiabilities;
        this.Inventories = Inventories;
        this.Equity = Equity;
        this.totalAssets = totalAssets;
        this.Profit = Profit;
        this.profitLastPeriod = profitLastPeriod;
        this.countryRiskScore = countryRiskScore;
        this.industryRiskScore = industryRiskScore;
    }

    public String getRiskClass() {
        double decimalCurrentAsset = convertCurrencyToDecimal(currentAsset);
        double decimalCurrentLiabilities = convertCurrencyToDecimal(currentLiabilities);
        double currentRatio = decimalCurrentAsset / decimalCurrentLiabilities;
        currentRatio = round(currentRatio, 1);

        double decimalInventories = convertCurrencyToDecimal(Inventories);
        double quickRatio = (decimalCurrentAsset - decimalInventories) / decimalCurrentLiabilities;
        quickRatio = round(quickRatio, 1);

        double debt_by_total_asset;
        double decimalTotalAssets = convertCurrencyToDecimal(totalAssets);
        try {
            debt_by_total_asset = (decimalTotalAssets - convertCurrencyToDecimal(Equity)) / decimalTotalAssets;
            debt_by_total_asset = round(debt_by_total_asset, 2);
        } catch (Exception e) {
            debt_by_total_asset = -1.0;
        }

        double return_on_assets_most_current_period;
        try {
            return_on_assets_most_current_period = convertCurrencyToDecimal(Profit) / decimalTotalAssets;
        } catch (Exception e) {
            return_on_assets_most_current_period = 0;
        }

        double return_on_assets_previous_period;
        try {
            return_on_assets_previous_period = convertCurrencyToDecimal(profitLastPeriod) / decimalTotalAssets;
        } catch (Exception e) {
            return_on_assets_previous_period = 0;
        }

        int scoreCurrentRatio, scoreQuickRatio, score_debt_by_total_assets, score_return_on_assets_most_current_period, score_return_on_assets_previous_period, scoreCountry, scoreIndustry;

        if (currentRatio >= 1) {
            scoreCurrentRatio = 100;
        } else {
            if (currentRatio >= 0.7) {
                scoreCurrentRatio = 50;
            } else {
                scoreCurrentRatio = 10;
            }
        }

        if (quickRatio >= 0.7) {
            scoreQuickRatio = 100;
        } else {
            if (quickRatio >= 0.5) {
                scoreQuickRatio = 50;
            } else {
                scoreQuickRatio = 10;
            }
        }

        /**
         * IF(B4<0,10,IF(B4<=0.7,100,IF(B4<=0.9,50,10)))
         */
        if (debt_by_total_asset < 0) {
            score_debt_by_total_assets = 10;
        } else {
            if (debt_by_total_asset <= 0.7) {
                score_debt_by_total_assets = 100;
            } else {
                if (debt_by_total_asset <= 0.9) {
                    score_debt_by_total_assets = 50;
                } else {
                    score_debt_by_total_assets = 10;
                }
            }
        }


        /**
         * =IF(B5>=0.2,100,IF(B5>=0.1,50,10))
         * */
        if (return_on_assets_most_current_period >= 0.2) {
            score_return_on_assets_most_current_period = 100;
        } else {
            if (return_on_assets_most_current_period >= 0.1) {
                score_return_on_assets_most_current_period = 50;
            } else {
                score_return_on_assets_most_current_period = 10;
            }
        }


        /**
         * =IF(B6>=0.2,100,IF(B6>=0.1,50,10))
         * */
        if (return_on_assets_previous_period >= 0.2) {
            score_return_on_assets_previous_period = 100;
        } else {
            if (return_on_assets_previous_period >= 0.1) {
                score_return_on_assets_previous_period = 50;
            } else {
                score_return_on_assets_previous_period = 10;
            }
        }

        scoreCountry = countryRiskScore;
        scoreIndustry=industryRiskScore;
        int averageRiskScore =
                (scoreCurrentRatio +
                scoreQuickRatio +
                score_debt_by_total_assets +
                score_return_on_assets_most_current_period +
                score_return_on_assets_previous_period +
                scoreCountry +
                scoreIndustry)/7;

        /**
         * =IF(AVERAGE(C2:C8)>80,"Low",IF(AVERAGE(C2:C8)>50,"Medium","High"))
         */
        String Index;
        if (averageRiskScore>80){
            Index="Low";
        }
        else {
            if (averageRiskScore>50){
                Index="Medium";
            }
            else {
                Index="High";
            }
        }

        return Index;
    }

    /**
     * =IF(VLOOKUP($Input.D13,$Country.A:C,3,0)<40,
     * "Secured Payment Terms",
     * IF( D21=0,"Secured Payment Terms",
     * "Shipping Days plus "&VLOOKUP($Input.D13,$Country.A:D,4,0)))
     * */
    int getPaymentTerm(){
        if ()
        return 0;
    }

    double convertCurrencyToDecimal(String currency) {
        String num = currency.replace(",", "");
        double Decimal = Double.valueOf(num);
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
