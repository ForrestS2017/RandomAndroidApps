package com.forrestsmith.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.forrestsmith.criminalintent.crime_id";

    /**
     * Custom Intent creation - we use the UUID crimeID to pull the correct Crime from our list
     * @param packageContext    Calling activity
     * @param crimeID   UUID of crime we're calling
     * @return  new Intent created with id Extra
     */
    public static Intent newIntent(Context packageContext, UUID crimeID) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }


    /**
     * Calls CrimeFragment.newInstance to pass in id as argument bundle
     * @return new instance with proper arguments
     */
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}
