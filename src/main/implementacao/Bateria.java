package main.implementacao;

import java.util.ArrayList;

public class Bateria extends Instrumento{

    /*private HashMap<Integer, Nota> notas;
    private ArrayList<Integer> teclasFrameAnterior;
    private boolean estaTocando;*/
    
    public Bateria() {
        super("bateria");
    }



    @Override
    public boolean tocar (ArrayList<Tecla> teclas){
        
        boolean estaTocando = false;
        for (Tecla tecla : teclas){

            boolean teclaFoiPressionadaNesteFrame=notas.containsKey(tecla) && !teclasFrameAnterior.contains(tecla);

            if (teclaFoiPressionadaNesteFrame){
                notas.get(tecla).tocarNota();
                estaTocando = true;
            }
        }

        teclasFrameAnterior = teclas;
        return estaTocando;
    }

    public Bateria BateriaCopy() {
        this.notas = loadNotas("bateria");
        this.teclasFrameAnterior = new ArrayList<Tecla>();
        return this;

    }

}
