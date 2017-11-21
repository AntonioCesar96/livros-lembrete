package livroslembrete.com.br.livroslembrete.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.fragments.LivroFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setupNavDrawer();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_main, new LivroFragment(), "TAG");
        t.commit();
    }
}
