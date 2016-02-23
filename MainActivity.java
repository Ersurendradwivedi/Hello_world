package sixth.sense.mylocation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.LikeView;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
// Developed by s k dwivedi

public class MainActivity extends Activity {

    //  private TextView info;
    String SENDER_ID = "309023424173", str;
    private TextView register;
    private LoginButton loginButton;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

         getActionBar().hide();

        setContentView(R.layout.login);

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        // info = (TextView) findViewById(R.id.info);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, RegisterActrivity.class);
                startActivity(in);
            }
        });
        LoginManager.getInstance().logOut();

//        LikeView likeView = (LikeView) findViewById(R.id.likeView);
//        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
//        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
//        likeView.setObjectIdAndType(
//                "http://inthecheesefactory.com/blog/understand-android-activity-launchmode/en",
//                LikeView.ObjectType.OPEN_GRAPH);

        //loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends", "user_birthday", "basic_info"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/taggable_friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Toast.makeText(getApplicationContext(),Profile.getCurrentProfile().getName()+" success login",Toast.LENGTH_LONG).show();
                                Intent in = new Intent(MainActivity.this, RegisterActrivity.class);
                                startActivity(in);
/*
                                /*//* handle the result *//**//**//**//**//**//**//**//**//**//**//**//**//**//**//**//*
                                JSONObject obj = response.getJSONObject();
                                JSONArray arr;

//                               info.setText( "\n" + Profile.getCurrentProfile().getId()
//                                         + "\n" + Profile.getCurrentProfile().getName()
//                                        );


//   BELOW CODE IS IMPORTANT
                                String data = Profile.getCurrentProfile().getName() + ":" + Profile.getCurrentProfile().getId();
                                Log.e("data", data);
                                File file = new File(Allpath.loginpath);
                                try {
                                    FileOutputStream fs = new FileOutputStream(file);
                                    fs.write(data.getBytes());
                                    fs.close();

                                    //  generateUrl();

                                } catch (FileNotFoundException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                File file1 = new File(Allpath.profilepath);
                                try {

                                    Log.e("over url", "");
                                    FileOutputStream fs = new FileOutputStream(file1);
                                    fs.write((Profile.getCurrentProfile().getProfilePictureUri(400, 400) + "").getBytes());
                                    fs.close();
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                                Log.e("profile pic", Profile.getCurrentProfile().getProfilePictureUri(400, 800) + "");
                                Log.e("contents pic", Profile.getCurrentProfile().describeContents() + "");


                                SharedPreferences settings = getSharedPreferences("prefs", 0);
                                boolean firstRun = settings.getBoolean("firstRun", true);
                                Log.e("after shared", "dfds" + firstRun);
                                if (firstRun == true)
                                    try {
                                        GCMRegistrar.checkDevice(getApplicationContext());
                                        GCMRegistrar.checkManifest(getApplicationContext());
                                        Log.e("before register ", "registration success");
                                        GCMRegistrar.register(getApplicationContext(), SENDER_ID);
                                        Log.e("after register ", "registration success");
                                        str = GCMRegistrar.getRegistrationId(getApplicationContext());
                                        Log.e("after getregisteri ", "registration success");

                                    } catch (Exception e) {
                                        Log.e("error in catch  ", e.toString());
                                    }
                                */

                            }
                        }
                ).executeAsync();

            }

            @Override
            public void onCancel() {
//                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.e("exception", e.toString());
//                info.setText("Login attempt failed.");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


}
