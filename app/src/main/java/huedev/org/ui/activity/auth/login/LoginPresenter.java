package huedev.org.ui.activity.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import huedev.org.data.model.User;
import huedev.org.data.repository.LoginRepository;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.UserDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.data.source.remote.response.auth.LoginResponse;
import huedev.org.data.source.remote.response.user.CreateUserReponse;
import huedev.org.data.source.remote.response.user.ListUserResponse;
import huedev.org.data.source.remote.response.user.UpdateUserReponse;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.rx.BaseSchedulerProvider;
import io.reactivex.Single;

public class LoginPresenter implements LoginContract.Presenter{
    private UserLocalDataSource localDataSource;
    private UserRemoteDataSource remoteDataSource;
    private Context mContext;
    private LoginContract.View mView;
    private LoginRepository mLoginRepository;
    public UserRepository userRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    public LoginPresenter(Context context, LoginRepository loginRepository, BaseSchedulerProvider schedulerProvider){
        mContext = Preconditions.checkNotNull(context);
        mLoginRepository = Preconditions.checkNotNull(loginRepository);
        mSchedulerProvider = Preconditions.checkNotNull(schedulerProvider);
    }

    @Override
    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            mView.logicFaild();
        }else {
            mLoginRepository.login(username, password)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(loginResponse -> handleLoginSuccess(loginResponse, username, password),
                            error -> handleLoginFailed(error));
        }
    }

    @Override
    public void getAllUser() {
        userRepository = UserRepository.getInstance(localDataSource,remoteDataSource);
        userRepository.users()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(listUserResponse -> handlerSuccessGetAllUser(listUserResponse),
                        erro -> handlerFailGetAllUser(erro));
    }

    private void handlerFailGetAllUser(Throwable erro) {
        Log.d(AppConstants.KEY_DEBUG,"GetUserListFail");
    }

    private void handlerSuccessGetAllUser(ListUserResponse listUserResponse) {
        List<User> listUser = listUserResponse.userList;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(AppConstants.ID_USERlIST, (ArrayList<? extends Parcelable>) listUser);
        Log.d(AppConstants.KEY_DEBUG,"GetUserListSuccess");
    }

    private void handleLoginSuccess(LoginResponse loginResponse, String username, String password){
        getAllUser();
        AppPrefs.getInstance(mContext).putApiToken(loginResponse.data.getAccess_token());
        AppPrefs.getInstance(mContext).putIdUser(loginResponse.data.getId());
        AppPrefs.getInstance(mContext).putUserNameUser(username);
        AppPrefs.getInstance(mContext).putPasswordUser(password);
        AppPrefs.getInstance(mContext).putNameUser(loginResponse.data.getName());
        AppPrefs.getInstance(mContext).putEmailUser(loginResponse.data.getEmail());
        AppPrefs.getInstance(mContext).putRole(Integer.parseInt(loginResponse.data.getRole()));
        mView.getUser(loginResponse.data);
    }

    private void handleLoginFailed(Throwable error){
        Log.e("errr", error.toString());
    }
    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }
    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
