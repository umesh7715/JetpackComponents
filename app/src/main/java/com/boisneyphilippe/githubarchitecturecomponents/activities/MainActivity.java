package com.boisneyphilippe.githubarchitecturecomponents.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.BuildConfig;
import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.GithubRespositorySearchFragment;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.PostListFragment;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.UserProfileFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static String USER_LOGIN = "JakeWharton";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDagger();
        this.showFragment(savedInstanceState);
        init();
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void init() {

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetch(60 * 1000)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mFirebaseRemoteConfig.activateFetched();
                    }
                });


    }

    // ---

    private void showFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            GithubRespositorySearchFragment fragment = new GithubRespositorySearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }
}
