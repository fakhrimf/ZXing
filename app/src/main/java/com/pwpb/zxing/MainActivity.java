package com.pwpb.zxing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.IllegalFormatException;

public class MainActivity extends AppCompatActivity {

    ImageView ivQR;
    Button btn;
    EditText nama;
    String namaValue;
    Thread thread;
    public static final int QR_CODE_WIDTH = 500;
    Bitmap bmp;

    public void initUI() {
        ivQR = findViewById(R.id.ivQR);
        btn = findViewById(R.id.btnGenerate);
        nama = findViewById(R.id.edtNama);
    }

    public void initBtn() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaValue = nama.getText().toString();
                try {
                    bmp = ttie(namaValue);
                    ivQR.setImageBitmap(bmp);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Bitmap ttie(String value) throws WriterException {
        BitMatrix bm = null;
        try {
            bm = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QR_CODE_WIDTH, QR_CODE_WIDTH, null
            );
        } catch (IllegalFormatException i) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        int[] pixels = new int[bmWidth * bmHeight];

        for (int y = 0; y < bmHeight; y++) {
            int offset = y * bmWidth;
            for (int x = 0;x<bmWidth;x++){
                pixels[offset + x]=bm.get(x,y)?getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bmp = Bitmap.createBitmap(bmWidth,bmHeight, Bitmap.Config.ARGB_4444);
        bmp.setPixels(pixels,0,500,0,0,bmWidth,bmHeight);
        return bmp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initBtn();
    }
}
