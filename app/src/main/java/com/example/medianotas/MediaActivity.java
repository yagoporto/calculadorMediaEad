package com.example.medianotas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;


public class MediaActivity extends Activity {
    EditText edtAva1;
    EditText edtAva2;
    EditText edtA2;
    EditText edtA3;
    TextView resultA1;
    TextView resultMedia;
    TextView tvResultAprov;

    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        edtAva1 = (EditText) findViewById(R.id.etAVA1);
        edtAva2 = (EditText) findViewById(R.id.etAVA2);
        edtA2 = (EditText) findViewById(R.id.etA2);
        edtA3 = (EditText) findViewById(R.id.etA3);
        resultA1 = (TextView) findViewById(R.id.tvA1Resultado);
        resultMedia = (TextView) findViewById(R.id.tvResultado);
        tvResultAprov = (TextView) findViewById(R.id.tvSituacao);

        edtAva1.addTextChangedListener(textWatcher);
        edtAva2.addTextChangedListener(textWatcher);

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Não precisa de implementação aqui
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Não precisa de implementação aqui
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Calcular e exibir a média de A1
            double a1 = calcularMediaA1();
            resultA1.setText(String.format("%.1f", a1));
        }
    };


    public double calcularMediaA1(){
        double ava1 = 0;
        double ava2 = 0;
        try{
            if (!edtAva1.getText().toString().isEmpty()) {
                ava1 = Double.parseDouble(edtAva1.getText().toString());
                ava1 = formatarNota(ava1);
            }

            if (!edtAva2.getText().toString().isEmpty()) {
                ava2 = Double.parseDouble(edtAva2.getText().toString());
                ava2 = formatarNota(ava2);
            }
            return (ava1 + ava2) / 2;
        }catch (NumberFormatException e){
            return 0 ;
        }
    }

    @SuppressLint("DefaultLocale")
    public void calcular(View v){
        double a1 = calcularMediaA1();
        double a2 = 0;
        double a3 = 0;
        double nfp = 0;

        //pegando o valor da a2 do editText
        try{
            if(!edtA2.getText().toString().isEmpty()){
                a2 = Double.parseDouble(edtA2.getText().toString());
            }
        }catch (NumberFormatException e){
            a2 = 0;
        }

        //pegando o valor da a3 do editText
        try {
            if(!edtA3.getText().toString().isEmpty()){
                a3 = Double.parseDouble(edtA3.getText().toString());
            }
        }catch (NumberFormatException e){
            a3 = 0;
        }

        //formatando o valor da nota
        a2 = formatarNota(a2);
        a3 = formatarNota(a3);

        //calculo da media geral (se A3 for maior que a A2, A3 substui a nota A2)
        nfp = calculoMediaGeral(a1, a2, a3);

        //exibir o resultado da media
        resultMedia.setText(String.format("%.1f", nfp));

        tvResultAprov.setText(String.format(verificarAprovado(nfp)));

        //fechar o teclado ao aperta o botao
        fecharTeclado();
    }

    private double calculoMediaGeral(double a1, double a2, double a3){
        double nfp = 0;

        if(a1 == 0){
            if( a3 > a2){
                nfp = ((a1 * 0.4) + (a3 * 0.6)) /2;
            } else if (a3 < a2) {
                nfp = ((a1 * 0.4) + (a2 * 0.6)) /2;
            }
        }else{
            if(a3 > a2){
                nfp = (a1 * 0.4) + (a3 * 0.6);
            }else{
                nfp = (a1 * 0.4) + (a2 * 0.6);
            }
        }

        return  nfp;
    }
    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int formatarNota(double nota){
        int result = 0;
        if(nota > 10){
            result = (int) nota / 10;
        }else{
            result = (int) nota;
        }
        return result;
    }

    public String verificarAprovado(double media){
        String result = "";
        double a1 =  calcularMediaA1();
        if(media < 6 || a1 == 0){
            result = "REPROVADO";
        }else{
            result = "APROVADO";
        }

        return result;
    }
}
