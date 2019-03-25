package com.example.morpion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Morpion extends AppCompatActivity {

    public boolean joueurActif;
    public GifImageView gifArtifice;
    public GifImageView gifExplosion;
    public ArrayList<ArrayList<Button> > plateau;
    public TextView labelVictoire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        gifArtifice = (GifImageView) findViewById(R.id.artifice);
        gifArtifice.setVisibility(View.GONE);
        gifExplosion = (GifImageView) findViewById(R.id.explosion);
        gifExplosion.setVisibility(View.GONE);
        labelVictoire = findViewById(R.id.textVictoire);
        labelVictoire.setText("");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        tirerJoueur();
        plateau = new ArrayList<>();

        // Create n lists one by one and append to the
        // master list (ArrayList of ArrayList)
        ArrayList<Button> ligne1 = new ArrayList<Button>();
        ligne1.add((Button) findViewById(R.id.a1));
        ligne1.add((Button) findViewById(R.id.a2));
        ligne1.add((Button) findViewById(R.id.a3));
        plateau.add(ligne1);

        ArrayList<Button> ligne2 = new ArrayList<Button>();
        ligne2.add((Button) findViewById(R.id.b1));
        ligne2.add((Button) findViewById(R.id.b2));
        ligne2.add((Button) findViewById(R.id.b3));

        plateau.add(ligne2);

        ArrayList<Button> ligne3 = new ArrayList<Button>();
        ligne3.add((Button) findViewById(R.id.c1));
        ligne3.add((Button) findViewById(R.id.c2));
        ligne3.add((Button) findViewById(R.id.c3));
        plateau.add(ligne3);

        boolean swap = false;
        for (ArrayList<Button> liste: plateau) {
            for (Button btn: liste) {
                btn.setText("");
                btn.setTextSize(80.0f);
            }
        }
    }

    public void onClick(View v){
        Button btn = (Button) v;
        if (!(btn.getText().equals("X") || btn.getText().equals("O"))){
            if (joueurActif){
                btn.setText("X");
                joueurActif = false;
                verifierVictoire("X");
            }else{
                btn.setText("O");
                joueurActif = true;
                verifierVictoire("O");
            }
        }
    }

    public void verifierVictoire(String c){
        // Lignes
        for (int i = 0; i < 3; i++){
            if (plateau.get(i).get(0).getText().equals(c) && plateau.get(i).get(1).getText().equals(c) && plateau.get(i).get(2).getText().equals(c)){
                onWin("Victoire",c);
                return;
            }
        }
        //Colonnes
        for(int i = 0; i < 3; i++){
            if (plateau.get(0).get(i).getText().equals(c) && plateau.get(1).get(i).getText().equals(c) && plateau.get(2).get(i).getText().equals(c)){
                onWin("Victoire",c);
                return;
            }
        }
        //Diagonales
        if (plateau.get(0).get(0).getText().equals(c) && plateau.get(1).get(1).getText().equals(c) && plateau.get(2).get(2).getText().equals(c)){
            onWin("Victoire",c);
            return;
        }
        if (plateau.get(0).get(2).getText().equals(c) && plateau.get(1).get(1).getText().equals(c) && plateau.get(2).get(0).getText().equals(c)){
            onWin("Victoire",c);
            return;
        }
        if (isPlateauComplet()){
            onWin("Egalité",c);
            return;
        }
    }
    public void onWin(String message, String caractère){
        for (ArrayList<Button> liste: plateau) {
            for (Button btn: liste) {
                btn.setEnabled(false);
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                if(message.equals("Victoire")){
                    gifArtifice.setVisibility(View.VISIBLE);
                    labelVictoire.setText("Victoire des "+caractère);
                }else{
                    labelVictoire.setText("Egalité");
                    gifExplosion.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public void onRejouer(View v){
        for (ArrayList<Button> liste: plateau) {
            for (Button btn: liste) {
                btn.setEnabled(true);
                btn.setText("");
            }
        }
        labelVictoire.setText("");
        gifArtifice.setVisibility(View.GONE);
        gifExplosion.setVisibility(View.GONE);

        tirerJoueur();
    }
    public void tirerJoueur(){
        if(Math.random() > 0.5){
            this.joueurActif = false;
        }else{
            this.joueurActif = true;
        }

    }
    public boolean isPlateauComplet(){
        for (ArrayList<Button> liste: plateau) {
            for (Button btn: liste) {
                if (btn.getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }

}
