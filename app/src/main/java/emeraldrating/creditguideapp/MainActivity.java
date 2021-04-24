package emeraldrating.creditguideapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

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

    String[] rangesNaturalNumbers = {"1", "1,000", "10,000", "20,000", "50,000", "100,000", "200,000",
            "500,000", "1,000,000", "2,000,000", "5,000,000", "10,000,000"};
    String[] rangesIntegerNumbers = {"Negative", "1", "1,000", "10,000", "20,000", "50,000", "100,000",
            "200,000", "500,000", "1,000,000", "2,000,000", "5,000,000", "10,000,000"};

    Button outputButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);// completely remove night mode

        outputButton = findViewById(R.id.go_to_output);
        SharedPreferences sh = getSharedPreferences("Spinner Items", MODE_PRIVATE);
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
        spinnerCountry.setSelection(sh.getInt("Country", 208), true);//208 is index no for Switzerland

        /**
         * Second Spinner
         * Choose Industry
         */
        Spinner spinnerIndustry = (Spinner) findViewById(R.id.spinner_industry);
        spinnerIndustry.setOnItemSelectedListener(this);
        ArrayAdapter adapterIndustry = new ArrayAdapter(this, android.R.layout.simple_spinner_item, industryNames);
        adapterIndustry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIndustry.setAdapter(adapterIndustry);
        spinnerIndustry.setSelection(sh.getInt("Industry", 3));

        /**
         * Third Spinner
         * Choose Current Assets
         */
        Spinner spinnerCurrentAssets = (Spinner) findViewById(R.id.spinner_current_assets);
        spinnerCurrentAssets.setOnItemSelectedListener(this);
        ArrayAdapter adapterCurrentAssets = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterCurrentAssets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrentAssets.setAdapter(adapterCurrentAssets);
        spinnerCurrentAssets.setSelection(sh.getInt("Current Assets", 3));

        /**
         * Fourth Spinner
         * Choose -
         */
        Spinner spinnerCurrentLiabilities = (Spinner) findViewById(R.id.spinner_current_liabilities);
        spinnerCurrentLiabilities.setOnItemSelectedListener(this);
        ArrayAdapter adapterCurrentLiabilities = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterCurrentLiabilities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrentLiabilities.setAdapter(adapterCurrentLiabilities);
        spinnerCurrentLiabilities.setSelection(sh.getInt("Current Liabilities", 3));


        /**
         * Fifth Spinner
         * Choose Inventories
         */
        Spinner spinnerInventories = (Spinner) findViewById(R.id.spinner_inventories);
        spinnerInventories.setOnItemSelectedListener(this);
        ArrayAdapter adapterInventories = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterInventories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInventories.setAdapter(adapterInventories);
        spinnerInventories.setSelection(sh.getInt("Inventories", 3));


        /**
         * Sixth Spinner
         * Choose Equity
         */
        Spinner spinnerEquity = (Spinner) findViewById(R.id.spinner_equity);
        spinnerEquity.setOnItemSelectedListener(this);
        ArrayAdapter adapterEquity = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterEquity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquity.setAdapter(adapterEquity);
        spinnerEquity.setSelection(sh.getInt("Equity", 3));


        /**
         * Seventh Spinner
         * Choose Total Assets
         */
        Spinner spinnerTotalAssets = (Spinner) findViewById(R.id.spinner_total_assets);
        spinnerTotalAssets.setOnItemSelectedListener(this);
        ArrayAdapter adapterTotalAssets = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesNaturalNumbers);
        adapterTotalAssets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTotalAssets.setAdapter(adapterTotalAssets);
        spinnerTotalAssets.setSelection(sh.getInt("Total Assets", 3));


        /**
         * Eighth Spinner
         * Choose Profit
         */
        Spinner spinnerProfit = (Spinner) findViewById(R.id.spinner_profit);
        spinnerProfit.setOnItemSelectedListener(this);
        ArrayAdapter adapterProfit = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterProfit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfit.setAdapter(adapterProfit);
        spinnerProfit.setSelection(sh.getInt("Profit", 3));

        /**
         * Ninth Spinner
         * Choose Profit Last Period
         */
        Spinner spinnerProfitLastPeriod = (Spinner) findViewById(R.id.spinner_profit_last_period);
        spinnerProfitLastPeriod.setOnItemSelectedListener(this);
        ArrayAdapter adapterProfitLastPeriod = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rangesIntegerNumbers);
        adapterProfitLastPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfitLastPeriod.setAdapter(adapterProfitLastPeriod);
        spinnerProfitLastPeriod.setSelection(sh.getInt("Profit Last Period", 3));


        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateResult();
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
        if (id == R.id.action_about) {
            Intent myIntent = new Intent(this, AboutUs.class);
            startActivity(myIntent);
            return true;
        } else if (id == R.id.action_privacy_policy) {
            Intent myIntent = new Intent(this, PrivacyPolicy.class);
            startActivity(myIntent); // https://emeraldrating.com/privacy-statement/
            return true;
        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@emeraldrating.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject: I loved your App");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences sharedPreferences = getSharedPreferences("Spinner Items", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
//        myEdit.putString("name", name.getText().toString());


        switch (parent.getId()) {
            case R.id.spinner_country:
                Country = countryNames[position];
                Tolerance = countryTolerances[position];
                standardPaymentTerm = countryStandardPaymentTerms[position];
                countryRiskScore = countryRiskScores[position];
//                Toast.makeText(getApplicationContext(), countryName[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Country = " + countryNames[position]);

                myEdit.putInt("Country", position);

                break;
            case R.id.spinner_industry:
                Industry = industryNames[position];
                industryRiskScore = industryRiskScores[position];
//                Toast.makeText(getApplicationContext(), industryName[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Industry = " + industryNames[position]);
                myEdit.putInt("Industry", position);
                break;
            case R.id.spinner_current_assets:
                currentAsset = rangesNaturalNumbers[position];
//                Toast.makeText(getApplicationContext(), rangesNaturalNumbers[position], Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onItemSelected: Current Assets = " + rangesNaturalNumbers[position]);
                myEdit.putInt("Current Assets", position);
                break;
            case R.id.spinner_current_liabilities:
                currentLiabilities = rangesNaturalNumbers[position];
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                myEdit.putInt("Current Liabilities", position);
                break;
            case R.id.spinner_inventories:
                Inventories = rangesNaturalNumbers[position];
                myEdit.putInt("Inventories", position);
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_equity:
                Equity = rangesIntegerNumbers[position];
                myEdit.putInt("Equity", position);
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_total_assets:
                totalAssets = rangesNaturalNumbers[position];
                myEdit.putInt("Total Assets", position);
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_profit:
                Profit = rangesIntegerNumbers[position];
                myEdit.putInt("Profit", position);
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
            case R.id.spinner_profit_last_period:
                profitLastPeriod = rangesIntegerNumbers[position];
                myEdit.putInt("Profit Last Period", position);
//                Log.d(TAG, "onItemSelected: Current Liabilities = " + rangesNaturalNumbers[position]);
                break;
        }

        myEdit.apply();//Apply Shared Prefs

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    void CalculateResult() {
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
        String creditLimit = String.valueOf(((int) object.getCreditLimit()));
        String average = object.getAverage();

        showDialog(riskClass,
                paymentTerms,
                paymentTermTolerance,
                maxOrderSize,
                creditLimit,
                average);
    }

    private void showDialog(String riskClass,
                            String paymentTerms,
                            String paymentTermTolerance,
                            String maxOrderSize,
                            String creditLimit,
                            String average) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentDialog editNameDialogFragment = FragmentDialog.newInstance(
                "Result",
                riskClass,
                paymentTerms,
                paymentTermTolerance,
                maxOrderSize,
                creditLimit,
                average);
        editNameDialogFragment.show(fm, "fragment_dialog");
    }

}