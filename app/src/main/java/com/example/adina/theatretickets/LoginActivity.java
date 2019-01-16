package com.example.adina.theatretickets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adina.theatretickets.DataBase.ConnectDatabase;
import com.example.adina.theatretickets.Models.Play;
import com.example.adina.theatretickets.Models.Ticket;
import com.example.adina.theatretickets.Models.User;
import com.example.adina.theatretickets.Utils.HashingPassword;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    public static long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectDatabase.getConnection(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //insertUser();
        getUsers();
        getPlays();
        getTickets();

        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            focusView.requestFocus();
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            focusView.requestFocus();
            cancel = true;
        }

        if (!cancel) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private volatile boolean match = false;
        private volatile boolean isAdmin = false;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
                    User user = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().returnUserByUsername(mEmail);
                    if (user != null) {
                        userID = user.getUserID();
                        if (user.getUsername().equals(mEmail) && user.getPassword().equals(HashingPassword.encodePassword(mPassword))) {
                            match = true;
                            if (user.getRole() == 0) {
                                isAdmin = true;
                            }
                        }
                    }
            return match;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Toast.makeText(getApplicationContext(), "SUCCESS!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, AdminFirstActivity.class);
                    intent.putExtra("isAdmin", isAdmin);
                    startActivity(intent);

            } else {
                mEmailView.setText("");
                mPasswordView.setText("");
                showAlertDialogButtonClicked();
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public void showAlertDialogButtonClicked() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("We might have a problem..");
        builder.setMessage("There is no account with this email address.");

        // add the buttons
        builder.setPositiveButton("Register",  new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        builder.setNegativeButton("Try Again", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    ////////////  DE TEST  /////////////
    private void insertUser(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setPassword(HashingPassword.encodePassword("hello1"));
                user.setUsername("adina1@mail.com");
                user.setRole(0);
                ConnectDatabase.getTheatreTicketsDatabase().daoAccess().insertUser(user);
            }
        });

    }
    private void getUsers() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<User> list = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getUsers();
                for (User u : list) {
                    System.out.println("________ID[ " + u.getUserID() + " ]" + "  ___Email: " + u.getUsername() + "  ___Pass: " + u.getPassword() + "  ___Role: " + u.getRole());
                }
            }
        });
    }

    private void getPlays() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Play> list = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getAllPlays();
                for (Play u : list) {
                    System.out.println("________ID[ " + u.getPlayID() + " ]" + "  ___Title: " + u.getTitle() + "  ___Details: " + u.getDetails() + "  ___Date: " + u.getPlayDate() + " ___Available nr. of tickets: " + u.getAvailableTickets());
                }
            }
        });
    }

    private void getTickets() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Ticket> list = ConnectDatabase.getTheatreTicketsDatabase().daoAccess().getALLTickets();
                for (Ticket u : list) {
                    System.out.println("________ID[ " + u.getTicketID() + " ]" + "  ___PlayID: " + u.getPlayID() + "  ___USerID: " + u.getUserID() + "  ___Seat: " + u.getSeat());
                }
            }
        });
    }
}

