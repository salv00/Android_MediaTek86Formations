package com.example.mediatek86formations.vue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.mediatek86formations.R;
import com.example.mediatek86formations.controleur.Controle;
import com.example.mediatek86formations.modele.Formation;
import com.example.mediatek86formations.outils.MesOutils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FormationListAdapter extends BaseAdapter {

    private ArrayList<Formation> lesFormations;
    private ArrayList<Integer> lesFavoris ;
    private LayoutInflater inflater;
    private Controle controle;
    private Context context;

    /**
     *
     * @param lesFormations
     * @param context
     */
    public FormationListAdapter(ArrayList<Formation> lesFormations,ArrayList<Integer> lesFavoris, Context context) {
        this.lesFormations = lesFormations;
        this.lesFavoris = lesFavoris;
        this.controle = Controle.getInstance(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     *
     * @return nombre de formations
     */
    @Override
    public int getCount() {
        return lesFormations.size();
    }

    /**
     *
     * @param i position de l'item
     * @return valeur à cette position
     */
    @Override
    public Object getItem(int i) {
        return lesFormations.get(i);
    }

    /**
     *
     * @param i position de l'item
     * @return id à cette position
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Construction de la ligne
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewProperties viewProperties;
        if(view == null){
            viewProperties = new ViewProperties();
            view = inflater.inflate(R.layout.layout_liste_formations, null);
            viewProperties.txtListeTitle = (TextView)view.findViewById(R.id.txtListTitle);
            viewProperties.txtListPublishedAt = (TextView)view.findViewById(R.id.txtListPublishedAt);
            viewProperties.btnListFavori = (ImageButton)view.findViewById(R.id.btnListFavori);
            view.setTag(viewProperties) ;
        }else{
            viewProperties = (ViewProperties)view.getTag();
        }

        viewProperties.txtListeTitle.setText(lesFormations.get(i).getTitle());
        viewProperties.txtListPublishedAt.setText(lesFormations.get(i).getPublishedAtToString());
        viewProperties.txtListeTitle.setTag(i);
        viewProperties.txtListeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirUneFormationActivity(v);
            }
        });

        viewProperties.txtListPublishedAt.setTag(i);
        viewProperties.txtListPublishedAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirUneFormationActivity(v);
            }
        });

        viewProperties.txtListPublishedAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirUneFormationActivity(v);
            }
        });

        viewProperties.btnListFavori.setImageResource(selectFavBtnColor(i));
        viewProperties.btnListFavori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(lesFavoris.contains(lesFormations.get(i).getId())){
                    Log.d("onclik","***************************** si rouge::: "+ "id formation"+":::" +(int)getItemId(i+1)+ lesFavoris.toString());
                    viewProperties.btnListFavori.setImageResource(R.drawable.coeur_gris);
                    Log.d("onclik", "lesfavoris: " +lesFavoris.toString());
                    controle.removeFav(lesFormations.get(i));
                }

                else{
                    Log.d("onclik","***************************** si gris");
                    viewProperties.btnListFavori.setImageResource(R.drawable.coeur_rouge);
                    Log.d("onclik","***************************** "+ "id formation"+":::" +(int)getItemId(i+1)+ "i: "+ i+1 + lesFavoris.toString());
                    //controle.ajoutFavoris(i+1);
                    controle.ajoutFavoris(lesFormations.get(i));
                }
                lesFavoris = controle.getLesFavoris();
                notifyDataSetChanged();

            }
        });
        return view;
    }

    private int selectFavBtnColor(int id){
        if(lesFavoris.contains(lesFormations.get(id).getId())){
            Log.d("onclik", "ligne rouge " +lesFavoris.toString());
            return R.drawable.coeur_rouge;
        }
        else{
            Log.d("onclik", "ligne grise " +lesFavoris.toString());
            return R.drawable.coeur_gris;
        }
    }
    /**
     * Ouvre la page du détail de la formation
     * @param v
     */
    private void ouvrirUneFormationActivity(View v){
        int indice = (int)v.getTag();
        controle.setFormation(lesFormations.get(indice));
        Intent intent = new Intent(context, UneFormationActivity.class);
        context.startActivity(intent);
    }

    /**
     * Propriétés de la ligne
     */
    private class ViewProperties{
        ImageButton btnListFavori;
        TextView txtListPublishedAt;
        TextView txtListeTitle;
    }
}
