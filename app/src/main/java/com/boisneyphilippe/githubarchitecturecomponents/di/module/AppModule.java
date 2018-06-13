package com.boisneyphilippe.githubarchitecturecomponents.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.boisneyphilippe.githubarchitecturecomponents.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.api.WebAPIInterface;
import com.boisneyphilippe.githubarchitecturecomponents.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.PostDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Philippe on 02/03/2018.
 */

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "MyDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(MyDatabase database) { return database.userDao(); }

    @Provides
    @Singleton
    PostDao provideUPostDao(MyDatabase database) { return database.postDao(); }

    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserWebservice webservice, UserDao userDao, Executor executor) {
        return new UserRepository(webservice, userDao, executor);
    }

    // --- NETWORK INJECTION ---

    private static String BASE_URL = "https://api.github.com/";
    private static String BASE_URL_POST_LIST = "https://jsonplaceholder.typicode.com/";


    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL_POST_LIST)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    UserWebservice provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(UserWebservice.class);
    }


    @Provides
    @Singleton
    WebAPIInterface provideApiWebserviceForWebList(Retrofit restAdapter) {
        return restAdapter.create(WebAPIInterface.class);
    }
}
