package emeraldrating.creditguideapp;

import android.util.Log;

public class Calculator {

    private static final String TAG = "MyTag";
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
    int Tolerance;
    int standardPaymentTerms;

    double average;

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
                      int industryRiskScore,
                      int Tolerance,
                      int standardPaymentTerms) {
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
        this.Tolerance=Tolerance;
        this.standardPaymentTerms=standardPaymentTerms;
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
        scoreIndustry = industryRiskScore;
        double averageRiskScore =
                (scoreCurrentRatio +
                        scoreQuickRatio +
                        score_debt_by_total_assets +
                        score_return_on_assets_most_current_period +
                        score_return_on_assets_previous_period +
                        scoreCountry +
                        scoreIndustry) / 7.0;
        average = averageRiskScore;
        averageRiskScore=Math.round(averageRiskScore);

        Log.d(TAG, "Current Ratio  = "+currentRatio+"   "+scoreCurrentRatio);
        Log.d(TAG, "Quick Ratio  = "+quickRatio+"   "+scoreQuickRatio);
        Log.d(TAG, "Debt / Total Asset  = "+debt_by_total_asset+"   "+score_debt_by_total_assets);
        Log.d(TAG, "Return on Asset most current period  = "+return_on_assets_most_current_period+"   "+score_return_on_assets_most_current_period);
        Log.d(TAG, "Return on Asset previous period  = "+return_on_assets_previous_period+"   "+score_return_on_assets_previous_period);
        Log.d(TAG, "Country  = "+scoreCountry);
        Log.d(TAG, "Industry  = "+scoreIndustry);



        /**
         * =IF(AVERAGE(C2:C8)>80,"Low",IF(AVERAGE(C2:C8)>50,"Medium","High"))
         */
        String Index;
        if (averageRiskScore > 80) {
            Index = "Low";
        } else {
            if (averageRiskScore > 50) {
                Index = "Medium";
            } else {
                Index = "High";
            }
        }

        return Index;
    }

    /**
     * =IF(VLOOKUP($Input.D13,$Country.A:C,3,0)<40,
     * "Secured Payment Terms",
     * IF( D21=0,"Secured Payment Terms",
     * "Shipping Days plus "&VLOOKUP($Input.D13,$Country.A:D,4,0)))
     */


    String getPaymentTerm() {
        String PaymentIs;
        if (countryRiskScore < 40) {
            PaymentIs = "Secured Payment Terms";
        } else {
            if (getCreditLimit()==0){
                PaymentIs = "Secured Payment Terms";
            }
            else{
                PaymentIs = "Shipping Days plus "+String.valueOf(standardPaymentTerms);
            }
        }
        return PaymentIs;
    }

    /**
     * Credit Limit
     * =ROUND(IFERROR($Scoring.C9/100*$Input.D23,0),-4)
     */
    double getCreditLimit() {
        double creditLimit;
        try {
            creditLimit = (average * convertCurrencyToDecimal(Equity))/100.0;
        } catch (Exception e) {
            Log.d(TAG, "getCreditLimit: Exception e = "+e);
            creditLimit = 0;
        }
        Log.d(TAG, "getCreditLimit: before"+creditLimit);
        creditLimit = (int) (Math.round((creditLimit * 1.0) / 10000.0) * 10000);//round off
        Log.d(TAG, "getCreditLimit: after"+creditLimit);

        return creditLimit;
    }

    /**
     * =MIN(ROUND($Input.D17*0.5*$Scoring.C9/100,-4)
     * ,ROUND(IFERROR($Scoring.C9/100*$Input.D23,0),-4))
     */
    int getMaximumOrderSize() {
        double tempA = (convertCurrencyToDecimal(currentAsset) * 0.5 * average) / 100.0;
        tempA = (Math.round((tempA * 1.0) / 10000.0) * 10000);

        double tempB;
        try {
            tempB = average / 100.0 * convertCurrencyToDecimal(Equity);
            tempB = (Math.round((tempB * 1.0) / 10000.0) * 10000);
        } catch (Exception e) {
            tempB = 0;
        }

        return (int) Math.min(tempA, tempB);
    }

    /**
     * =IF(
     * VLOOKUP($Input.D13,$Country.A:C,3,0)<40,"",
     * IF(D21=0,0,
     * VLOOKUP($Input.D13,$Country.A:E,5,0)))
     **/
    String getPaymentTermTolerance() {
        String paymentTermTolerance;
        if (countryRiskScore<40){
            paymentTermTolerance="";
        }
        else{
            if (getCreditLimit()==0){
                paymentTermTolerance="0";
            }
            else{
                paymentTermTolerance=String.valueOf(Tolerance);
            }
        }

        return paymentTermTolerance;
    }

    String getAverage(){
        return String.valueOf(average);
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
