/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.packageinstaller.role.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.packageinstaller.role.model.Role;
import com.android.packageinstaller.role.model.Roles;

/**
 * Activity for a default app.
 */
public class DefaultAppActivity extends FragmentActivity {

    private static final String LOG_TAG = DefaultAppActivity.class.getSimpleName();

    /**
     * Create an intent for starting this activity.
     *
     * @param roleName the name of the role for the default app
     * @param user the user for the default app
     * @param context the context to create the intent
     *
     * @return an intent to start this activity
     */
    @NonNull
    public static Intent createIntent(@NonNull String roleName, @NonNull UserHandle user,
            @NonNull Context context) {
        return new Intent(context, DefaultAppActivity.class)
                .putExtra(DefaultAppFragment.EXTRA_ROLE_NAME, roleName)
                .putExtra(Intent.EXTRA_USER, user);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String roleName = intent.getStringExtra(DefaultAppFragment.EXTRA_ROLE_NAME);
        UserHandle user = intent.getParcelableExtra(Intent.EXTRA_USER);

        Role role = Roles.getRoles(this).get(roleName);
        if (role == null) {
            Log.e(LOG_TAG, "Unknown role: " + roleName);
            finish();
            return;
        }

        if (savedInstanceState == null) {
            DefaultAppFragment fragment = DefaultAppFragment.newInstance(roleName, user);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }
}