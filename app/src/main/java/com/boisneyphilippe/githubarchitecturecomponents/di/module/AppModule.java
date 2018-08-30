package com.boisneyphilippe.githubarchitecturecomponents.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.boisneyphilippe.githubarchitecturecomponents.api.GithubServiceAPI;
import com.boisneyphilippe.githubarchitecturecomponents.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.api.WebAPIInterface;
import com.boisneyphilippe.githubarchitecturecomponents.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.PostDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.executors.AppExecutors;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.GithubServiceRespository;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.PostRepository;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.UserRepository;
import com.boisneyphilippe.githubarchitecturecomponents.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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

    // --- REPOSITORY INJECTION ---

    @Provides
    @Singleton
    UserDao provideUserDao(MyDatabase database) {
        return database.userDao();
    }

    @Provides
    @Singleton
    PostDao provideUPostDao(MyDatabase database) {
        return database.postDao();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // --- NETWORK INJECTION ---

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserWebservice webservice, UserDao userDao, Executor executor, ExampleIntercepter exampleIntercepter) {
        return new UserRepository(webservice, userDao, executor,exampleIntercepter);
    }


    @Provides
    @Singleton
    GithubServiceRespository provideGithubRepository(GithubServiceAPI webservice, AppExecutors executor, ExampleIntercepter exampleIntercepter) {
        return new GithubServiceRespository(webservice, executor,exampleIntercepter);
    }

    @Provides
    @Singleton
    PostRepository providePostListRepository(WebAPIInterface webservice, PostDao postDao, AppExecutors executor, ExampleIntercepter exampleIntercepter) {
        return new PostRepository(webservice, postDao, executor, exampleIntercepter);
    }

    @Provides
    @Singleton
    ExampleIntercepter provideInterceptor() {
        return ExampleIntercepter.getIntercepter().get();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ExampleIntercepter interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .baseUrl("http://localhost/")
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

    @Provides
    @Singleton
    GithubServiceAPI provideApiWebserviceForGithubService(Retrofit restAdapter) {
        return restAdapter.create(GithubServiceAPI.class);
    }

}
