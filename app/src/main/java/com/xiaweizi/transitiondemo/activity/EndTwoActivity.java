package com.xiaweizi.transitiondemo.activity;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaweizi.transitiondemo.R;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EndTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_two);
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTarget(findViewById(R.id.iv_end_two4));
        transitionSet.addTarget(findViewById(R.id.tv_end_two3));
        transitionSet.addTarget(findViewById(R.id.tv_end_two1));
        getWindow().setSharedElementEnterTransition(transitionSet);
        setEnterSharedElementCallback(new MyListener("enter"));
    }

    private class MyListener extends androidx.core.app.SharedElementCallback {
        private String type = "";
        public MyListener(String type) {
            super();
            this.type =  "end-MyListener-" + type + "::";
        }

        @Override
        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            Log.i(type, "onSharedElementStart: " + getNames(sharedElementNames) + getElements(sharedElements) + getElements(sharedElementSnapshots));
            super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        @Override
        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            Log.i(type, "onSharedElementEnd: " + getNames(sharedElementNames) + getElements(sharedElements) + getElements(sharedElementSnapshots));
        }

        @Override
        public void onRejectSharedElements(List<View> rejectedSharedElements) {
            super.onRejectSharedElements(rejectedSharedElements);
            Log.i(type, "onRejectSharedElements: " + getElements(rejectedSharedElements));
        }

        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            super.onMapSharedElements(names, sharedElements);
            Log.i(type, "onMapSharedElements: " + getNames(names) + getElementsMap(sharedElements));
        }

        @Override
        public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
            Log.i(type, "onCaptureSharedElementSnapshot: " + screenBounds.toShortString());
            return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        @Override
        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            Log.i(type, "onCreateSnapshotView: ");
            return super.onCreateSnapshotView(context, snapshot);
        }

        @Override
        public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
            Log.i(type, "onSharedElementsArrived: " + getNames(sharedElementNames) + getElements(sharedElements));
            super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
        }

        private String getElements(List<View> sharedElements) {
            StringBuilder sb = new StringBuilder();
            if (sharedElements != null) {
                sb.append("\tsharedElements:");
                for (View sharedElement : sharedElements) {
                    sb.append(sharedElement.getTag()).append(";");
                }
            }
            return sb.toString();
        }

        private String getElementsMap(Map<String, View> sharedElements) {
            StringBuilder sb = new StringBuilder();
            if (sharedElements != null) {
                sb.append("\tsharedElementsMap:");
                Set<String> strings = sharedElements.keySet();
                for (String key : strings) {
                    sb.append(key).append(":").append(sharedElements.get(key).getTag()).append(";");
                }
            }
            return sb.toString();
        }

        private String getNames(List<String> names) {
            StringBuilder sb = new StringBuilder();
            if (names != null) {
                sb.append("\tnames:");
                for (String name : names) {
                    sb.append(name).append(";");
                }
            }
            return sb.toString();
        }

    }


}
