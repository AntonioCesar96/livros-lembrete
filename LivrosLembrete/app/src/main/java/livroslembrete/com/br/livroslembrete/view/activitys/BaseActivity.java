package livroslembrete.com.br.livroslembrete.view.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.model.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.model.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.view.fragments.LembretesFragment;
import livroslembrete.com.br.livroslembrete.view.fragments.LivroFragment;
import livroslembrete.com.br.livroslembrete.model.domain.Usuario;
import livroslembrete.com.br.livroslembrete.model.utils.AlertUtils;
import livroslembrete.com.br.livroslembrete.view.BaseView;

public class BaseActivity extends AppCompatActivity implements BaseView {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected ProgressDialog progressDialog;

    protected void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setUpNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setupNavDrawer() {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            if (navigationView != null && drawerLayout != null) {
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();

                Usuario usuario = Application.getInstance().getUsuario();
                if (usuario != null) {
                    View headerView = navigationView.getHeaderView(0);
                    TextView tNome = headerView.findViewById(R.id.txtNome);
                    TextView tEmail = headerView.findViewById(R.id.txtEmail);

                    tNome.setText(usuario.getNome());
                    tEmail.setText(usuario.getEmail());
                }

                navigationView.setNavigationItemSelectedListener(onNavigationItemSelected());
            }
        }
    }

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelected() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_livros) {
                    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.content_main, new LivroFragment(), "TAG");
                    t.commit();
                } else if (id == R.id.nav_lembretes) {
                    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.content_main, new LembretesFragment(), "TAG");
                    t.commit();
                } else if (id == R.id.nav_sair) {
                    finalizarSessao();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        };
    }

    private void finalizarSessao() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("Fazer logout");
        builder.setMessage("Você realmente deseja sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    DataBaseHelper dataBaseHelper = Application.getInstance().getDataBaseHelper();
                    new UsuarioDAO(dataBaseHelper.getConnectionSource()).deletar();

                    Application.getInstance().setUsuario(null);

                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    finish();
                } catch (SQLException e) {
                    Log.i("", e.getMessage());
                }

                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlert(String title, String msg) {
        AlertUtils.alert(this, title, msg);
    }

    @Override
    public void openActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
        finish();
    }

    @Override
    public void showProgressDialog(String title, String msg) {
        this.progressDialog = ProgressDialog.show(this, title, msg, false, false);
    }

    @Override
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setResultActivity(int code, Intent intent) {
        setResult(code, intent);
        finish();
    }

    @Override
    public void setResultActivity(int code) {
        setResult(code);
        finish();
    }
}
