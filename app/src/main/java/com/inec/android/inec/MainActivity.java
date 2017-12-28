package com.inec.android.inec;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inec.android.inec.adapter.RepositoryListAdapter;
import com.inec.android.inec.util.NetworkUtils;
import com.inec.android.inec.util.ProfileUtils;
import com.inec.android.inec.model.User;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements RepositoryListAdapter.ListItemClickListener{
    //Componentes basicos usados na View
    private TextView mTextViewNameGitHub;
    private TextView mTextViewNameAccount;
    private CircleImageView mImageAccount;
    private EditText mSearchBoxEditText;
    private Button mButtonSearch;
    private ProgressBar mLoadingIndicator;

    //Objeto Usuario que sera exibido e Toast para a descricao dos repositorios quando o item e clicado
    private User github_user;
    private Toast mToast;

    //Atributos usados para a lista de repositorios
    RepositoryListAdapter mAdapter;
    RecyclerView mNumbersList;

    //Cria a View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewNameGitHub = (TextView) findViewById(R.id.tv_name);
        mTextViewNameAccount = (TextView) findViewById(R.id.tv_account);
        mImageAccount = (CircleImageView) findViewById(R.id.im_profile);
        mSearchBoxEditText = (EditText) findViewById(R.id.input_user);
        mButtonSearch = (Button) findViewById(R.id.btn_search);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    //Listener para o botao Search User (btn_search). Executa a Task em busca do usuario passado pelo Edit Text
    public void onClickSearchUser(View v) {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        new GithubQueryTask().execute(githubSearchUrl);
    }

    //Permite executar uma tarefa em background para nao ocupar a thread principal
    public class GithubQueryTask extends AsyncTask<URL, Void, User> {
        //Antes de iniciar a execucao mostrar a barra de progresso
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        //Realiza a busca pelo usuario em background
        @Override
        protected User doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = searchUrl.toString();
            return ProfileUtils.parseJsonUser(githubSearchResults);
        }

        //Apos a execucao e feito a alteracao na view e mostrado o resultado da busca
        @Override
        protected void onPostExecute(User account) {
            if (account != null){
                github_user = account;

                hideKeyborad();
                hideSearchComponents();
                printUserInformation();
                createRepositoryList();
            }
            else{
                String toastMessage = "User not found";
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            }
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }

        //Esconde Keyborad após buscar usuario
        private void hideKeyborad(){
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        //Esconde o edit text e o botao de busca
        private void hideSearchComponents(){
            mSearchBoxEditText.setVisibility(View.GONE);
            mButtonSearch.setVisibility(View.GONE);
        }

        //Altera o texto e imagem da apresentacao do usuario
        private void printUserInformation(){
            mTextViewNameGitHub.setText(github_user.getName());
            mTextViewNameAccount.setText(github_user.getUser_login());
            mImageAccount.setImageBitmap(github_user.getImage_profile());
        }

        //Monta a lista de repositorios para um determinado usuario
        private void createRepositoryList(){
            mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            mNumbersList.setLayoutManager(layoutManager);
            mNumbersList.setHasFixedSize(true);
            mAdapter = new RepositoryListAdapter(github_user.getRepository(), MainActivity.this);
            mNumbersList.setAdapter(mAdapter);
        }
    }

    //Adiciona um menu 'Reset' na barra superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Quando o Reset e clicado e reiniciado a Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_refresh:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //Quando um item da lista de repositorios e selecionado e exibido um Toast com a descricao do repositorio
    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Description: " + github_user.getRepository().get(clickedItemIndex).getDescription();
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}
