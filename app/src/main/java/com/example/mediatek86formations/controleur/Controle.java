package com.example.mediatek86formations.controleur;

import com.example.mediatek86formations.modele.AccesDistant;
import com.example.mediatek86formations.modele.Formation;

import java.util.ArrayList;

public class Controle {

    private static Controle instance = null ;
    private ArrayList<Formation> lesFormations = new ArrayList<>();
    private Formation formation = null;

    /**
     * constructeur privé
     */
    private Controle(){
        super();
    }

    /**
     * récupération de l'instance unique de Controle
     * @return instance
     */
    public static final Controle getInstance(){
        if(Controle.instance == null){
            Controle.instance = new Controle();
            AccesDistant accesDistant = new AccesDistant();
            accesDistant.envoi("tous", null);
        }
        return Controle.instance;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    /**
     /**
     * Methode qui retourne la liste des formations avec le filtre.
     *
     * @param filtre String
     * @return lstFiltre List<Formation>
     */
    public List<Formation> getLesFormationFiltre(String filtre) {
        List<Formation> lstFiltre = new ArrayList<>();
        if (lesFormationsChoix != null) {
            for (Formation uneFormation : lesFormationsChoix) {
                if (uneFormation.getTitle().toUpperCase().contains(filtre.toUpperCase())) {
                    lstFiltre.add(uneFormation);
                }
            }
        }
        return lstFiltre;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }



}


