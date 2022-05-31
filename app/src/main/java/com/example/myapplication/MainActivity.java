package com.example.myapplication;

import static android.content.ContentValues.TAG;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DatabaseAdapter;

import org.w3c.dom.Text;

public class MainActivity extends Activity {


    private ImageView imageView;
    private final int Pick_image = 1;
    private ListView postList;
    private Img img;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        Button PickImage = (Button) findViewById(R.id.open);
        Button save = (Button) findViewById(R.id.save);
        text = findViewById(R.id.textView4);

        PickImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });

        DatabaseAdapter dataAdapter = new DatabaseAdapter(this);
        save.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            dataAdapter.open();

            dataAdapter.insert(img);
            List<Img> posts = dataAdapter.getPosts();
            for(int i = 0; i<posts.size(); i++){
                Log.d("id", String.valueOf((posts.get(i)).getId()));
                Log.d("url", String.valueOf((posts.get(i)).getUrl()));
            }
            dataAdapter.close();

            Toast toast = Toast.makeText(MainActivity.this, "URL сохранен. Id: "+ (posts.get(posts.size()-1)).getId(), Toast.LENGTH_LONG);
            toast.show();
        }
        });
    }


    //Обрабатываем результат выбора в галерее:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        //Получаем URI изображения, преобразуем его в Bitmap
                        final Uri imageUri = imageReturnedIntent.getData();
                        text.setText(imageUri.toString());
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        img = new Img(0, imageUri.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}}