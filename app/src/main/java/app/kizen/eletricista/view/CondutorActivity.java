package app.kizen.eletricista.view;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.text.MessageFormat;
import java.util.ArrayList;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

/**
 * Activity para dimensionamento de condutor conforme NBR 5410.
 * O usuário informa tipo de instalação (A1/A2/B1/B2), número de condutores
 * carregados, temperatura ambiente e material isolante (PVC/EPR) para
 * obter a seção recomendada e a capacidade de condução corrigida.
 */
// TODO AUTO PREENCHER USANDO SUGESTÃO
public class CondutorActivity extends AppCompatActivity {
    // Seletores do tipo de instalação (A1/A2/B1/B2) e opção de sugestão
    public SwitchCompat Swa1, A2, B1, B2, swSugetao; //C;
    EditText editNumeroCondutosCarregados, editResultadoSecaoCondutor, editCorrente, editCapacidadeCorrente, editTemperatura, editresultadoDisjuntor;
    TextView txtTipopoDeLinha;
    String tipoDeLinhaEletrica;
    Button btnCalcular;
    Integer contador = 2; // valor inicial do número de condutores carregados
    int material=0; // 0 = PVC, 1 = EPR
    int numeroDECondutoresCarregados;
    // AlertDialog
    AlertDialog.Builder builder;
    AlertDialog alert;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condutor);
        // instancia os objetos
        initFormulary();
    }


    // Direciona para a tabela correta de acordo com instalação e nº de condutores
    public void CalcularGeral(View view) {
        editResultadoSecaoCondutor.setError(null);
        if (validarDados()) if (tipoDeLinhaEletrica.equals("A1")) {// verifica o tipo de linha
            if (numeroDECondutoresCarregados == 2) {//Direciona para a tabela 2°
                if (Double.parseDouble(editCorrente.getText().toString()) <= 767.0)
                    calculaSecao("A1_2");
            } else if (numeroDECondutoresCarregados == 3) {//Direciona para a tabela 3°
                if (Double.parseDouble(editCorrente.getText().toString()) <= 679.0)
                    calculaSecao("A1_3");
                else {
                    correnteExcedeu();
                }
            }
        } else if (tipoDeLinhaEletrica.equals("A2")) { // verifica o tipo de linha
            if (numeroDECondutoresCarregados == 2) {//Direciona para a tabela °4
                if (Double.parseDouble(editCorrente.getText().toString()) <= 698.0)
                    calculaSecao("A2_2");
                else {
                    correnteExcedeu();
                }

            } else if (numeroDECondutoresCarregados == 3) {//Direciona para a tabela 3°
                if (Double.parseDouble(editCorrente.getText().toString()) <= 618.0)
                    calculaSecao("A2_3");
                else {
                    correnteExcedeu();
                }
            }
        } else if (tipoDeLinhaEletrica.equals("B1")) { // verifica o tipo de linha
            if (numeroDECondutoresCarregados == 2) {//Direciona para a tabela °4
                if (Double.parseDouble(editCorrente.getText().toString()) <= 1012.)
                    calculaSecao("B1_2");
                else {
                    correnteExcedeu();
                }

            } else if (numeroDECondutoresCarregados == 3) {//Direciona para a tabela 3°
                if (Double.parseDouble(editCorrente.getText().toString()) <= 906.)
                    calculaSecao("B1_3");
                else {
                    correnteExcedeu();
                }
            }
        } else if (tipoDeLinhaEletrica.equals("B2")) { // verifica o tipo de linha
            if (numeroDECondutoresCarregados == 2) {//Direciona para a tabela °4
                if (Double.parseDouble(editCorrente.getText().toString()) <= 827.)
                    calculaSecao("B2_2");
                else {
                    correnteExcedeu();
                }
            } else if (numeroDECondutoresCarregados == 3) {//Direciona para a tabela 3°
                if (Double.parseDouble(editCorrente.getText().toString()) <= 738.)
                    if (Integer.parseInt(editCorrente.getText().toString()) <= 15)
                        calculaSecao("B2_3");
                    else {
                        correnteExcedeu();
                    }
            }
        }
    }

    // Calcula seção e capacidade corrigida (aplica FCA e temperatura)
    public void calculaSecao(String tabela) {
        double capacidadeDeCorrente= 0.0, sessaoCondutor = 0.0, correnteProjeto, Fca, correnteInformada=0 ;//Fca fator de agrupamento
        ArrayList<Double> lista;
        //String material = "PVC";
        if (Double.parseDouble(editNumeroCondutosCarregados.getText().toString()) > 0 && Double.parseDouble(editNumeroCondutosCarregados.getText().toString()) <= 5) {
            Fca = AppUtil.tabelaFca(editNumeroCondutosCarregados.getText().toString());
        } else
            Fca = 2;
        correnteProjeto = Double.parseDouble(editCorrente.getText().toString()); // todo analizar

        do {
            try {
                // Verifica o tipo de isolamento
                //material
                // Passa a tabela e a corrente pra função que devolve a seção inicial De acordo com a tabela 36 NBR_5410_2005
                lista = AppUtil.VerificaSecaoEcorrente(correnteProjeto, tabela, material);
                sessaoCondutor = lista.get(0);//pega a seção do condutor que é o item 0 da lista
                capacidadeDeCorrente = lista.get(1);//pega a capacidade máxima de condução do condutor encontrado
                // corrige o capacidadeDeCorrente aplicando fator de agrupamentoe temperatura na capacidade máxima de condução
                capacidadeDeCorrente = capacidadeDeCorrente * Fca * CorrecaoFTC(editTemperatura.getText().toString());
                editResultadoSecaoCondutor.setText(null);
                editCapacidadeCorrente.setText(null);

                if (sessaoCondutor < 1.5) {
                    editResultadoSecaoCondutor.setText(MessageFormat.format("{0} mm\nPara sinalização e controle ", AppUtil.FormatarSaida(sessaoCondutor))); // TODO Analizar essa mudança para aprimorar a progrmação
                } else // exibe resultado final
                {
                    //editResultadoSecaoCondutor.setText(String.format("%s mm", sessaoCondutor));
                    editResultadoSecaoCondutor.setText(String.format("Seção do condutor "+ sessaoCondutor+"m²"));
                }

                //TODO PEGAR VALOR DO DISJUNTOR  E compara com a capacidade de condução

                correnteProjeto = correnteProjeto + 1;// sobe a acorrente para pegar a proxima seção de condutor caso o capacidadedeCorrente atual ñao suporte

            } catch (NumberFormatException e) {
                editResultadoSecaoCondutor.setText(String.format(getString(R.string.txtErro), e.getMessage()));
                break;
            }

            correnteProjeto = correnteProjeto + 1;// sobe a acorrente para pegar a proxima seção de condutor caso o capacidadeDeCorrente atual ñao suporte
            //todo precisa corrigir
        } while (correnteInformada >= capacidadeDeCorrente);// Enquanto corrente informada for menor ou igual a capacidade de condução IZ
        ocultarTeclado();
        editResultadoSecaoCondutor.requestFocus();
        editCapacidadeCorrente.setText(String.format("%s%s A", getString(R.string.txtEditCapacidadeDeConducao) + " \b", AppUtil.FormatarSaida(capacidadeDeCorrente))); // exibe o capacidadedeCorrente
        editResultadoSecaoCondutor.requestFocus();
        salvarSharedPrerences(sessaoCondutor);
    }
    // Preenche automaticamente campos a partir de valores salvos
    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "0"));
            editTemperatura.setText(sharedPreferences.getString(getString(R.string.idTemperatura), "1"));
        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!swSugetao.isChecked()) {
                    editCorrente.setText(null);
                    editTemperatura.setText(null);
                    editCorrente.setHint(R.string.editHintCorrente);
                    editTemperatura.setHint(R.string.editHintTemperatura);
                }

            }
        });
    }
    // Indica quando a corrente informada excede o limite das tabelas
    private void correnteExcedeu() {
        editCorrente.setError("*");
        editResultadoSecaoCondutor.requestFocus();
        editResultadoSecaoCondutor.setError(getString(R.string.alertcorrenteExcedeu));
        editResultadoSecaoCondutor.setText(null);
        editCapacidadeCorrente.setText(null);
    }
    private void salvarSharedPrerences(double d) {
        //TODO salvar dados
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        Double a = Double.parseDouble( editCorrente.getText().toString());

        dados.putString(getString(R.string.idAmper), AppUtil.FormatarSaida(a) );
        dados.putString(getString(R.string.idTemperatura), editTemperatura.getText().toString());

        dados.putString(getString(R.string.idSecao), String.valueOf(d));
        dados.apply();
    }
    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
    }

    /**
     * Verifica se os campos foram preenchidos
     * Verifica se não tem campo em branco
     *
     * @return boolean
     */
    public boolean validarDados() {
        boolean retorno = true;
        if (TextUtils.isEmpty(editCorrente.getText().toString())) {
            retorno = false;
            editCorrente.setError("*");
            editCorrente.requestFocus();
        } else if (TextUtils.isEmpty(editTemperatura.getText().toString())) {
            retorno = false;
            editTemperatura.setError("*");
            editTemperatura.requestFocus();
        }else if (TextUtils.isEmpty(editNumeroCondutosCarregados.getText().toString())) {
            retorno = false;
            editNumeroCondutosCarregados.setError("*");
            editNumeroCondutosCarregados.requestFocus();
        }
        return retorno;
    }

    /**
     * Fecha teclado
     */
    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(Swa1.getWindowToken(), 0);
    }

    /**
     * instancia os objetos
     */
    private void initFormulary() {
         
        //Entrada de Dados
        Swa1 = findViewById(R.id.swA1);
        A2 = findViewById(R.id.swA2);
        B1 = findViewById(R.id.swB1);
        B2 = findViewById(R.id.swB2);
        swSugetao = findViewById(R.id.swSugestao);
        // C = findViewById(R.id.C);
        editNumeroCondutosCarregados = findViewById(R.id.editNumeroDeCondutoresCarregados);
        editTemperatura = findViewById(R.id.editTemperatura);
        txtTipopoDeLinha = findViewById(R.id.txtTipopoDeInstalacao);
        editCorrente = findViewById(R.id.editCorrente);

        //Ação
        btnCalcular = findViewById(R.id.btnCalcular);

        //Saida
        editResultadoSecaoCondutor = findViewById(R.id.editResultadoConsumo);
        editCapacidadeCorrente = findViewById(R.id.editCapacidadeCorrente);

        //Valores Iniciais
        B1.setChecked(true);
        tipoDeLinhaEletrica = "B1";

        editNumeroCondutosCarregados.setText(Integer.toString(2));
        numeroDECondutoresCarregados = Integer.parseInt(editNumeroCondutosCarregados.getText().toString());

    }


    public void incrementador(View view) {
        if (contador <= 5) {
            try {
                contador++;
                editNumeroCondutosCarregados.setText(String.valueOf(contador));
            } catch (Exception e) {
                editNumeroCondutosCarregados.setText(getString(R.string.txtErro) + e);
            }
        }


    }

    public void decrementador(View view) {

        if (contador > 2) {
            try {
                contador--;
                editNumeroCondutosCarregados.setText(String.valueOf(contador));
            } catch (Exception e) {
                editNumeroCondutosCarregados.setText(getString(R.string.txtErro) + e);
            }
        }

    }


    public void onClickA1(View view) {
        A2.setChecked(false);
        B1.setChecked(false);
        B2.setChecked(false);
        //C.setChecked(false);
        tipoDeLinhaEletrica = Swa1.getText().toString();

    }

    public void OnclickA2(View view) {
        Swa1.setChecked(false);
        B1.setChecked(false);
        B2.setChecked(false);
        // C.setChecked(false);
        tipoDeLinhaEletrica = A2.getText().toString();

    }

    public void OnclickB1(View view) {
        Swa1.setChecked(false);
        A2.setChecked(false);
        B2.setChecked(false);
        // C.setChecked(false);
        tipoDeLinhaEletrica = B1.getText().toString();

    }

    public void OnclickB2(View view) {
        Swa1.setChecked(false);
        A2.setChecked(false);
        B1.setChecked(false);
        //C.setChecked(false);
        tipoDeLinhaEletrica = B2.getText().toString();

    }

    public void ajudaCondutorCobre(View view) {
        builder = new AlertDialog.Builder(CondutorActivity.this);
        builder.setTitle(R.string.alertaTituloCondutorCobre);
        builder.setMessage(R.string.ajudaTipoDeCondutor);
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_ajuda);
        AlertDialog.Builder voltar = builder.setPositiveButton("Voltar", (dialog, which) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }


    public void AjudaTipoDeInstalacao(View view) {
        // Objeto para troca de dados entre activitys
        Bundle bundle = new Bundle();
        // setando a informação utilizadas na classe ajuda para abrir o site correspondente
        bundle.putString("icone", "instalacao");
        bundle.putString("activity", "condutor");
        // chamando a classe de ajuda
        Intent intent = new Intent(CondutorActivity.this, Ajuda.class);
        // passando o obj para a intent
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void ajudaCorrente(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "corrente");
        bundle.putString("activity", "condutor");

        Intent intent = new Intent(CondutorActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    public void numeroCondutorCar(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "numeroCondutorCar");
        bundle.putString("activity", "condutor");

        Intent intent = new Intent(CondutorActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    public void ajudaTemperatura(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "temp");
        bundle.putString("activity", "condutor");

        Intent intent = new Intent(CondutorActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }
    public void AjudaTipodeIsolacao(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "iso");
        bundle.putString("activity", "condutor");

        Intent intent = new Intent(CondutorActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    // Fator de correção por temperatura ambiente (FTC)
    public Double CorrecaoFTC(String temperatura) {
        Double t = Double.parseDouble(temperatura), r = 1.0;
        if (t <= 10) {
            r = 1.22;
        } else if (t <= 15) {
            r = 1.17;
        } else if (t <= 20) {
            r = 1.12;
        } else if (t <= 25) {
            r = 1.06;
        } else if (t <= 30) {
            r = 1.;
        } else if (t <= 35) {
            r = 0.94;
        } else if (t <= 40) {
            r = 0.87;
        } else if (t <= 45) {
            r = 0.79;
        } else if (t <= 50) {
            r = 0.71;
        } else if (t <= 55) {
            r = 0.61;
        } else if (t <= 60) {
            r = 0.5;
        }

        return r;
    }


    // Seleciona PVC como material isolante
    public void onClickPVC(View view) {
         material = 0;

    }

    // Seleciona EPR (XLPE) como material isolante
    public void onClickXls(View view) {

        material = 1;
    }
}
