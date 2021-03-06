package me.aelesia;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import me.aelesia.R;

import java.util.ArrayList;
import java.util.List;

public class ActionSheetAndroidModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private boolean isShowingDialog = false;

    public ActionSheetAndroidModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ActionSheetAndroid";
    }

    @ReactMethod
    public void options(String title, String message, String cancel, ReadableArray option, int destructiveIndex,
            String tintColor, String backgroundColor, String textColor, String borderColor, Promise promise) {
        if (!isShowingDialog) {
            List<Object> messageStrList = option.toArrayList();
            List<String> strList = new ArrayList<String>();

            for (Object msg : messageStrList) {
                if (msg instanceof String) {
                    strList.add((String) msg);
                }
            }

            BottomSheetDialog dialog = new BottomSheetDialog(getCurrentActivity(), R.style.BottomSheetDialog);
            dialog.setContentView(R.layout.actionsheet);

            if (title != null || message != null) {
                LinearLayout header = dialog.findViewById(R.id.actionsheet_header);
                GradientDrawable d = (GradientDrawable) ((LinearLayout) header.getParent()).getBackground();
                d.setColorFilter(Color.parseColor(backgroundColor), PorterDuff.Mode.SRC_ATOP);

                header.setPadding(0, 24, 0, 24);
                if (title != null) {
                    TextView titleText = dialog.findViewById(R.id.actionsheet_title);
                    titleText.setText(title);
                    titleText.setVisibility(View.VISIBLE);
                }

                if (message != null) {
                    TextView messageText = dialog.findViewById(R.id.actionsheet_message);
                    messageText.setText(message);
                    messageText.setPadding(0, 12, 0, 0);
                    messageText.setVisibility(View.VISIBLE);
                }
            }

            if (cancel == null) {
                dialog.setCancelable(false);
            } else {
                dialog.setOnCancelListener(dialog1 -> {
                    isShowingDialog = false;
                    promise.resolve(-1);
                });
            }

            ListView listView = dialog.findViewById(R.id.actionsheet_list);
            listView.setDivider(new ColorDrawable(Color.parseColor(borderColor)));
            listView.setDividerHeight(1);
            if (title != null || message != null) {
                View border = new View(reactContext);
                // border.setBackgroundColor(Color.parseColor(borderColor));
                border.setLayoutParams(
                        new android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                listView.addHeaderView(border);
            }
            ActionSheetListAdapter adapter = new ActionSheetListAdapter(reactContext, strList, destructiveIndex,
                    tintColor, backgroundColor, textColor, borderColor, position -> {
                        dialog.dismiss();
                        isShowingDialog = false;
                        promise.resolve(position);
                    });
            listView.setAdapter(adapter);
            listView.setBackgroundColor(Color.parseColor(backgroundColor));

            if (cancel != null) {
                View border = new View(reactContext);
                // border.setBackgroundColor(Color.parseColor(borderColor));
                border.setLayoutParams(
                        new android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
                listView.addFooterView(border);

                dialog.findViewById(R.id.actionsheet_list);
                TextView cancelText = dialog.findViewById(R.id.actionsheet_cancel);
                try {
                    cancelText.setTextColor(Color.parseColor(tintColor));
                } catch (Exception e) {
                    cancelText.setTextColor(Color.parseColor(textColor));
                }
                cancelText.setText(cancel);
                cancelText.setBackgroundColor(Color.parseColor(backgroundColor));
                LinearLayout cancelView = dialog.findViewById(R.id.actionsheet_cancel_view);
                cancelView.setBackgroundColor(Color.parseColor(backgroundColor));
                cancelView.setVisibility(View.VISIBLE);
                cancelView.setOnClickListener(v -> {
                    dialog.dismiss();
                    isShowingDialog = false;
                    promise.resolve(-1);
                });
            }

            isShowingDialog = true;
            dialog.show();
        }
    }
}
