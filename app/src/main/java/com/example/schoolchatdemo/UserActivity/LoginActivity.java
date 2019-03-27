package com.example.schoolchatdemo.UserActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.schoolchatdemo.BaseActivity;
import com.example.schoolchatdemo.Frame.MainActivity;
import com.example.schoolchatdemo.R;
import com.example.schoolchatdemo.UserPackage.User;
import com.example.schoolchatdemo.UserPackage.UserCallBackListener;
import com.example.schoolchatdemo.UserPackage.UserModel;
import com.example.schoolchatdemo.WebSocket.MyWebSocketListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private List<String> needPermission;
    private final int REQUEST_CODE_PERMISSION = 0;
    private String[] permissionArray = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
    };


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @OnClick({R.id.login,R.id.register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:
                final String un=username.getText().toString();
                final String psd=password.getText().toString();
                if(TextUtils.isEmpty(un)||TextUtils.isEmpty(psd)){
                    toast("请输入用户名与密码");
                }else {
                    UserModel.getInstance().loginUser(LoginActivity.this, un, psd, new UserCallBackListener() {
                        @Override
                        public void onFinish(String jsonString) {
                            Log.d("msg",jsonString);
                            try {
                                JSONObject jsonObject= new JSONObject(jsonString);
                                JSONObject data = new JSONObject(jsonObject.getString("data"));
                                String result = (String) data.getString("login");
                                Log.d("msg","login:"+result);
                                if(result.equals("success")){
                                    toast("登录成功");
                                    SharedPreferences sharedPreferences=getSharedPreferences("currentUser",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("username",un);
                                    editor.putString("password",psd);
                                    editor.commit();
                                    startActivity(MainActivity.class,true);
                                }else {
                                    toast("登录失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            UserModel.getInstance().connect(LoginActivity.this,new MyWebSocketListener());
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
                break;
            case R.id.register:
                startActivity(RegisterActivity.class,false);
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login2;
    }

    @Override
    public void initData() {
        askMultiplePermission();
        User user=UserModel.getInstance().getCurrentUser(LoginActivity.this);
        if(!TextUtils.isEmpty(user.getU_name())&&!TextUtils.isEmpty(user.getU_password())){
            UserModel.getInstance().loginUser(LoginActivity.this, user.getU_name(), user.getU_password(), new UserCallBackListener() {
                @Override
                public void onFinish(String jsonString) {
                    try {
                        JSONObject jsonObject= new JSONObject(jsonString);
                        JSONObject data = new JSONObject(jsonObject.getString("data"));
                        String result = (String) data.getString("login");
                        Log.d("msg","login:"+result);
                        if(result.equals("success")){
                            toast("登录成功");
                            startActivity(MainActivity.class,true);
                        }else {
                            toast("登录失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    @Override
    public void initView() {

    }

    public void askMultiplePermission() {
        needPermission = new ArrayList<>();
        for (String permissionName :
                permissionArray) {
            if (!checkIsAskPermission(this, permissionName)) {
                needPermission.add(permissionName);
            }
        }

        if (needPermission.size() > 0) {
            //开始申请权限
            ActivityCompat.requestPermissions(this, needPermission.toArray(new String[needPermission.size()]), REQUEST_CODE_PERMISSION);
        } else {
            //获取数据

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                Map<String, Integer> permissionMap = new HashMap<>();
                for (String name :
                        needPermission) {
                    permissionMap.put(name, PackageManager.PERMISSION_GRANTED);
                }

                for (int i = 0; i < permissions.length; i++) {
                    permissionMap.put(permissions[i], grantResults[i]);
                }
                if (checkIsAskPermissionState(permissionMap, permissions)) {
                    //获取数据

                } else {
                    //提示权限获取不完成，可能有的功能不能使用
                }

                break;
        }
    }

    public boolean checkIsAskPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkIsAskPermissionState(Map<String, Integer> maps, String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (maps.get(list[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }
}
