package com.momo.engks.dalil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.roger.catloadinglibrary.CatLoadingView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private long time;
    TextView txt_title;
    TextView txt_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        FrameLayout frameLayout = findViewById(R.id.frame_container);
        txt_title = findViewById(R.id.txt_title);
        txt_desc = findViewById(R.id.txt_desc);
        TextView textView = (TextView) toolbar.findViewById(R.id.text_toolbar);
        textView.setText("مواضيع");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragemnt(new Home_Fragment());

        toolbar_text_custom();
        toggle_menu_difinetion();
        navigarion_drawer_font();
        bottom_navigation_font();
        check_net();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Typeface typeface;
            switch (item.getItemId()) {
                case R.id.home:
                    typeface = Typeface.createFromAsset(getAssets(), "Cairo.ttf");
                    TextView textView = (TextView) toolbar.findViewById(R.id.text_toolbar);
                    textView.setText("مواضيع");
                    textView.setTypeface(typeface);
                    fragment = new Home_Fragment();
                    setFragemnt(fragment);
                    return true;
                case R.id.section:
                    typeface = Typeface.createFromAsset(getAssets(), "Cairo.ttf");
                    TextView text_sec = (TextView) toolbar.findViewById(R.id.text_toolbar);
                    text_sec.setText("أقسام");
                    text_sec.setTypeface(typeface);
                    fragment = new Section_Fragment();
                    setFragemnt(fragment);
                    return true;
                case R.id.chat:
/*
                    typeface = Typeface.createFromAsset(getAssets(), "Cairo.ttf");
                    TextView text_chat = (TextView) toolbar.findViewById(R.id.text_toolbar);
                    text_chat.setText("شات");
                    text_chat.setTypeface(typeface);
                    fragment = new Chat_Fragment();
                    setFragemnt(fragment);
  */
                startActivity(new Intent(getApplicationContext() , Sign_With_Google.class));
                /**
                 * I have 3 fragment in navigation bottom , I want to make activity(Sign_With_Google) to enable user to register
                 * using Google Sign in , but this activity will appear when i click to chat icon(chat fragment) in navigation bottom
                 * if user registered , chat fragment appear automatically , if user not registered , the activity will appear to enable user to register
                 * i'm sorry if my words are not clear i'm not good at english
                 * */

                    return true;

            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("الخروج من التطبيق")
                .setMessage("هل تريد الخروج من دليل فاقوس ؟")
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.support) {

            Rate_Dialoge rate_dialoge = new Rate_Dialoge();

            rate_dialoge.show(getSupportFragmentManager(), "ratedialoge");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getPackageName() + "\n" + "تأكد تماما اننا نسعى دائما لتحسين خدمتنا , تطبيق دليل فاقوس هو التطبيق الأول والأفضل لخدمة أهل الشرقيه وفاقوس الكرام.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.rate) {

            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

            Intent intent = goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }


        } else if (id == R.id.contact) {

            Intent send_email = new Intent(Intent.ACTION_SEND);
            send_email.setData(Uri.parse("mailto:"));
            send_email.setType("messages/rf0822");
            send_email.putExtra(Intent.EXTRA_EMAIL, new String[]{"mo7med9878@gmail.com"});
            send_email.putExtra(Intent.EXTRA_SUBJECT, "عزيزي/");
            send_email.putExtra(Intent.EXTRA_TEXT, "");
            try {
                startActivity(Intent.createChooser(send_email, "ارسال"));

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "لا يوجد ايميل بهذا العنوان", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.person) {
            Uri uri = Uri.parse("https://www.facebook.com/profile.php?id=100013381152500");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void toolbar_text_custom() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "Cairo.ttf");
        TextView textView = (TextView) toolbar.findViewById(R.id.text_toolbar);
        textView.setTypeface(typeface);


    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "CairoBold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void navigarion_drawer_font() {

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void bottom_navigation_font() {
        Menu m = navigation.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void toggle_menu_difinetion() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void check_net() {

        Check_Internet check_internet = new Check_Internet(this);
        boolean Ch = check_internet.isPhoneConnected();
        if (!Ch) {

            Toast.makeText(this, "يرجى الاتصال بالانترنت.. ", Toast.LENGTH_SHORT).show();


        }

    }


    private void setFragemnt(Fragment fragemnt) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragemnt);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


}
