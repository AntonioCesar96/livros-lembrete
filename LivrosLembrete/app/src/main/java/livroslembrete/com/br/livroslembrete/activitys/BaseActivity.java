package livroslembrete.com.br.livroslembrete.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.fragments.LembretesFragment;
import livroslembrete.com.br.livroslembrete.fragments.LivroFragment;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

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
            drawerLayout =  findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            if (navigationView != null && drawerLayout != null) {
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();

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
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
