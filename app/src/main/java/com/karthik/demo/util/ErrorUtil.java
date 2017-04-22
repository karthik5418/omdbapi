package com.karthik.demo.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthik.demo.R;
import com.karthik.demo.listener.OnTryAgainListener;

import static android.view.View.VISIBLE;
import static com.karthik.demo.constants.Constants.SNACK_BAR_TIME_OUT;


/**
 * Created by karthik on 16/12/16.
 */

public class ErrorUtil {

    // Error type
    public static final int NO_INTERNET = 0;
    public static final int NO_RESULTS = 1;
    public static final int SERVER_ERROR = 2;
    public static final int FAILURE_ERROR = 3;

    // Error message
    public static final String NO_INTERNET_MESSAGE = "Your network doesn’t seem to be working properly";
    public static final String TIME_OUT_MESSAGE = "Your network doesn’t seem to be working properly";
    public static final String SERVER_ERROR_MESSAGE = "Something went wrong. Please try again later.";


    private static LinearLayout llNetworkError;
    private static LinearLayout llServerError;
    private static LinearLayout llNoResultError;


    public static Snackbar setSnackBarError(final View coordinatorLayout, String buttonText, String message, int length, final OnTryAgainListener onTryAgainListener) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, length)
                .setAction(buttonText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onTryAgainListener.onTryAgain();
                    }
                });

        View view = snackbar.getView(); // showing snackbar on top
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        view.setLayoutParams(params);
        snackbar.show();
        return snackbar;
    }

    public static void showSnackBar(final View rootView, String message, int length, final OnTryAgainListener onTryAgainListener) {
        TextView tvMessage = (TextView) rootView.findViewById(R.id.tv_message);
        TextView tvRetry = (TextView) rootView.findViewById(R.id.tv_try_again);

        tvMessage.setText(message);
        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTryAgainListener.onTryAgain();
            }
        });

        rootView.setPivotX(0);
        rootView.setPivotY(0);

        if (length == Snackbar.LENGTH_INDEFINITE) {
            tvRetry.setVisibility(View.VISIBLE);
            animateViewIn(rootView, tvRetry, true);
        } else {

            tvRetry.setVisibility(View.GONE);
            animateViewIn(rootView, tvRetry, false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateViewOut(rootView);
                }
            }, SNACK_BAR_TIME_OUT);
        }


    }

    private static void animateViewIn(final View rootView, final TextView tvRetry, final boolean isTryAgain) {
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(rootView, "scaleY", 0.0f, 1.0f);
        scaleAnimator.setDuration(300);
        scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.setVisibility(VISIBLE);
                if (isTryAgain) {
                    tvRetry.setVisibility(VISIBLE);
                } else {
                    tvRetry.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnimator.start();
    }

    public static void hideSnackBar(final View rootView) {
        rootView.setPivotX(0);
        rootView.setPivotY(0);
        animateViewOut(rootView);
    }

    private static void animateViewOut(final View rootView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(rootView, "scaleY", 1.0f, 0.0f);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    public static void setSnackBarError(View errorView, TextView tvError, TextView tvTryAgain, boolean isTryAgain, String errorMessage, final OnTryAgainListener listener) {
        errorView.setVisibility(VISIBLE);
        tvError.setText(errorMessage);
        if (isTryAgain) {
            tvTryAgain.setVisibility(VISIBLE);
        } else {
            tvTryAgain.setVisibility(View.GONE);
        }
        tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }


    public static void showInsetLoader(View rootView, View loaderView) {
        rootView.setVisibility(View.GONE);
        loaderView.setVisibility(VISIBLE);
    }

    public static void hideInsetLoader(View rootView, View loaderView) {
        rootView.setVisibility(VISIBLE);
        loaderView.setVisibility(View.GONE);

    }

    public static void showInsetError(View rootView, View errorRoot, View errorView, String message, final OnTryAgainListener listener) {
        rootView.setVisibility(View.GONE);
        errorRoot.setVisibility(VISIBLE);
        errorView.setVisibility(VISIBLE);
        Button btRetry;
        TextView tvNoResultMessage = (TextView) errorRoot.findViewById(R.id.tv_no_result_desc);
        if (!message.equals("")) {
            if (tvNoResultMessage != null) {

            }
        }
        btRetry = (Button) errorRoot.findViewById(R.id.bt_no_network_retry);
        if (btRetry == null) {
            btRetry = (Button) errorRoot.findViewById(R.id.bt_network_timeout_retry);
        }
        if (btRetry == null) {
            btRetry = (Button) errorRoot.findViewById(R.id.bt_server_retry);
        }

        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });


        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }

    public static void showNetworkError(View rootView, View errorRoot, final OnTryAgainListener listener) {
        if (llServerError != null) {
            llServerError.setVisibility(View.GONE);
        }

        if (llNoResultError != null) {
            llNoResultError.setVisibility(View.GONE);
        }


        rootView.setVisibility(View.GONE);
        errorRoot.setVisibility(VISIBLE);
        llNetworkError = (LinearLayout) errorRoot.findViewById(R.id.ll_no_network);

        llNetworkError.setVisibility(VISIBLE);
        Button btRetry = (Button) errorRoot.findViewById(R.id.bt_no_network_retry);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
        llNetworkError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }

    public static void showServerError(View rootView, View errorRoot, final OnTryAgainListener listener) {
        if (llNetworkError != null) {
            llNetworkError.setVisibility(View.GONE);
        }

        if (llNoResultError != null) {
            llNoResultError.setVisibility(View.GONE);
        }


        rootView.setVisibility(View.GONE);
        errorRoot.setVisibility(VISIBLE);
        llServerError = (LinearLayout) errorRoot.findViewById(R.id.ll_server_error);
        llServerError.setVisibility(VISIBLE);
        Button btRetry = (Button) errorRoot.findViewById(R.id.bt_server_retry);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
        llServerError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }

    public static void showFailure(View rootView, View errorRoot, String message, final OnTryAgainListener listener) {
        if (llNetworkError != null) {
            llNetworkError.setVisibility(View.GONE);
        }

        if (llNoResultError != null) {
            llNoResultError.setVisibility(View.GONE);
        }


        rootView.setVisibility(View.GONE);
        errorRoot.setVisibility(VISIBLE);
        llServerError = (LinearLayout) errorRoot.findViewById(R.id.ll_server_error);
        llServerError.setVisibility(VISIBLE);
        TextView tvFailure = (TextView) llServerError.findViewById(R.id.tv_server_desc);
        tvFailure.setText(message);
        Button btRetry = (Button) errorRoot.findViewById(R.id.bt_server_retry);
        btRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
        llServerError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }


    public static void showNoResultsError(View rootView, View errorRoot, String response, int drawable) {

        if (llServerError != null) {
            llServerError.setVisibility(View.GONE);
        }
        if (llNetworkError != null) {
            llNetworkError.setVisibility(View.GONE);
        }


        rootView.setVisibility(View.GONE);
        errorRoot.setVisibility(VISIBLE);
        llNoResultError = (LinearLayout) errorRoot.findViewById(R.id.ll_no_result_error);
        llNoResultError.setVisibility(VISIBLE);

        TextView tvNoResultDesc = (TextView) llNoResultError.findViewById(R.id.tv_no_result_desc);
        tvNoResultDesc.setText(response);

        ImageView ivNoResult = (ImageView) llNoResultError.findViewById(R.id.iv_no_results);
        ivNoResult.setImageResource(drawable);
    }



    public static void hideInsetError(View rootView, View inError) {
        inError.setVisibility(View.GONE);
        rootView.setVisibility(VISIBLE);
    }


    public static void showInsetError(int errorType, View rootView, View errorView, View noNetworkView, View serverErrorView, View noResultView, TextView tvErrorMessage, String errorMessage, final OnTryAgainListener listener) {

        rootView.setVisibility(View.GONE);
        errorView.setVisibility(VISIBLE);
        if (errorMessage != null) {
            tvErrorMessage.setText(errorMessage);
        }

        if (errorType == NO_INTERNET) {
            noResultView.setVisibility(View.GONE);
            serverErrorView.setVisibility(View.GONE);
            noNetworkView.setVisibility(VISIBLE);
            return;
        }
        if (errorType == SERVER_ERROR) {
            noNetworkView.setVisibility(View.GONE);
            noResultView.setVisibility(View.GONE);
            serverErrorView.setVisibility(VISIBLE);
            return;
        }
        if (errorType == NO_RESULTS) {
            noNetworkView.setVisibility(View.GONE);
            serverErrorView.setVisibility(View.GONE);
            noResultView.setVisibility(VISIBLE);
            return;
        }
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }

}
