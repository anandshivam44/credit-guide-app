package emeraldrating.creditguideapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

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
    int standardPaymentTerm;


    String[] countryNames = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola",
            "Anguilla", "Antarctica", "Antigua & Barbuda", "Argentina", "Armenia", "Aruba", "Australia",
            "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "BES Islands (Bonaire, St Eustatius, Saba)", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Myanmar (Burma)", "Burundi",
            "Cambodia", "Cameroon", "Canada", "Cape Verde Islands", "Cayman Islands", "Central African Republic",
            "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros",
            "Congo (Democratic Rep Of)", "Congo (People's Rep Of)", "Cook Islands", "Costa Rica", "Côte d'Ivoire",
            "Croatia", "Cuba", "Curacao", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica",
            "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
            "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France",
            "French Guiana", "French Polynesia", "French Southern Territory", "Gabon", "Gambia", "Georgia",
            "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
            "Guinea (Rep Of)", "Guinea Bissau", "Guyana", "Haiti", "Heard and McDonald Islands", "Honduras",
            "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel",
            "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "North Korea",
            "South Korea", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macao", "Macedonia", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte",
            "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",
            "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua",
            "Nigeria", "Niger", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan",
            "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",
            "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "South Georgia/Sandwich Islands",
            "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles",
            "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "South Sudan", "Spain", "Sri Lanka", "St Helena", "St. Kitts & Nevis", "St. Maarten", "St. Lucia",
            "St. Pierre Et Miquelon", "St. Vincent & The Grenadines", "Sudan", "Suriname", "Svalbard & Jan Mayen",
            "Swaziland", "Sweden", "Switzerland", "Syria", "Tajikistan", "Taiwan", "Tanzania", "Timor Leste", "Thailand",
            "Togo", "Tokelau", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks & Caicos",
            "Tuvalu", "United Arab Emirates", "US Virgin Islands", "US Minor Outlying Islands", "United States", "Uganda",
            "Ukraine", "United Kingdom", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
            "Wallis & Futuna", "Yemen", "Zambia", "Zimbabwe"};
    int[] countryRiskLevels = {4, 3, 2, 1, 1, 3, 1, 1, 3, 3, 4, 1, 1, 1, 4, 2, 3, 3, 3, 4, 1, 3, 3, 1,
            1, 3, 4, 4, 1, 1, 3, 1, 1, 2, 2, 3, 4, 4, 3, 3, 1, 4, 1, 4, 4, 2, 2, 1, 1, 2, 4, 4, 4, 3,
            1, 3, 3, 4, 3, 2, 1, 1, 4, 3, 2, 4, 3, 2, 4, 4, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 3, 3, 4, 1,
            2, 1, 3, 1, 3, 1, 1, 1, 4, 4, 3, 4, 1, 2, 2, 2, 2, 1, 1, 4, 4, 1, 1, 2, 4, 1, 2, 4, 2, 3,
            4, 1, 1, 4, 4, 1, 4, 3, 4, 4, 1, 1, 1, 2, 3, 4, 3, 2, 4, 4, 1, 4, 1, 4, 1, 1, 2, 3, 4, 1,
            4, 4, 1, 1, 4, 2, 4, 4, 1, 1, 1, 3, 3, 4, 2, 1, 1, 1, 2, 4, 3, 1, 4, 2, 1, 1, 1, 1, 2, 3,
            2, 1, 1, 4, 3, 1, 3, 1, 3, 2, 3, 3, 3, 4, 2, 1, 2, 4, 4, 2, 4, 2, 3, 1, 3, 3, 3, 1, 3, 4,
            4, 1, 3, 1, 1, 4, 4, 2, 3, 4, 1, 3, 1, 4, 3, 3, 3, 4, 1, 3, 1, 1, 1, 1, 3, 4, 1, 2, 4, 3,
            1, 4, 3, 1, 4, 3, 4};
    int[] countryRiskScores = {20, 40, 50, 70, 70, 40, 70, 70, 40, 40, 20, 70, 70, 70, 20, 50, 40, 40,
            40, 20, 70, 40, 40, 70, 70, 40, 20, 20, 70, 70, 40, 70, 70, 50, 50, 40, 20, 20, 40, 40,
            70, 20, 70, 20, 20, 50, 50, 70, 70, 50, 20, 20, 20, 40, 70, 40, 40, 20, 40, 50, 70, 70,
            20, 40, 50, 20, 40, 50, 20, 20, 70, 40, 70, 70, 20, 70, 70, 70, 70, 70, 40, 40, 20, 70,
            50, 70, 40, 70, 40, 70, 70, 70, 20, 20, 40, 20, 70, 50, 50, 50, 50, 70, 70, 20, 20, 70,
            70, 50, 20, 70, 50, 20, 50, 40, 20, 70, 70, 20, 20, 70, 20, 40, 20, 20, 70, 70, 70, 50,
            40, 20, 40, 50, 20, 20, 70, 20, 70, 20, 70, 70, 50, 40, 20, 70, 20, 20, 70, 70, 20, 50,
            20, 20, 70, 70, 70, 40, 40, 20, 50, 70, 70, 70, 50, 20, 40, 70, 20, 50, 70, 70, 70, 70,
            50, 40, 50, 70, 70, 20, 40, 70, 40, 70, 40, 50, 40, 40, 40, 20, 50, 70, 50, 20, 20, 50,
            20, 50, 40, 70, 40, 40, 40, 70, 40, 20, 20, 70, 40, 70, 70, 20, 20, 50, 40, 20, 70, 40,
            70, 20, 40, 40, 40, 20, 70, 40, 70, 70, 70, 70, 40, 20, 70, 50, 20, 40, 70, 20, 40, 70,
            20, 40, 20};
    int[] countryStandardPaymentTerms = {0, 15, 30, 30, 30, 15, 30, 30, 15, 15, 0, 30, 30, 30, 0, 30,
            15, 15, 15, 0, 30, 15, 15, 30, 30, 15, 0, 0, 30, 30, 15, 30, 30, 30, 30, 15, 0, 0, 15,
            15, 30, 0, 30, 0, 0, 30, 30, 30, 30, 30, 0, 0, 0, 15, 30, 15, 15, 0, 15, 30, 30, 30, 0,
            15, 30, 0, 15, 30, 0, 0, 30, 15, 30, 30, 0, 30, 60, 30, 30, 30, 15, 15, 0, 30, 30, 30,
            15, 30, 15, 30, 30, 30, 0, 0, 15, 0, 30, 30, 30, 30, 30, 30, 30, 0, 0, 30, 30, 30, 0,
            30, 30, 0, 30, 15, 0, 30, 30, 0, 0, 30, 0, 15, 0, 0, 30, 30, 30, 30, 15, 0, 15, 30, 0,
            0, 30, 0, 30, 0, 30, 30, 30, 15, 0, 30, 0, 0, 30, 30, 0, 30, 0, 0, 30, 30, 30, 15, 15,
            0, 30, 30, 30, 30, 30, 0, 15, 30, 0, 30, 30, 30, 30, 30, 30, 15, 30, 30, 30, 0, 15, 30,
            15, 30, 15, 30, 15, 15, 15, 0, 30, 30, 30, 0, 0, 30, 0, 30, 15, 30, 15, 15, 15, 30, 15,
            0, 0, 30, 15, 30, 30, 0, 0, 30, 15, 0, 30, 15, 30, 0, 15, 15, 15, 0, 30, 15, 30, 30,
            30, 30, 15, 0, 30, 30, 0, 15, 30, 0, 15, 30, 0, 15, 0};
    int[] countryTolerances = {0, 15, 15, 15, 15, 15, 15, 15, 15, 15, 0, 15, 15, 15, 0, 15, 15, 15,
            15, 0, 15, 15, 15, 15, 15, 15, 0, 0, 15, 15, 15, 15, 15, 15, 15, 15, 0, 0, 15, 15, 15,
            0, 15, 0, 0, 15, 15, 15, 15, 15, 0, 0, 0, 15, 15, 15, 15, 0, 15, 15, 15, 15, 0, 15,
            15, 0, 15, 15, 0, 0, 15, 15, 15, 15, 0, 15, 0, 15, 15, 15, 15, 15, 0, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 0, 0, 15, 0, 15, 15, 15, 15, 15, 15, 15, 0, 0, 15, 15, 30, 0, 15,
            15, 0, 15, 15, 0, 15, 15, 0, 0, 15, 0, 15, 0, 0, 15, 15, 15, 15, 15, 0, 15, 15, 0, 0,
            15, 0, 15, 0, 15, 15, 15, 15, 0, 15, 0, 0, 15, 15, 0, 15, 0, 0, 15, 15, 15, 15, 15, 0,
            15, 15, 15, 15, 15, 0, 15, 15, 0, 15, 15, 15, 15, 15, 30, 15, 15, 15, 15, 0, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 0, 15, 15, 15, 0, 0, 15, 0, 30, 15, 15, 15, 15, 15, 15, 15, 0,
            0, 15, 15, 15, 15, 0, 0, 15, 15, 0, 15, 15, 15, 0, 15, 15, 15, 0, 15, 15, 15, 15, 15,
            15, 15, 0, 15, 15, 0, 15, 15, 0, 15, 15, 0, 15, 0};

    String[] industryNames = {"Aeronautics", "Agrifood", "Automotive", "Chemicals", "Construction", "Energy",
            "Household Equipment", "Info/ Commnication Tech", "Machinery & Equipment", "Metal", "Paper",
            "Pharmaceuticals", "Retail", "Textile", "Transportation"};
    int[] industryRiskScores = {70, 70, 70, 70, 40, 50, 50, 50, 40, 40, 40, 70, 50, 40, 50};

    String[] rangesNaturalNumbers = {"1", "1,000", "10,000", "20,000", "50,000", "1,00,000", "2,00,000",
            "5,00,000", "10,00,000", "20,00,000", "50,00,000", "1,00,00,000"};
    String[] rangesIntegerNumbers = {"Negative", "1", "1,000", "10,000", "20,000", "50,000", "1,00,000",
            "2,00,000", "5,00,000", "10,00,000", "20,00,000", "50,00,000", "1,00,00,000"};

    Button outputButton;
    TextView showResult;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        outputButton = findViewById(R.id.go_to_output);
        showResult = findViewById(R.id.show_result);
        builder = new AlertDialog.Builder(this);

        /**
         * First Spinner
         * Choose Country
         */
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
        spinnerCountry.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter adapterCountry = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNames);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCountry.setAdapter(adapterCountry);

        /**
         * Second Spinner
         * Choose Industry
         */
        Spinner spinnerIndustry = (Spinner) findViewById(R.id.spinner_industry);
        spinnerIndustry.setOnItemSelectedListener(this);
        ArrayAdapter adapterIndustry = new ArrayAdapter(this, android.R.layout.simple_spinner_item, industryNames);
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndustry.setAdapter(adapterIndustry);

        /**
         * Third Spinner
         * Choose Current Assets
         */
        Spinner spinnerCurrentAssets = (Spinner) findViewById(R.id.spinner_current_assets);
        spinnerCurrentAssets.setOnItemSelectedListener(this);
        ArrayAdapter adapterCurrentAssets = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterCurrentAssets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrentAssets.setAdapter(adapterCurrentAssets);

        /**
         * Fourth Spinner
         * Choose -
         */
        Spinner spinnerCurrentLiabilities = (Spinner) findViewById(R.id.spinner_current_liabilities);
        spinnerCurrentLiabilities.setOnItemSelectedListener(this);
        ArrayAdapter adapterCurrentLiabilities = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterCurrentLiabilities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrentLiabilities.setAdapter(adapterCurrentLiabilities);


        /**
         * Fifth Spinner
         * Choose Inventories
         */
        Spinner spinnerInventories = (Spinner) findViewById(R.id.spinner_inventories);
        spinnerInventories.setOnItemSelectedListener(this);
        ArrayAdapter adapterInventories = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterInventories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInventories.setAdapter(adapterInventories);


        /**
         * Sixth Spinner
         * Choose Equity
         */
        Spinner spinnerEquity = (Spinner) findViewById(R.id.spinner_equity);
        spinnerEquity.setOnItemSelectedListener(this);
        ArrayAdapter adapterEquity = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterEquity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquity.setAdapter(adapterEquity);


        /**
         * Seventh Spinner
         * Choose Total Assets
         */
        Spinner spinnerTotalAssets = (Spinner) findViewById(R.id.spinner_total_assets);
        spinnerTotalAssets.setOnItemSelectedListener(this);
        ArrayAdapter adapterTotalAssets = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterTotalAssets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTotalAssets.setAdapter(adapterTotalAssets);


        /**
         * Eighth Spinner
         * Choose Profit
         */
        Spinner spinnerProfit = (Spinner) findViewById(R.id.spinner_profit);
        spinnerProfit.setOnItemSelectedListener(this);
        ArrayAdapter adapterProfit = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterProfit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfit.setAdapter(adapterProfit);

        /**
         * Ninth Spinner
         * Choose Profit Last Period
         */
        Spinner spinnerProfitLastPeriod = (Spinner) findViewById(R.id.spinner_profit_last_period);
        spinnerProfitLastPeriod.setOnItemSelectedListener(this);
        ArrayAdapter adapterProfitLastPeriod = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterProfitLastPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfitLastPeriod.setAdapter(adapterProfitLastPeriod);


        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = CalculateResult();


//                //Setting message manually and performing action on button click
//                builder.setMessage(message)
//                        .setCancelable(true)
//                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        })
//                ;
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                //Setting the title manually
//                alert.setTitle("Result");
//                alert.show();
                showResult.setText(message);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_country:
                Country = countryNames[position];
                Tolerance = countryTolerances[position];
                standardPaymentTerm = countryStandardPaymentTerms[position];
                countryRiskScore = countryRiskScores[position];
//                Toast.makeText(getApplicationContext(), countryName[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Country = " + countryNames[position]);
                break;
            case R.id.spinner_industry:
                Industry = industryNames[position];
                industryRiskScore = industryRiskScores[position];
//                Toast.makeText(getApplicationContext(), industryName[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Industry = " + industryNames[position]);
                break;
            case R.id.spinner_current_assets:
                currentAsset = rangesNaturalNumbers[position];
//                Toast.makeText(getApplicationContext(), rangesNaturalNumbers[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Current Assets = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_current_liabilities:
                currentLiabilities = rangesNaturalNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_inventories:
                Inventories = rangesNaturalNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_equity:
                Equity = rangesIntegerNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_total_assets:
                totalAssets = rangesNaturalNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_profit:
                Profit = rangesIntegerNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_profit_last_period:
                profitLastPeriod = rangesIntegerNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    String CalculateResult() {
        Calculator object = new Calculator(Country,
                Industry,
                currentAsset,
                currentLiabilities,
                Inventories,
                Equity,
                totalAssets,
                Profit,
                profitLastPeriod,
                countryRiskScore,
                industryRiskScore,
                Tolerance,
                standardPaymentTerm);
        String riskClass = object.getRiskClass();
        String paymentTerms = object.getPaymentTerm();
        String paymentTermTolerance = object.getPaymentTermTolerance();
        String maxOrderSize = String.valueOf(object.getMaximumOrderSize());
        String creditLimit = String.valueOf(object.getCreditLimit());
        String average = object.getAverage();

        String result = "Risk Class = " + riskClass + "\n" + "Payment Term = "
                + paymentTerms + "\n" + "Payment Term Tolerance = " +
                paymentTermTolerance + "\n" + "Maximum Order Size = " + maxOrderSize +
                "\n" + "Credit Limit = " + creditLimit +
                "\nAverage Risk Score = " + average;
        return result;
    }
}