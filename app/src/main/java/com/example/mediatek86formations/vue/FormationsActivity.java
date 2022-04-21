package com.example.mediatek86formations.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import com.example.mediatek86formations.R;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;

public class FormationsActivity extends AppCompatActivity {

    private Controle controle;
    private Button btnFiltrer;
    private EditText txtFiltre;
    private ArrayList<Formation> lesFormations;
    private ArrayList<Integer> lesFavoris ;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * initialisations
     */
    private void init(){
        btnFiltrer = (Button) findViewById(R.id.btnFiltrer);
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);

        controle = Controle.getInstance(this);

        lesFormations = controle.getLesFormations();
        lesFavoris = controle.getLesFavoris();
        creerListe(lesFormations);
        ecouteFavoris();
        ecouteFiltre();
    }

    /**
     * création de la liste adapter
     */
    private void creerListe(ArrayList<Formation> lesFormations){
        if(lesFormations != null){
            Log.d("creerliste", "FORMATION===========================" );
            Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
            ListView listView = (ListView)findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations, lesFavoris,FormationsActivity.this);
            listView.setAdapter(adapter);
        }
    }

    /**
     * Methode événementielle sur le clic du button filtrer. Permet de filtrer les formations sur le titre
     */
    private void ecouteFiltre(){
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);
        btnFiltrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ArrayList<Formation> lstFormationFiltre = new ArrayList<Formation>(controle.getLesFormationFiltre(txtFiltre.getText().toString(), FormationsActivity.this));
                if(txtFiltre.getText().toString() != ""){
                    creerListe(lstFormationFiltre);
                }
                else {
                    controle.setLesFormations(controle.getLesFormations());
                    creerListe(lesFormations);
                }
            }
        });
    }

    private void ecouteFavoris(){

    }
}