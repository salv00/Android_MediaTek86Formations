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
    private List<Formation> lesFormationsChoix;
    /**
     * Propriété contenant la liste des id des formations favorites.
     */
    private List<Integer> lesFavoris;

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
        btnFiltrer = findViewById(R.id.btnFiltrer);
        txtFiltre = findViewById(R.id.txtFiltre);

        controle = Controle.getInstance(this);

        lesFormationsChoix = controle.getLesFormationsChoix();
        lesFavoris = Controle.getLesFavoris();
        creerListe(lesFormationsChoix);
        ecouteFiltre();
    }

    /**
     * Méthode de création de la liste adapter.
     *
     * @param lesFormations List<Formation>
     */
    private void creerListe(final List<Formation> lesFormations) {
        if (lesFormations != null) {
            Collections.sort(lesFormations, Collections.reverseOrder());
            ListView listView = findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(lesFormations, lesFavoris, FormationsActivity.this);
            listView.setAdapter(adapter);
        }
    }

    /**
     * Methode événementielle sur le clic du button filtrer. Permet de filtrer les formations sur le titre.
     */
    private void ecouteFiltre() {
        txtFiltre = findViewById(R.id.txtFiltre);
        btnFiltrer.setOnClickListener(v -> {
            if (!txtFiltre.getText().toString().equals("")) {
                List<Formation> lstFormationFiltre = new ArrayList<>(controle.getLesFormationFiltre(txtFiltre.getText().toString()));
                creerListe(lstFormationFiltre);
            } else {
                lesFormationsChoix = controle.getLesFormationsChoix();
                creerListe(lesFormationsChoix);
            }
        });
    }
}