package com.example.m8projecteuf1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LoggedUserActivity extends AppCompatActivity {

    Realm realm;
    public int[] profilePics = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9, R.drawable.pic10};

    ImageButton ibRock;
    ImageButton ibPaper;
    ImageButton ibScissors;

    FrameLayout fl;
    TextView tvCurrentMoney;
    EditText etBet;
    ImageView imageViewRps;
    User user;
    Random r = new Random();
    int[] rps = {R.drawable.rock, R.drawable.paper, R.drawable.scissors};
    int resultado = -1;
    int apuesta;

    Fragment frWin = new WinFragment();
    Fragment frLose = new LoseFragment();
    Fragment frDraw = new DrawFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_buttons);

        ibRock = findViewById(R.id.imageButtonRock);
        ibPaper = findViewById(R.id.imageButtonPaper);
        ibScissors = findViewById(R.id.imageButtonScissors);

        tvCurrentMoney = findViewById(R.id.textViewCurrentMoney);
        etBet = findViewById(R.id.editTextBet);
        imageViewRps = findViewById(R.id.imageViewDado);
        fl = findViewById(R.id.frameLayout);

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).equalTo("name", getIntent().getStringExtra("user")).findFirst();

        ImageView iv = findViewById(R.id.imageViewFragment);
        TextView tv = findViewById(R.id.textViewUserName);
        iv.setImageResource(profilePics[user.getProfilePic()]);
        tv.setText(user.getName());
        tvCurrentMoney.setText("" + user.getMoney());

    }

    public void onBetClick(View view) {

        if (Integer.parseInt(tvCurrentMoney.getText().toString()) >= Integer.parseInt(etBet.getText().toString())) {

            getApuesta(view);
            setButtonsVisibilityGone();

            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

            shake.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    resultado = r.nextInt(3);
                    Log.i("zzz", "resultadoEnAnimacion = " + resultado);

                    imageViewRps.setImageResource(rps[resultado]);

                    Animation scale = AnimationUtils.loadAnimation(LoggedUserActivity.this, R.anim.scale);
                    imageViewRps.startAnimation(scale);

                    checkWinner();
                    setButtonsVisibilityVisible();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            imageViewRps.startAnimation(shake);


        } else {
            Toast.makeText(this, "You don't have enough.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getApuesta(View view) {
        if (ibRock.getId() == view.getId()){
            apuesta = 0;
        }
        else if (ibPaper.getId() == view.getId()) {
            apuesta = 1;
        }
        else if (ibScissors.getId() == view.getId()) {
            apuesta = 2;
        }

    }

    private void setButtonsVisibilityGone() {
        ibRock.setVisibility(View.GONE);
        ibPaper.setVisibility(View.GONE);
        ibScissors.setVisibility(View.GONE);
        fl.setVisibility(View.GONE);
    }

    private void setButtonsVisibilityVisible() {
        ibRock.setVisibility(View.VISIBLE);
        ibPaper.setVisibility(View.VISIBLE);
        ibScissors.setVisibility(View.VISIBLE);
        fl.setVisibility(View.VISIBLE);
    }

    private void checkWinner() {
        switch (apuesta) {
            case 0:
                switch (resultado){
                    case 2:
                        Wins();
                        break;
                    case 1:
                        Loses();
                        break;
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(fl.getId(), frDraw).commit();
                        break;
                }
                break;

            case 1:
                switch (resultado) {
                    case 0:
                        Wins();
                        break;
                    case 2:
                        Loses();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(fl.getId(), frDraw).commit();
                        break;
                }
                break;

            case 2:
                switch (resultado) {
                    case 1:
                        Wins();
                        break;
                    case 0:
                        Loses();
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(fl.getId(), frDraw).commit();
                        break;
                }
                break;
        }
    }

    private void Loses() {
        int aux;
        aux = Integer.parseInt(tvCurrentMoney.getText().toString()) - Integer.parseInt(etBet.getText().toString());
        tvCurrentMoney.setText("" + aux);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frLose).commit();
        if (aux <= 0) {
            realm.beginTransaction();
            user.setMoney(aux);
            realm.commitTransaction();
            startActivity(new Intent(this,LoseActivity.class));
        }

        realm.beginTransaction();
        user.setMoney(aux);
        realm.commitTransaction();
    }

    private void Wins() {
        int aux;
        aux = Integer.parseInt(tvCurrentMoney.getText().toString()) + Integer.parseInt(etBet.getText().toString());
        tvCurrentMoney.setText("" + aux);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frWin).commit();

        realm.beginTransaction();
        user.setMoney(aux);
        realm.commitTransaction();
    }

    public void onAllinClick(View view) {

        CheckBox cb = findViewById(view.getId());
        if (cb.isChecked()) {
            etBet.setText(tvCurrentMoney.getText().toString());
        } else {
            etBet.setText("0");
        }
    }
}
