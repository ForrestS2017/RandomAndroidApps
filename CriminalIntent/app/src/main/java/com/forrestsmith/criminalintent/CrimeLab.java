package com.forrestsmith.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        // TODO: Replace following filler code

        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);    // Every other crime is solved
            mCrimes.add(crime);
        }

    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (id == crime.getId()) return crime;
        }

        return null;
    }

    /**
     * Get CrimeLab instance
     * @param context viewContext
     * @return instance of CrimeLab
     */
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }
}
