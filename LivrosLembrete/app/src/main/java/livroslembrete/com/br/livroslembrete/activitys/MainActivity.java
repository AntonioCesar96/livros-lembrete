package livroslembrete.com.br.livroslembrete.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.fragments.LivroFragment;

public class MainActivity extends BaseActivity {
    public static final int RECRIAR_ACTIVITY = 5;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RECRIAR_ACTIVITY) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
