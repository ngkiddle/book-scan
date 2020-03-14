package com.example.android.sqliteweather.scan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.fragment.app.Fragment;

import com.example.android.sqliteweather.R;
import com.example.android.sqliteweather.bookdetail.BookDetailActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Engine;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.frame.FrameProcessor;
import com.otaliastudios.cameraview.size.Size;

import java.nio.ByteBuffer;

public class ScanFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private TextView mOverlayTextView;
    private boolean mBarcodeFound;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scan, container, false);

        mOverlayTextView = root.findViewById(R.id.camera_overlay);
        setScanningEnabled(false);

        final CameraView camera = root.findViewById(R.id.camera);
        camera.setLifecycleOwner(this);
        camera.setMode(Mode.PICTURE);
        camera.setEngine(Engine.CAMERA1);

        final BarcodeDetector detector =
                new BarcodeDetector.Builder(getContext().getApplicationContext())
                        .setBarcodeFormats(Barcode.ISBN | Barcode.EAN_13)
                        .build();
        if(!detector.isOperational()){
            Log.d(TAG, "onCreate: Detector not operational.");
        }

        camera.addFrameProcessor(new FrameProcessor() {
            @Override
            @WorkerThread
            public void process(@NonNull com.otaliastudios.cameraview.frame.Frame frame) {
                Size size = frame.getSize();
                int format = frame.getFormat();
                if (frame.getDataClass() == byte[].class) {
                    byte[] data = frame.getData();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(data);
                    Frame frame2 = new Frame.Builder().setImageData(byteBuffer, size.getWidth(), size.getHeight(), format).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame2);
                    Log.d(TAG, "onBitmapReady: Barcodes found: " + barcodes.size());
                    if(!mBarcodeFound) {
                        handleBarcodes(barcodes);
                    }

                }
            }
        });

        mOverlayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScanningEnabled(true);
            }
        });

        return root;
    }


    private void setScanningEnabled(boolean isEnabled) {
        if(isEnabled) {
            mBarcodeFound = false;
            mOverlayTextView.setVisibility(View.GONE);
        } else {
            mBarcodeFound = true;
            mOverlayTextView.setVisibility(View.VISIBLE);
        }
    }

    private void handleBarcodes(SparseArray<Barcode> barcodes) {
        if(barcodes.size() < 1) {
            Log.d(TAG, "handleBarcodes: No barcodes found.");
            return;
        }

        Barcode barcode = barcodes.valueAt(0);
        String barcodeVal = barcode.rawValue;

        if(isISBNFormat(barcodeVal)) {
            Log.d(TAG, "handleBarcodes: Found ISBN: " + barcodeVal);
            // Launch activity
            Intent intent = new Intent(getContext(), BookDetailActivity.class);
            intent.putExtra(BookDetailActivity.EXTRA_ISBN, barcodeVal); //Optional parameters
            getContext().startActivity(intent);
            setScanningEnabled(false);
        }
    }

    private boolean isISBNFormat(String s) {
        // Check that length is correct and it's all numbers
        return s.length() <= 13 && s.matches("[0-9]+");
    }
}
