package com.example.mediatek86formations.controleur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.mediatek86formations.modele.AccesDistant;
import com.example.mediatek86formations.modele.AccesLocal;
import com.example.mediatek86formations.modele.Formation;
import com.example.mediatek86formations.vue.FavorisActivity;
import com.example.mediatek86formations.vue.FormationsActivity;

import java.util.ArrayList;
import java.util.Date;

public class Controle {

    private static Controle instance = null ;
    private static ArrayList<Formation> lesFormations = new ArrayList<>();

    public static void setLesFormationsFavorites(ArrayList<Formation> lesFormationsFavorites) {
        Controle.lesFormationsFavorites = lesFormationsFavorites;
    }

    private static ArrayList<Formation> lesFormationsFavorites = new ArrayList<>();
    private static ArrayList<Integer> lesFavoris = new ArrayList<>();

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    private Formation formation ;
    private static AccesLocal accesLocal;
    private static  AccesDistant accesDistant;

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
    public static final Controle getInstance(Context context){
         if(Controle.instance == null) {
            Controle.instance = new Controle();

             Handler handler = new Handler();
             handler.postDelayed(new Runnable() {
                 public void run() {
                     accesDistant = new AccesDistant();
                     accesDistant.envoi("tous", null);
                 }
             }, 900);   //900 milliseconds

            accesLocal = new AccesLocal(context);
            lesFavoris = accesLocal.recup();

        }

        return Controle.instance;
    }

    public ArrayList<Formation> getLesFormationsFavorites(){
        for(Formation uneFormation : lesFormations){
            if(lesFavoris.contains(uneFormation.getId() )&& !lesFormationsFavorites.contains(uneFormation)){
                lesFormationsFavorites.add(uneFormation);
            }
        }
        return  lesFormationsFavorites;

    }

    public static ArrayList<Integer> getLesFavoris() {
        return lesFavoris;
    }


    public  void ajoutFavoris(Formation favFormation){

        //Formation uneFormation = getLesFormations().get(favLigne-1);
        Log.d("ajoutFavorisscontrolle", "Ligne :"+favFormation+"  titre :" + favFormation.getTitle() + " :: id:" + favFormation.getId());

        if (!lesFavoris.contains(favFormation.getId())) {
            lesFavoris.add(favFormation.getId());
            accesLocal.ajoutfavoris(favFormation.getId());
            lesFormationsFavorites.add(favFormation);
            Log.d("ajoutFavorisscontrolle"," :AJOUtEEEEEEEEEEEEEEEEEEEEEER: " + favFormation.getTitle()) ;
        }

    }


    public void removeFav(Formation notfavFormation){
        //Formation uneFormation = getLesFormations().get(notfavLigne-1);
        Log.d("Supprscontrolle", "titre : " + notfavFormation.getTitle() + " 1!!" + notfavFormation.getId());
        accesLocal.removefavoris(notfavFormation.getId());
        Log.d("Supprscontrolle", "titre : " + notfavFormation.getTitle() + " 2!!" + lesFavoris.toString());
        lesFavoris.remove(lesFavoris.indexOf(notfavFormation.getId()));
        lesFormationsFavorites.remove(notfavFormation);
        Log.d("Supprscontrolle", "titre : " + notfavFormation.getTitle() + " 3!!" + lesFavoris.toString());
    }

    public void setLesFavoris(ArrayList<Integer> lesFavoris) { this.lesFavoris = lesFavoris;}



    /**
     * retourne les formations dont le titre contient le filtre
     * @param filtre
     * @return
     */
    public ArrayList<Formation> getLesFormationFiltre(String filtre, Context context){
        ArrayList<Formation> lstFormations = null;
        if(context instanceof FavorisActivity){
            Log.d("FormationFiltre", "titre : " + " 3!!" + lesFavoris.toString());

            lstFormations = lesFormationsFavorites;
        }
        else if(context instanceof FormationsActivity){
            Log.d("gFormationFiltre", "titre : " +  " 3!!" + lesFavoris.toString());

            lstFormations = lesFormations;
        }
        ArrayList<Formation> lstFiltre = new ArrayList<>();
        for(Formation uneFormation : lstFormations){
            if(uneFormation.getTitle().toUpperCase().contains(filtre.toUpperCase())){
                lstFiltre.add(uneFormation);
            }
        }
        return lstFiltre;
    }

    public Formation getFormation() {
        return formation;
    }



    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) { this.lesFormations = lesFormations;}

}
