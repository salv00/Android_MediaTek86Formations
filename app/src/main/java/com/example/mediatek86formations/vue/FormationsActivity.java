package com.example.mediatek86formations.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mediatek86formations.R;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe visuelle de l'activité gérant l'affichage des formations qui hérite de AppCompatActivity.
 */
public class FormationsActivity extends AppCompatActivity {

    /**
     * Propriété de l'instance du controleur.
     */
    private Controle controle;
    /**
     * Propriété graphique du bouton Filtrer.
     */
    private Button btnFiltrer;
    /**
     * Propriété graphique de la zone de texte du filtre.
     */
    private EditText txtFiltre;

    /**
     * Propriété contenant la liste des formations.
     */
    private ArrayList<Formation> lesFormations;

    /**
     * Methode générant l'activité de formations.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);
        init();
    }

    /**
     * Methode qui initialise les objets graphiques et récupére l'instance du controleur.
     */
    private void init() {
        controle = Controle.getInstance();
        btnFiltrer = findViewById(R.id.btnFiltrer);
        txtFiltre = findViewById(R.id.txtFiltre);

        lesFormations = controle.getLesFormations();
        creerListe(lesFormations);
        ecouteFiltre();
    }

    /**
     * Méthode de création de la liste adapter.
     *
     * @param lesFormations List<Formation>
     */
        private void creerListe(ArrayList<Formation> lesFormations){
            if (lesFormations != null) {
                Collections.sort(lesFormations, Collections.<Formation>reverseOrder());
                ListView listView = (ListView)findViewById(R.id.lstFormations);
                FormationListAdapter adapter = new FormationListAdapter(lesFormations,FormationsActivity.this);
                listView.setAdapter(adapter);
        }
    }

    /**
     * Methode événementielle sur le clic du button filtrer. Permet de filtrer les formations sur le titre.
     */
    private void ecouteFiltre(){
        txtFiltre = (EditText) findViewById(R.id.txtFiltre);
        btnFiltrer.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v){
                ArrayList<Formation> lstFormationFiltre = new ArrayList<Formation>(controle.getLesFormationFiltre(txtFiltre.getText().toString()));
                if (txtFiltre.getText().toString() != "") {
                creerListe(lstFormationFiltre);
                }
                else {
                    controle.setLesFormations(controle.getLesFormations());
                    creerListe(lesFormations);
                }
            }

        });
    }
}